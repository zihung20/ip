public class InvalidCommandException extends LolokException {
    private String message;
    public InvalidCommandException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Invalid Command. Don't know what '" + this.message + "' means";
    }
}
