package es.bsc.autonomic.powermodeller.graphics;

import es.bsc.autonomic.powermodeller.DataSet;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TotalPowerVsTotalPredictionTest {

    private static DataSet csvA;

    @Before
    public void testContextInitialization() {
        csvA = new DataSet(getClass().getResource("/csvA.csv").getPath());
        csvA.setIndependent("power");
    }

    //@Test
    public void testDisplaySimpleGraph() throws Exception {
        TotalPowerVsTotalPrediction graph = new TotalPowerVsTotalPrediction(csvA);
        graph.display();

        Thread.sleep(10000);
    }
}