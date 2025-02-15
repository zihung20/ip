package lolok.command;

import lolok.exception.InvalidCommandException;
import lolok.exception.InvalidDataException;

/**
 * An enumeration of the action commands that can be performed.
 */
public enum Action {
    LIST(0),
    BYE(0),
    MARK(1),
    UNMARK(1),
    DELETE(1),
    TODO(1),
    FIND(1),
    DEADLINE(2),
    EVENT(3);

    //Represents the number of arguments each command can accept
    private final int argumentCount;

    Action(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    public int getArgumentCount() {
        return argumentCount;
    }

    public boolean hasKeyword() {
        return this == DEADLINE || this == EVENT;
    }
    /**
     * Parses a string into the corresponding Enum action type.
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
     */
    public static Action parseCommand(String type) throws InvalidCommandException {
        return switch (type) {
        case "bye" -> Action.BYE;
        case "list" -> Action.LIST;
        case "mark" -> Action.MARK;
        case "unmark" -> Action.UNMARK;
        case "find" -> Action.FIND;
        case "delete" -> Action.DELETE;
        case "todo" -> Action.TODO;
        case "deadline" -> Action.DEADLINE;
        case "event" -> Action.EVENT;
        default -> throw new InvalidCommandException(type);
        };
    }
}
