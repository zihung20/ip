package lolok.task;

/**
 * Represents a Todo class that models a task with no specific deadline.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo instance, which is a subclass of Task
     *
     * @param description the description of the todo task
     * @throws IllegalArgumentException if description is null or empty
     */
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Todo)) {
            return false;
        }
        return super.equals(obj);
    }
}
