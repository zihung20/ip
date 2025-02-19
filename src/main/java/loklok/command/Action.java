package loklok.command;

import loklok.exception.InvalidCommandException;
import loklok.exception.InvalidDataException;

/**
 * An enumeration of the action commands that can be performed.
 * Commands are grouped by their functional category.
 */
public enum Action {
    // Navigation commands
    LIST(0),
    BYE(0),

    // Task state management commands
    MARK(1),
    UNMARK(1),
    DELETE(1),
    FIND(1),

    // Task creation commands
    TODO(1, false),
    DEADLINE(2, true),
    EVENT(3, true);

    // Represents the number of arguments each command can accept
    private final int argumentCount;
    private final boolean hasKeyword;

    Action(int argumentCount) {
        this(argumentCount, false);
    }
    Action(int argumentCount, boolean hasKeyword) {
        this.argumentCount = argumentCount;
        this.hasKeyword = hasKeyword;
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public boolean hasKeyword() {
        return hasKeyword;
    }

    /**
     * Validates whether the number of arguments is correct for this action.
     *
     * @param argCount The number of arguments provided
     * @return true if the argument count matches what's expected for this action
     */
    public boolean isValidArgumentCount(int argCount) {
        return argCount == argumentCount;
    }

    /**
     * Parses a string into the corresponding Enum action type for data storage.
     *
     * @param type The string representation of the action type.
     * @return The corresponding Action type.
     * @throws InvalidDataException If the provided string does not match any known action type.
     */
    public static Action parseData(String type) throws InvalidDataException {
        return switch (type) {
        case "T" -> Action.TODO;
        case "D" -> Action.DEADLINE;
        case "E" -> Action.EVENT;
        default -> throw new InvalidDataException("Unknown Action: " + type);
        };
    }

    /**
     * Parses a string command type into its corresponding enum action.
     *
     * @param type The string representing the command type, usually the first word of the command.
     * @return The corresponding action enum representing the command action.
     * @throws InvalidCommandException If the provided command is not recognized.
     */
    public static Action parseCommand(String type) throws InvalidCommandException {
        return switch (type.toLowerCase()) {
        case "bye" -> Action.BYE;
        case "list" -> Action.LIST;
        case "mark" -> Action.MARK;
        case "unmark" -> Action.UNMARK;
        case "find" -> Action.FIND;
        case "delete" -> Action.DELETE;
        case "todo" -> Action.TODO;
        case "deadline" -> Action.DEADLINE;
        case "event" -> Action.EVENT;
        default -> throw new InvalidCommandException("Invalid Command. Don't know what '" + type + "' means");
        };
    }
}
