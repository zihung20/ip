package lolok.command;



/**
 * represent the standard argument position when parsing and execute
 */
public enum Keyword {
    FROM(1),
    TO(2),
    BY(1),
    UNKNOWN(-1);
    private final int position;

    Keyword(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Parses the given string into a corresponding Keyword enum that represents
     * the position of the standard command when executing.
     * @param string The string to parse, represent the keyword in the typed command.
     * @return Keyword enum that corresponds to the parsed keyword string
     */
    public static Keyword parseKeyword(String string) {
        return switch (string) {
        case "/from" -> FROM;
        case "/to" -> TO;
        case "/by" -> BY;
        default -> UNKNOWN;
        };
    }
}
