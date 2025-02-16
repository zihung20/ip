package lolok.exception;

/**
 * An exception related to errors caused by chatbot.
 */
public class LolokException extends RuntimeException {
    private final String message;

    /**
     * Creates an exception object related to the chatbot.
     *
     * @param message The error message of the exception.
     */
    public LolokException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "LolokException: " + this.message;
    }
}
