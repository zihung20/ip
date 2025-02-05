package lolok.command;

import lolok.exception.InvalidDataException;

/**
 * An enumeration of the action commands that can be performed.
 */
public enum Action {
    LIST(0),
    MARK(1),
    UNMARK(1),
    TODO(1),
    DEADLINE(2),
    EVENT(3),
    DELETE(1);

    //Represents the number of arguments each command can accept
    private final int argumentCount;

    Action(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    public int getArgumentCount() {
        return this.argumentCount;
    }

    /**
     * parse a string type data to Enum type of action
     * @param type the string representation of the type
     * @return Action type
     * @throws InvalidDataException if the string type is not known
     */
    public static Action parseData(String type) throws InvalidDataException {
        return switch (type) {
        case "T" -> Action.TODO;
        case "D" -> Action.DEADLINE;
        case "E" -> Action.EVENT;
        default -> throw new InvalidDataException("Unknown duke.Action: " + type);
        };
    }
}
