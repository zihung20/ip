public class Deadline extends Task{
    private String by;

    public Deadline(String description, String date) {
        super(description);
        this.by = date;
    }

    @Override
    public String toFormatString() {
        return "D|" + super.toFormatString() + "|" + this.by;
    }
    
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
