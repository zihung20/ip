package lolok.exception;

/**
 * Throws an exception if the keyword provided by the user is invalid.
 */
public class InvalidKeywordException extends LolokException {
    public InvalidKeywordException(String message) {
        super(message);
    }
}
