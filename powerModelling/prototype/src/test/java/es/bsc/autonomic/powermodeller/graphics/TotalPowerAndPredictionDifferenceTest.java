package es.bsc.autonomic.powermodeller.graphics;

import es.bsc.autonomic.powermodeller.DataSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TotalPowerAndPredictionDifferenceTest {
    private static DataSet actualVsPredicted;

    @Before
    public void testContextInitialization() {
        actualVsPredicted = new DataSet(getClass().getResource("/actualVsPredicted.csv").getPath());
        actualVsPredicted.setIndependent("Pactual");
    }

    //@Test
    public void testDisplaySimpleGraph() throws Exception {
        TotalPowerAndPredictionDifference graph = new TotalPowerAndPredictionDifference(actualVsPredicted);
        graph.display();

        Thread.sleep(20000);
    }
}