package es.bsc.autonomic.powermodeller.exceptions;

/**
 * Created by jsubirat on 18/11/14.
 */
public class DataSetException extends RuntimeException {
    public DataSetException () {
    }

    public DataSetException (String message) {
        super (message);
    }

    public DataSetException (Throwable cause) {
        super (cause);
    }

    public DataSetException (String message, Throwable cause) {
        super (message, cause);
    }
}
