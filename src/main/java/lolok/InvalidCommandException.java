package lolok;

public class InvalidCommandException extends LolokException {
    private String type;
    public InvalidCommandException(String message) {
        super(message);
        this.type = message;
    }

    @Override
    public String toString() {
        return "Invalid duke.Command. Don't know what '" + this.type + "' means";
    }
}
