package lolok.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     */
    public Deadline(String description, String date) {
        super(description);
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        by = LocalDateTime.parse(date, parseFormat);
    }


    @Override
    public String toFormatString() {
        return "D|" + super.toFormatString() + "|" + by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + by.format(DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm")) + ")";
    }
}
