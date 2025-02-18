package lolok.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a deadline task with a deadline time.
 */
public class Deadline extends Task {
    private final LocalDateTime by;

    /**
     * Constructs a Deadline instance, which is a subclass of Task.
     *
     * @param description the description of the deadline event
     * @param date the date of the deadline event, formatted as "yyyy-MM-dd, HH:mm"
     * @throws IllegalArgumentException if description is null or empty
     * @throws DateTimeParseException if the date format is invalid
     */
    public Deadline(String description, String date) {
        super(description);
        if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("Deadline date cannot be null or empty");
        }

        try {
            DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern(DATA_DATETIME_FORMAT);
            by = LocalDateTime.parse(date, parseFormat);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format. Expected format: "
                    + DATA_DATETIME_FORMAT, e.getParsedString(), e.getErrorIndex());
        }
    }


    @Override
    public String toFormatString() {
        return "D|" + super.toFormatString() + "|" + by.format(DateTimeFormatter.ofPattern(DATA_DATETIME_FORMAT));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern(OUTPUT_DATETIME_FORMAT)) + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Deadline temp) {
            return super.equals(temp) && temp.by.equals(this.by);
        }
        return false;
    }
}
