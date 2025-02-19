package loklok.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Represents an event task with a start time and an end time.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs an instance of Event, which is a subclass of Task.
     *
     * @param description the description of the event
     * @param from the start time of the event, formatted as "yyyy-MM-dd, HH:mm"
     * @param to the end time of the event, formatted as "yyyy-MM-dd, HH:mm"
     * @throws IllegalArgumentException if description, from, or to is null or empty
     * @throws DateTimeParseException if the date format is invalid
     * @throws IllegalArgumentException if the end time is before the start time
     */
    public Event(String description, String from, String to) {
        super(description);
        if (from == null || from.trim().isEmpty()) {
            throw new IllegalArgumentException("Event start time cannot be null or empty");
        }
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("Event end time cannot be null or empty");
        }

        try {
            DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern(DATA_DATETIME_FORMAT);
            this.from = LocalDateTime.parse(from, parseFormat);
            this.to = LocalDateTime.parse(to, parseFormat);

            if (this.to.isBefore(this.from)) {
                throw new IllegalArgumentException("Event end time cannot be before start time");
            }
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Invalid date format. Expected format: "
                    + DATA_DATETIME_FORMAT, e.getParsedString(), e.getErrorIndex());
        }
    }

    @Override
    public String toFormatString() {
        return "E|" + super.toFormatString() + "|" + from.format(DateTimeFormatter.ofPattern(DATA_DATETIME_FORMAT))
                + "|" + to.format(DateTimeFormatter.ofPattern(DATA_DATETIME_FORMAT));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + from.format(DateTimeFormatter.ofPattern(OUTPUT_DATETIME_FORMAT))
                + " to: " + to.format(DateTimeFormatter.ofPattern(OUTPUT_DATETIME_FORMAT)) + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Event temp) {
            return super.equals(temp) && temp.from.equals(this.from) && temp.to.equals(this.to);
        }
        return false;
    }
}
