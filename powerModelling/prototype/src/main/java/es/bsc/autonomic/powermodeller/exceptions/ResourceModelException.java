package es.bsc.autonomic.powermodeller.exceptions;

/**
 * Created by jsubirat on 18/11/14.
 */
public class ResourceModelException extends RuntimeException {
    public ResourceModelException() {
    }

    public ResourceModelException(String message) {
        super (message);
    }

    public ResourceModelException(Throwable cause) {
        super (cause);
    }

    public ResourceModelException(String message, Throwable cause) {
        super (message, cause);
    }
}
