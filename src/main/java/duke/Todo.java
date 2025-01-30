package duke;

public class Todo extends Task{

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toFormatString() {
        return "T|" + super.toFormatString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
