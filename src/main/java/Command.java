import java.util.Arrays;

public class Command {
    private String[] block;
    private String type;

    public Command(String[] block) {
        this.block = block;
        this.type = block[0];
    }
    private String[] todoArgument() {
        String[] ans = new String[1];
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < block.length; i++) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(block[i]);
        }
        ans[0] = builder.toString();
        return ans;
    }

    private String[] deadlineArgument() {
        String[] ans = new String[2];
        int i = 1;
        StringBuilder builder = new StringBuilder();
        while(i < block.length && !block[i].equals("/by")) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(block[i]);
            i++;
        }
        ans[0] = builder.toString();
        i++;
        builder = new StringBuilder();
        while(i < block.length) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(block[i]);
            i++;
        }
        ans[1] = builder.toString();
        return ans;
    }

    private String[] eventArgument() {
        String[] ans = new String[3];
        int i = 1;
        StringBuilder builder = new StringBuilder();
        while(i < block.length && !block[i].equals("/from")) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(block[i]);
            i++;
        }
        ans[0] = builder.toString();
        i++;
        builder = new StringBuilder();
        while(i < block.length && !block[i].equals("/to")) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(block[i]);
            i++;
        }
        ans[1] = builder.toString();
        i++;
        builder = new StringBuilder();
        while (i < block.length) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(block[i]);
            i++;
        }
        ans[2] = builder.toString();
        return ans;
    }

    public String[] getArgument() {
        switch (type) {
            case "todo":
                return todoArgument();
            case "deadline":
                return deadlineArgument();
            case "event":
                return eventArgument();
            default:
                return new String[]{};
                //exception
        }
    }

    public static void main(String[] args) {
        Command c = new Command("event sleep /from Sunday".split(" "));
        System.out.println(Arrays.toString(c.getArgument()));
    }
}
