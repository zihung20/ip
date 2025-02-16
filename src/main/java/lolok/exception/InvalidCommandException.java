package lolok.exception;

/**
 * Throws an exception when the command provided by the user is invalid.
 */
public class InvalidCommandException extends LolokException {
    private String type;

    public InvalidCommandException(String message) {
        super(message);
        this.type = message;
    }

    @Override
    public String toString() {
        return "Invalid Command. Don't know what '" + this.type + "' means";
    }
}
