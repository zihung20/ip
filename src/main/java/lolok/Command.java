package lolok;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private String[] blocks;
    private String type;
    private boolean isExit = false;

    public Command(String[] block) {
        this.blocks = block;
        this.type = block[0];
    }

    public String[] getArgument(int count) throws IncorrectArgumentNumberException {
        ArrayList<String> ans = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < blocks.length; i++) {
            if (blocks[i].startsWith("/")) {
                ans.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (!builder.isEmpty()) {
                    builder.append(" ");
                }
                builder.append(blocks[i]);
            }
        }
        if (!builder.isEmpty()) {
            ans.add(builder.toString());
        }
        if (ans.size() != count) {
            throw new IncorrectArgumentNumberException("The number of arguments for "
                    + type + " is not as expected!");
        }
        return ans.toArray(new String[]{});
    }

    public String getType() {
        return this.type;
    }

    public void executeCommand(TaskList taskList, Ui ui, Storage storage) {
        switch (blocks[0]) {
        case "bye":
            isExit = true;
            break;
        case "list":
            taskList.printList();
            break;
        default:
            multipleArgumentCommand(taskList);
        }
    }

    private void multipleArgumentCommand(TaskList taskList) {
        try {
            String type = this.getType();
            //https://www.geeksforgeeks.org/list-contains-method-in-java-with-examples/
            if (List.of("mark", "unmark", "delete").contains(type)) {
                String[] arg = this.getArgument(1);
                if (type.equals("delete")) {
                    taskList.deleteTask(Integer.parseInt(arg[0]) - 1);
                } else {
                    taskList.markTask(Integer.parseInt(arg[0]) - 1, this.getType().equals("mark"));
                }
            } else if (type.equals("todo")) {
                String[] arg = this.getArgument(1);
                taskList.addToList(new Todo(arg[0]));
            } else if (type.equals("deadline")) {
                String[] arg = this.getArgument(2);
                taskList.addToList(new Deadline(arg[0], arg[1]));
            } else if (type.equals("event")) {
                String[] arg = this.getArgument(3);
                taskList.addToList(new Event(arg[0], arg[1], arg[2]));
            } else {
                throw new InvalidCommandException(type);
            }
        } catch (InvalidCommandException e) {
            Ui.printMessageBlock(e.toString());
        } catch (IncorrectArgumentNumberException e) {
            Ui.printMessageBlock(e.toString());
        }
    }

    public boolean isExit() {
        return this.isExit;
    }
}
