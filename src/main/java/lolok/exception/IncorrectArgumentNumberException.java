package lolok.exception;

/**
 * Throws an exception when the number of arguments provided by the user is incorrect.
 */
public class IncorrectArgumentNumberException extends LolokException {
    public IncorrectArgumentNumberException(String message) {
        super(message);
    }
}
