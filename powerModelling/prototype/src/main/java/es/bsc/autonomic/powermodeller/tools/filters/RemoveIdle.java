package es.bsc.autonomic.powermodeller.tools.filters;

import es.bsc.autonomic.powermodeller.DataSet;
import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;


/**
 * @author Mauro Canuto (mauro.canuto@bsc.es)
 */
public class RemoveIdle extends FilterTool{
    private double idle = CoreConfiguration.POWER_IDLE;
    private String independent = CoreConfiguration.INDEPENDENT;

    public RemoveIdle(double idle, String independent) {
        this.idle = idle;
        this.independent = independent;
    }
    public RemoveIdle(String independent) {
        this.independent = independent;
    }
    public RemoveIdle() {
    }

    @Override
    protected DataSet runFilter(DataSet ds) {

        logger.debug("Applying filter " + this.getClass().getSimpleName());
        return ds.substractFromColumn(independent, independent, idle);
    }


}
