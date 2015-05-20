package es.bsc.autonomic.powermodeller.graphics;

import es.bsc.autonomic.powermodeller.DataSet;
import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;
import org.junit.Before;
import org.junit.Test;

public class TotalPowerVsPredictionDecomposedTest {
    private static DataSet csvDecomposedPower;

    @Before
    public void testContextInitialization() {
        csvDecomposedPower = new DataSet(getClass().getResource("/decomposedPower.csv").getPath());
        csvDecomposedPower.setIndependent(CoreConfiguration.PACTUAL_LABEL);
    }

    @Test
    public void testDisplaySimpleGraph() throws Exception {
        System.out.println(csvDecomposedPower);
        System.out.println(csvDecomposedPower.getHeader());
        TotalPowerVsPredictionDecomposed graph = new TotalPowerVsPredictionDecomposed(csvDecomposedPower);
        graph.display();

        Thread.sleep(20000);
    }

}