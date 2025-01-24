import java.util.ArrayList;
import java.util.Arrays;

public class Command {
    private String[] blocks;
    private String type;

    public Command(String[] block) {
        this.blocks = block;
        this.type = block[0];
    }
    private String[] todoArgument() {
        String[] ans = new String[1];
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < blocks.length; i++) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(blocks[i]);
        }
        ans[0] = builder.toString();
        return ans;
    }

    private String[] deadlineArgument() {
        String[] ans = new String[2];
        int i = 1;
        StringBuilder builder = new StringBuilder();
        while(i < blocks.length && !blocks[i].equals("/by")) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(blocks[i]);
            i++;
        }
        ans[0] = builder.toString();
        i++;
        builder = new StringBuilder();
        while(i < blocks.length) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(blocks[i]);
            i++;
        }
        ans[1] = builder.toString();
        return ans;
    }

    private String[] eventArgument() {
        String[] ans = new String[3];
        int i = 1;
        StringBuilder builder = new StringBuilder();
        while(i < blocks.length && !blocks[i].equals("/from")) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(blocks[i]);
            i++;
        }
        ans[0] = builder.toString();
        i++;
        builder = new StringBuilder();
        while(i < blocks.length && !blocks[i].equals("/to")) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(blocks[i]);
            i++;
        }
        ans[1] = builder.toString();
        i++;
        builder = new StringBuilder();
        while (i < blocks.length) {
            if(!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(blocks[i]);
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

    public String[] getArgument (int count) throws IncorrectArgumentNumberException{
        ArrayList<String> ans = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for(int i = 1; i < blocks.length; i++) {
            if(blocks[i].startsWith("/")) {
                ans.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if(!builder.isEmpty()) {
                    builder.append(" ");
                }
                builder.append(blocks[i]);
            }
        }
        if(!builder.isEmpty()) {
            ans.add(builder.toString());
        }
        if(ans.size() != count) {
            throw new IncorrectArgumentNumberException("The number of arguments for "
                    + type + " is not as expected!");
        }
        return ans.toArray(new String[]{});
    }

    public String getType() {
        return this.type;
    }

    public static void main(String[] args) {
        Command c = new Command("mark 1".split(" "));
        System.out.println(Arrays.toString(c.getArgument(3)));
    }
}
