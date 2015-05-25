package es.bsc.autonomic.powermodeller.tools.filters;

import es.bsc.autonomic.powermodeller.DataSet;
import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;
import es.bsc.autonomic.powermodeller.exceptions.RemoveInvalidFilterException;

import java.io.*;

/**
 * Created by jsubirat on 2/12/14.
 * Source: http://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
 */
public class RemoveInvalid extends FilterTool {

    @Override
    protected DataSet runFilter(DataSet ds) {

        logger.debug("Applying filter " + this.getClass().getSimpleName());

        File inputFile = new File(ds.getFilePath());
        File tempFile = new File(CoreConfiguration.getNewCSVFileName());
        boolean successful = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if (trimmedLine.contains("?") || trimmedLine.toLowerCase().contains("nan")) {
                    logger.debug("Removing invalid instance: " + trimmedLine);
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
            writer.close();
            reader.close();
            successful = tempFile.renameTo(inputFile);
        } catch(IOException e) {
            successful = false;
        }

        if(!successful) {
            throw new RemoveInvalidFilterException("Error while filtering DataSet in " + ds.getFilePath());
        }

        DataSet ret = new DataSet(inputFile.getAbsolutePath());
        ret.setIndependent(ds.getIndependent());
        return ret;
    }
}
