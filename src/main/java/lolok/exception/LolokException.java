package lolok.exception;

/**
 * An exception related to errors caused by chatbot.
 */
public class LolokException extends RuntimeException {

    /**
     * Creates an exception object related to the chatbot.
     *
     * @param message The error message of the exception.
     */
    public LolokException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "LolokException: " + getMessage();
    }
}
