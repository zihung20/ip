package loklok.exception;

/**
 * Throws an exception if the keyword provided by the user is invalid.
 */
public class InvalidKeywordException extends LokLokException {
    public InvalidKeywordException(String message) {
        super(message);
    }
}
