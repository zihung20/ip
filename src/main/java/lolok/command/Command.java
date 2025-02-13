package lolok.command;

import java.util.ArrayList;

import lolok.storage.Storage;
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
    private final String[] blocks;
    private final Action type;
    private boolean isExit = false;

    /**
     * Constructs a new instance of a command that processes the given arguments.
     *
     * @param block an array of strings, where each index represents an argument
     */
    public Command(String[] block) throws InvalidCommandException {
        this.blocks = block;
        type = Action.parseCommand(block[0]);
    }

    private String[] getArgument(int count) throws IncorrectArgumentNumberException {
        //There must be at least one argument for the those multiple argument command
        assert count > 0;

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
        switch (type) {
        case BYE:
            isExit = true;
            break;
        case LIST:
            taskList.printList();
            break;
        default:
            executeMultipleArgumentCommand(taskList);
        }
    }

    private void executeMultipleArgumentCommand(TaskList taskList) {
        assert type != null;
        try {
            String[] arg = getArgument(type.getArgumentCount());
            switch (type) {
            case MARK, UNMARK -> {
                int taskIndex = Integer.parseInt(arg[0]);
                taskList.markTask(taskIndex, "mark".equals(blocks[0]));
            }
            case DELETE -> {
                int taskIndex = Integer.parseInt(arg[0]);
                taskList.deleteTask(taskIndex);
            }
            case TODO -> taskList.addToList(new Todo(arg[0]));
            case DEADLINE -> taskList.addToList(new Deadline(arg[0], arg[1]));
            case EVENT -> taskList.addToList(new Event(arg[0], arg[1], arg[2]));
            case FIND -> taskList.searchTask(arg[0]);
            //should not have invalid command
            default -> throw new InvalidCommandException(blocks[0]);
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
        return isExit;
    }
}
