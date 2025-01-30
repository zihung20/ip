package duke;

public enum Action {
    LIST(0),
    MARK(1),
    UNMARK(1),
    TODO(1),
    DEADLINE(2),
    EVENT(3),
    DELETE(1);

    private final int argumentCount;
    Action(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    public int getArgumentCount() {
        return this.argumentCount;
    }

    public static Action parseData(String type) throws InvalidDataException{
        return switch (type) {
            case "T" -> Action.TODO;
            case "D" -> Action.DEADLINE;
            case "E" -> Action.EVENT;
            default -> throw new InvalidDataException("Unknown duke.Action: " + type);
        };
    }
}
