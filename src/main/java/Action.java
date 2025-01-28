public enum Action {
    LIST(0),
    MARK(1),
    UNMARK(1),
    TODO(1),
    DEADLINE(2),
    EVENT(2),
    DELETE(1);

    private final int argumentCount;
    Action(int argumentCount) {
        this.argumentCount = argumentCount;
    }

    public int getArgumentCount() {
        return this.argumentCount;
    }
}
