package es.bsc.autonomic.powermodeller.tools.classifiers;

import es.bsc.autonomic.powermodeller.DataSet;
import es.bsc.autonomic.powermodeller.exceptions.WekaWrapperException;
import weka.classifiers.Classifier;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * @author Mauro Canuto (mauro.canuto@bsc.es)
 */
public class BaggingClassifier extends WekaWrapper  {

    @Override
    protected Classifier buildClassifier(DataSet training_ds) {
        logger.debug("Building Bagging classifier.");

        Classifier model = null;

        // Get the independent variable index
        String independent = training_ds.getIndependent();

        if (independent == null)
            throw new WekaWrapperException("Independent variable is not set in dataset.");

        try {

            // Read all the instances in the file (ARFF, CSV, XRFF, ...)
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(training_ds.getFilePath());
            Instances instances = source.getDataSet();

            // Set the independent variable (powerWatts).
            instances.setClassIndex(instances.attribute(independent).index());

            // Builds a regression model for the given data.
            model = new weka.classifiers.meta.Bagging();

            // Build Linear Regression
            model.buildClassifier(instances);

        } catch (WekaWrapperException e) {
            logger.error("Error while creating Bagging classifier.", e);
            throw new WekaWrapperException("Error while creating Bagging classifier.");

        } catch (Exception e) {
            logger.error("Error while applying Bagging to data set instances.", e);
            throw new WekaWrapperException("Error while applying Bagging to data set instances.");
        }

        return model;

    }

}
