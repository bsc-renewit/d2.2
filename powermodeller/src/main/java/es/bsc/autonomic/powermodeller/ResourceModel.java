package es.bsc.autonomic.powermodeller;

import es.bsc.autonomic.powermodeller.exceptions.WekaWrapperException;
import es.bsc.autonomic.powermodeller.tools.VariableParser;
import es.bsc.autonomic.powermodeller.tools.classifiers.WekaWrapper;
import org.apache.log4j.Logger;
import weka.classifiers.Classifier;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static es.bsc.autonomic.powermodeller.tools.classifiers.WekaWrapper.evaluateDataset;

/**
 * Created by jsubirat on 14/11/14.
 */
public class ResourceModel implements Serializable{
    private static final long serialVersionUID = 1L;

    private Classifier model;
    private VariableParser variableParser;
    private List<String> originalHeader;
    private String independent;

    final static Logger logger = Logger.getLogger(ResourceModel.class);

    public ResourceModel(DataSet training, VariableParser varParser) {
        logger.debug("Processing training dataset.");
        DataSet trainingDSForModel = WekaWrapper.processDataSet(training, varParser);
        logger.debug("Processed training dataset. Ordering...");

        trainingDSForModel = trainingDSForModel.order();

        logger.debug("Ordered dataset. Generating classifier...");
        model = WekaWrapper.generateClassifier(trainingDSForModel);
        logger.debug("Model built.");

        variableParser = varParser;
        originalHeader = trainingDSForModel.getHeader();
        independent = trainingDSForModel.getIndependent();

    }

    public ResourceModel(DataSet training, String classifier){

        training = training.order();
        logger.debug("Ordered dataset. Generating classifier...");
        model = WekaWrapper.generateClassifier(training, classifier);
        originalHeader = training.getHeader();
        logger.debug("Model built.");

    }

    public Classifier getClassifier() {
        return model;
    }

    public List<Double> estimateIndependent(DataSet validationDataset, VariableParser validationVarParser) {

        DataSet validationDS = WekaWrapper.processDataSet(validationDataset, validationVarParser);

        validationDS = validationDS.order();
        if (!validationDS.getHeader().containsAll(originalHeader)){
            logger.error("Incompatible training and validation dataset. Training: " + originalHeader.toString() + " Validation: " + validationDS.getHeaderString());
            throw new WekaWrapperException("Incompatible training and validation dataset. Training: " + originalHeader.toString() + " Validation: " + validationDS.getHeaderString());
        }

        return WekaWrapper.validateDataset(model, validationDS);
    
    }


    public List<Double> estimateIndependent(DataSet validationDataset) {
        DataSet validationDS = WekaWrapper.processDataSet(validationDataset, this.variableParser);

        validationDS = validationDS.order();
        List<String> originalHeaderWithoutIndep = new ArrayList<String>(originalHeader);
        originalHeaderWithoutIndep.remove(independent);
        List<String> validationHeaderWithoutIndep = new ArrayList<String>(validationDS.getHeader());
        validationHeaderWithoutIndep.remove(validationDS.getIndependent());

        if ( ! originalHeaderWithoutIndep.containsAll(validationHeaderWithoutIndep)) {
            logger.error("Incompatible training and validation dataset. Training: " + originalHeader.toString() + " Validation: " + validationDS.getHeaderString());
            throw new WekaWrapperException("Incompatible training and validation dataset. Training: " + originalHeader.toString() + " Validation: " + validationDS.getHeaderString());
        }

        return WekaWrapper.validateDataset(model, validationDS);

    }
/*
    public String evaluateModel(DataSet validationDS){

        logger.debug("Evaluating model...");
        DataSet valid = WekaWrapper.processDataSet(validationDS, variableParser);
        valid = valid.order();
        return evaluateDataset(this.model, this.originalTrainingDS, valid);
    }*/

    public List<Double> estimateIndependent2Step(DataSet validationDS) {
        //DataSet validationDS = WekaWrapper.processDataSet(validationDataset, this.variableParser);

        validationDS = validationDS.order();
        List<String> originalHeaderWithoutIndep = new ArrayList<String>(originalHeader);
        originalHeaderWithoutIndep.remove(independent);
        List<String> validationHeaderWithoutIndep = new ArrayList<String>(validationDS.getHeader());
        validationHeaderWithoutIndep.remove(validationDS.getIndependent());

        if ( ! originalHeaderWithoutIndep.containsAll(validationHeaderWithoutIndep)) {
            logger.error("Incompatible training and validation dataset. Training: " + originalHeader.toString() + " Validation: " + validationDS.getHeaderString());
            throw new WekaWrapperException("Incompatible training and validation dataset. Training: " + originalHeader.toString() + " Validation: " + validationDS.getHeaderString());
        }

        return WekaWrapper.validateDataset(model, validationDS);

    }

}
