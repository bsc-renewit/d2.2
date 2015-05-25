package es.bsc.autonomic.powermodeller.tools.filters;

import es.bsc.autonomic.powermodeller.DataSet;
import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Implements the moving average formula
 * @author Mauro Canuto (mauro.canuto@bsc.es)
 */
public class MovingAverage extends FilterTool{

    // Window for moving average
    private int span = CoreConfiguration.MOVING_AVG_WINDOW;

    public MovingAverage() {
    }

    public MovingAverage(int span) {
        this.span = span;
    }


    protected DataSet runFilterMauro(DataSet ds) {

        logger.info("Applying filter " + this.getClass().getSimpleName() + "Span: " + span);

        HashMap<String, List<Double>> newColumns = new HashMap<String, List<Double>>();
        List<Double> subValues;
        Double new_value;
        int size;
        int lowIndex;
        int highIndex;

        // get list of metrics
        List<String> metrics = ds.getHeader();

        for (String metric : metrics){
            List<Double> values = ds.getCol(metric);
            size = values.size();
            List<Double> valuesAvg = new ArrayList<Double>();

            for (int i = 0; i <= size - 1; i++) {

                lowIndex = i - span;
                highIndex = i + span;

                if (lowIndex < 0)
                    lowIndex = 0;

                if (highIndex >= size-1)
                    highIndex = size-1;

                subValues = values.subList(lowIndex, highIndex+1);

                new_value = computeAvg(subValues);
                valuesAvg.add(new_value);

            }
            newColumns.put(metric, valuesAvg);
        }
        DataSet ret = new DataSet(newColumns);
        logger.info("File containing moving-averaged data: " + ret.getFilePath());
        return ret;
    }

    @Override
    protected DataSet runFilter(DataSet ds) {

    // R implementation

        logger.info("Scaling validation data....");
        String fileOutput = CoreConfiguration.getNewCSVFileName();

        String RscriptPath = CoreConfiguration.getFilePath(CoreConfiguration.R_SCRIPT_MOVING_AVERAGE);
        try {
            String line;
            Process p = Runtime.getRuntime().exec(new String[]{RscriptPath, ds.getFilePath(), Integer.toString(CoreConfiguration.MOVING_AVG_WINDOW), fileOutput});
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            System.out.println("################################");
            logger.info("Scaled file: " + fileOutput);
            return  new DataSet(fileOutput);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }


    private Double computeAvg(List<Double> values){
        double sum =  0.0;

        int size = values.size();

        for (int i = 0; i <= size -1 ; i++) {
            sum += values.get(i);
        }

        return sum / size;
    }

   public void setSpan(Integer span) {
        this.span = span;
    }
}
