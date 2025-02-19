package loklok.exception;

/**
 * throw exception if the argument is not good or the data store in the file cannot be interpret
 */
public class InvalidDataException extends LokLokException {
    public InvalidDataException(String message) {
        super(message);
    }
}
