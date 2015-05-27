package es.bsc.autonomic.powermodeller.tools;

import es.bsc.autonomic.powermodeller.exceptions.VariableParserException;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class VariableParserTest {

    List<String> originalMetrics = Arrays.asList("cpu-cycles", "reads", "writes", "cache-misses");

    @Test
    public void testVariableParserConstruction() throws Exception {

        VariableParser vp = new VariableParser(getClass().getResource("/variables.conf").getPath(), originalMetrics);
        assertTrue(vp.getNewMetrics().get("newMetric").equalsIgnoreCase("2*2*a1*a4"));
    }

    @Test(expected = VariableParserException.class)
    public void testVariableParserFailsOnNonExistingVariables() throws Exception {

        VariableParser vp = new VariableParser(getClass().getResource("/variables2.conf").getPath(), originalMetrics);
    }

    @Test(expected = VariableParserException.class)
    public void testVariableParserFailsOnNonExistingMetrics() throws Exception {

        VariableParser vp = new VariableParser(getClass().getResource("/variables3.conf").getPath(), originalMetrics);
    }

    /**
     * A solution must be found to the fact that the keys are read in unsorted order by Apache Commons.
     * @throws Exception
     */
    /*@Test
    @Ignore
    public void testVariableParserPreviousDefinitions() throws Exception {

        VariableParser vp = new VariableParser(getClass().getResource("/variables4.conf").getPath(), originalMetrics);
        System.out.println(vp.getNewMetrics().get("newMetric1"));
        System.out.println(vp.getNewMetrics().get("newMetric2"));
    }*/

}