package lolok.command;

import java.util.ArrayList;
import java.util.List;

import lolok.Storage;
import lolok.exception.IncorrectArgumentNumberException;
import lolok.exception.InvalidCommandException;
import lolok.task.Deadline;
import lolok.task.Event;
import lolok.task.TaskList;
import lolok.task.Todo;
import lolok.ui.Ui;

/**
 * Represents a command that executes and processes the given command.
 */
public class Command {
    private String[] blocks;
    private String type;
    private boolean isExit = false;

    /**
     * Constructs a new instance of a command that processes the given arguments.
     *
     * @param block an array of strings, where each index represents an argument
     */
    public Command(String[] block) {
        this.blocks = block;
        this.type = block[0];
    }

    private String[] getArgument(int count) throws IncorrectArgumentNumberException {
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
    /**
     * Executes the command accordingly, using the environment passed as parameters.
     *
     * @param taskList the TaskList instance to process the command
     * @param ui the UI instance to handle user interactions
     * @param storage the Storage instance to manage data persistence
     */
    public void executeCommand(TaskList taskList, Ui ui, Storage storage) {
        switch (blocks[0]) {
        case "bye":
            isExit = true;
            break;
        case "list":
            taskList.printList();
            break;
        default:
            executeMultipleArgumentCommand(taskList);
        }
    }

    private void executeMultipleArgumentCommand(TaskList taskList) {
        try {
            //https://www.geeksforgeeks.org/list-contains-method-in-java-with-examples/
            if (List.of("mark", "unmark", "delete").contains(type)) {
                String[] arg = this.getArgument(1);
                if (type.equals("delete")) {
                    taskList.deleteTask(Integer.parseInt(arg[0]));

                } else {
                    taskList.markTask(Integer.parseInt(arg[0]), type.equals("mark"));
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
            } else if (type.equals("find")) {
                String[] arg = this.getArgument(1);
                taskList.searchTask(arg[0]);
            } else {
                throw new InvalidCommandException(type);
            }
        } catch (InvalidCommandException e) {
            Ui.printMessage(e.toString());
        } catch (IncorrectArgumentNumberException e) {
            Ui.printMessage(e.toString());
        }
    }

    /**
     * Returns true if this command is an exit command.
     *
     * @return true if the command is an exit command, otherwise false
     */
    public boolean isExit() {
        return this.isExit;
    }
}
