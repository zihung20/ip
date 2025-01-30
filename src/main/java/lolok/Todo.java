package lolok;

import java.util.Objects;

public class Todo extends Task{
    private final String description;

    /**
     * Constructs a Todo instance, which is a subclass of Task
     *
     * @param description the description of the todo task
     */
    public Todo(String description) {
        super(description);
        this.description = description;
    }

    @Override
    public String toFormatString() {
        return "T|" + super.toFormatString();
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Todo temp) {
            return Objects.equals(temp.description, this.description);
        }
        return false;
    }
}
