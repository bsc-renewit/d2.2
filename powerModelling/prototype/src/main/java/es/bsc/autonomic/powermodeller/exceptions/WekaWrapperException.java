package es.bsc.autonomic.powermodeller.exceptions;

/**
 * @author Mauro Canuto (mauro.canuto@bsc.es)
 */
public class WekaWrapperException extends RuntimeException {

    public WekaWrapperException() {
    }

    public WekaWrapperException(String message) {
        super(message);
    }

    public WekaWrapperException(Throwable cause) {
        super(cause);
    }

    public WekaWrapperException(String message, Throwable cause) {
        super(message, cause);
    }
}