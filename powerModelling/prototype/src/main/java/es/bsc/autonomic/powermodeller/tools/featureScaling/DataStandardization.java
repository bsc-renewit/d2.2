package es.bsc.autonomic.powermodeller.tools.featureScaling;

import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

/**
 * @author Mauro Canuto (mauro.canuto@bsc.es)
 */
public class DataStandardization implements Serializable {
    private static final long serialVersionUID = 1L;

    final static Logger logger = Logger.getLogger(DataStandardization.class);

    private String mu;
    private String sigma;
    private String fileOutputPath;
    private String fileOutputPathValidated;

    public DataStandardization(String mu, String sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }

    public DataStandardization() {
    }

    public void generateStandardization(String fileToScale, String independentVar) {

        logger.info("Scaling training data....");
        this.fileOutputPath = CoreConfiguration.getNewCSVFileName();
        this.mu = CoreConfiguration.getNewSerializedFileName();
        this.sigma = CoreConfiguration.getNewSerializedFileName();

        String RscriptPath = CoreConfiguration.getFilePath(CoreConfiguration.R_SCRIPT_GENERATE_SCALING);
        try {
            String line;
            Process p = Runtime.getRuntime().exec(new String[]{RscriptPath, fileToScale, fileOutputPath, mu, sigma, independentVar});
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            System.out.println("################################");
            logger.info("Scaled file: " + fileOutputPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String applyStandardization(String fileToScale, String independentVar){

        logger.info("Scaling validation data....");
        fileOutputPathValidated = CoreConfiguration.getNewCSVFileName();

        String RscriptPath = CoreConfiguration.getFilePath(CoreConfiguration.R_SCRIPT_APPLY_SCALING);
        try {
            String line;
            Process p = Runtime.getRuntime().exec(new String[]{RscriptPath, fileToScale, mu, sigma, fileOutputPathValidated, independentVar});
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            System.out.println("################################");
            logger.info("Scaled file: " + fileOutputPathValidated);
            return  fileOutputPathValidated;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public String getMu() {
        return mu;
    }

    public String getSigma() {
        return sigma;
    }

    public String getFileOutputPathValidated() {
        return fileOutputPathValidated;
    }

    public String getFileOutputPath() {
        return fileOutputPath;
    }
}
