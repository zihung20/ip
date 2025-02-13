package lolok.task;

/**
 *  Represents a task with common properties and behaviors
 */
public abstract class Task {
    public static final String OUTPUT_DATETIME_FORMAT = "MMM d yyyy, HH:mm";
    public static final String DATA_DATETIME_FORMAT = "yyyy-MM-dd, HH:mm";
    private final String description;
    private boolean isDone = false;

    public Task(String description) {
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
}
