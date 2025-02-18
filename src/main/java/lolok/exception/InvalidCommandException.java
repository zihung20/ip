package lolok.exception;

/**
 * Throws an exception when the command provided by the user is invalid.
 */
public class InvalidCommandException extends LolokException {

    /**
     * Creates an exception that indicates the command provided by the user is invalid.
     * @param commandString the string of the command provided by the user
     */
    public InvalidCommandException(String commandString) {
        super(commandString);
    }
}
