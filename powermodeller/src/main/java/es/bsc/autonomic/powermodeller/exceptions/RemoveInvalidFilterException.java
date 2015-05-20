package es.bsc.autonomic.powermodeller.exceptions;

/**
 * Created by jsubirat on 18/11/14.
 */
public class RemoveInvalidFilterException extends RuntimeException {
    public RemoveInvalidFilterException() {
    }

    public RemoveInvalidFilterException(String message) {
        super (message);
    }

    public RemoveInvalidFilterException(Throwable cause) {
        super (cause);
    }

    public RemoveInvalidFilterException(String message, Throwable cause) {
        super (message, cause);
    }
}
