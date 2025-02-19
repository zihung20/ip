package loklok.command;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents standard argument positions when parsing and executing commands.
 * Each keyword maps to a specific position in the argument array.
 */
public enum Keyword {
    FROM(1, "/from"),
    TO(2, "/to"),
    BY(1, "/by"),
    UNKNOWN(-1, "");
    // Static map for efficient keyword lookup by text
    private static final Map<String, Keyword> keywordMap = new HashMap<>();
    private final int position;
    private final String textRepresentation;

    static {
        for (Keyword keyword : values()) {
            if (!keyword.textRepresentation.isEmpty()) {
                keywordMap.put(keyword.textRepresentation, keyword);
            }
        }
    }

    /**
     * Constructs a keyword with its standard position.
     *
     * @param position The standard position in the argument array
     * @param textRepresentation How this keyword appears in commands (e.g., "/by")
     */
    Keyword(int position, String textRepresentation) {
        this.position = position;
        this.textRepresentation = textRepresentation;
    }

    /**
     * Gets the standard position for this keyword.
     *
     * @return The position index where arguments for this keyword should go
     */
    public int getPosition() {
        return position;
    }

    /**
     * Parses the given string into a corresponding Keyword enum that represents
     * the position of the standard command when executing.
     *
     * @param string The string to parse, represents the keyword in the typed command.
     * @return Keyword enum that corresponds to the parsed keyword string
     */
    public static Keyword parseKeyword(String string) {
        return keywordMap.getOrDefault(string.toLowerCase(), UNKNOWN);
    }
}
