package es.bsc.autonomic.powermodeller.exceptions;

/**
 * Created by jsubirat on 18/11/14.
 */
public class PowerModelEstimatorException extends RuntimeException {
    public PowerModelEstimatorException() {
    }

    public PowerModelEstimatorException(String message) {
        super (message);
    }

    public PowerModelEstimatorException(Throwable cause) {
        super (cause);
    }

    public PowerModelEstimatorException(String message, Throwable cause) {
        super (message, cause);
    }
}
