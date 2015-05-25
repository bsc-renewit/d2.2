package es.bsc.autonomic.powermodeller.exceptions;

/**
 * Created by jsubirat on 18/11/14.
 */
public class VariableParserException extends RuntimeException {
    public VariableParserException() {
    }

    public VariableParserException(String message) {
        super (message);
    }

    public VariableParserException(Throwable cause) {
        super (cause);
    }

    public VariableParserException(String message, Throwable cause) {
        super (message, cause);
    }
}
