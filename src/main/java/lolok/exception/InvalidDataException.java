package lolok.exception;

/**
 * throw exception if the argument is not good or the data store in the file cannot be interpret
 */
public class InvalidDataException extends LolokException {
    public InvalidDataException(String message) {
        super(message);
    }
}
