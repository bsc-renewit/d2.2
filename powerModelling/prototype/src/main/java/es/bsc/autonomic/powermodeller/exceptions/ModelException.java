package es.bsc.autonomic.powermodeller.exceptions;

/**
 * @author Mauro Canuto (mauro.canuto@bsc.es)
 */
public class ModelException extends RuntimeException {
        public ModelException() {
        }
        public ModelException(String message) {
            super (message);
        }

        public ModelException(Throwable cause) {
            super (cause);
        }

        public ModelException(String message, Throwable cause) {
            super (message, cause);
        }

}

