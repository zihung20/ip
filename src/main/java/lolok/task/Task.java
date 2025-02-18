package lolok.task;

import java.util.Objects;

/**
 *  Represents a task with common properties and behaviors
 */
public abstract class Task {
    public static final String OUTPUT_DATETIME_FORMAT = "MMM d yyyy, HH:mm";
    public static final String DATA_DATETIME_FORMAT = "yyyy-MM-dd, HH:mm";
    private final String description;
    private boolean isDone = false;

    /**
     * Constructs a task with the given description.
     *
     * @param description the description of the task
     * @throws IllegalArgumentException if description is null or empty
     */
    public Task(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Task description cannot be null or empty");
        }
        this.description = description;
    }

    public boolean matchKeyword(String keyword) {
        return description.contains(keyword);
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return isDone ? "X" : " "; // mark done task with X
    }

    /**
     * Returns a formatted string that follows a standard format, e.g., "x|x|...".
     *
     * @return a formatted string for storing data
     */
    public String toFormatString() {
        return getStatusIcon() + "|" + description;
    }

    /**
     * Returns a human-readable string representation of the task, e.g., "[X] example task".
     *
     * @return a human-readable string representation of the task
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Task temp) {
            return Objects.equals(temp.description, this.description);
        }
        return false;
    }
}
