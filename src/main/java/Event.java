import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, String from, String to) {
        super(description);
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        this.from = LocalDateTime.parse(from, parseFormat);
        this.to = LocalDateTime.parse(to, parseFormat);
    }

    @Override
    public String toFormatString() {
        return "E|" + super.toFormatString() + "|" + this.from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"))
                + "|" + this.to.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + this.from.format(DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"))
                + " to: " + this.to.format(DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"))  +  ")";
    }
}
