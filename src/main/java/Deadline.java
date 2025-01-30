import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task{
    private LocalDateTime by;

    public Deadline(String description, String date) {
        super(description);
        DateTimeFormatter parseFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");
        this.by = LocalDateTime.parse(date, parseFormat);
    }

    @Override
    public String toFormatString() {
        return "D|" + super.toFormatString() + "|"
                + this.by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " +
                this.by.format(DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm")) + ")";
    }
}
