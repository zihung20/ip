package lolok.exception;

public class LolokException extends RuntimeException {
    private final String message;

    public LolokException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String toString() {
        return "duke.Lolok special Exception: " + this.message;
    }
}
