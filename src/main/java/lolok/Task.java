package lolok;

public class Task {
    private String description;
    private boolean isDone = false;

    public Task(String description) {
        this.description = description;
    }

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (this.isDone ? "X" : " "); // mark done task with X
    }

    /**
     *
     * @return return a format string that follow a standard. e.g x|x|..
     */
    public String toFormatString() {
        return this.getStatusIcon() + "|" + this.description;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() +"] " + this.description;
    }
}
