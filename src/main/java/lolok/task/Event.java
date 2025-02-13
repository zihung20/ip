package lolok.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
     */
    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern(DATA_DATETIME_FORMAT);
        this.from = LocalDateTime.parse(from, parseFormat);
        this.to = LocalDateTime.parse(to, parseFormat);
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
}
