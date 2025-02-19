package loklok.exception;

/**
 * An exception related to errors caused by chatbot.
 */
public class LokLokException extends RuntimeException {

    /**
     * Creates an exception object related to the chatbot.
     *
     * @param message The error message of the exception.
     */
    public LokLokException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "LokLokException: " + getMessage();
    }
}
