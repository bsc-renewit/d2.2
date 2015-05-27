package es.bsc.autonomic.powermodeller.models;

import es.bsc.autonomic.powermodeller.DataSet;
import es.bsc.autonomic.powermodeller.PowerModelEstimator;
import es.bsc.autonomic.powermodeller.PowerModelGenerator;
import es.bsc.autonomic.powermodeller.ResourceModel;
import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;
import es.bsc.autonomic.powermodeller.tools.featureScaling.DataStandardization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mauro Canuto (mauro.canuto@bsc.es)
 */
public class GlobalModel extends Models implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String type = CoreConfiguration.GLOBAL_MODEL;
    private List<String> validationFiles;
    private ResourceModel global;
    private DataStandardization dataStandardization;

    @Override
    public void generateModel() {
        PowerModelGenerator pmg = new PowerModelGenerator();

        this.global = pmg.generateGlobalModel();
        this.dataStandardization = pmg.getDataStandardization();

    }

    @Override
    public DataSet validateModel() {

        DataSet validationDS = null;

        if (CoreConfiguration.SCALE_DATA) {
            String fileToProcess = DataSet.joinDataSetsFromPath(CoreConfiguration.VALIDATION).getFilePath();
            DataStandardization validateStandardization = new DataStandardization(this.dataStandardization.getMu(), this.dataStandardization.getSigma());

            validationDS = new DataSet(validateStandardization.applyStandardization(fileToProcess, CoreConfiguration.INDEPENDENT));
        }
        else{
            validationFiles = new ArrayList<String>(CoreConfiguration.VALIDATION);
            validationDS = DataSet.joinDataSetsFromPath(validationFiles);
        }


        PowerModelEstimator pme = new PowerModelEstimator(validationDS);
        DataSet result = pme.estimateGlobal(this.global);

        logger.info("Model validated with " + result.getSize() + " samples.");
        return result;
    }

    @Override
    protected String getType() {
        return this.type;
    }
}
