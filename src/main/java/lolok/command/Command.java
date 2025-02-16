package lolok.command;

import java.util.HashMap;
import java.util.Map;

import lolok.exception.IncorrectArgumentNumberException;
import lolok.exception.InvalidCommandException;
import lolok.storage.Storage;
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
     * syntax: command description /keyword argument1 /keyword argument2 ...
     * @param block an array of strings, where each index represents an argument
     */
    public Command(String[] block) throws InvalidCommandException {
        this.blocks = block;
        type = Action.parseCommand(block[0]);
    }

    private String[] getArgument(int count, Action type) throws IncorrectArgumentNumberException {
        //There must be at least one argument for the those multiple argument command
        assert count > 0;
        // type should be instanceof Action, if not, valid value is only null
        assert type != null;
        if (blocks.length < 2) {
            throw new IncorrectArgumentNumberException("Incorrect number of argument: " + blocks.length);
        }
        if (type.hasKeyword()) {
            return getArgumentWithKeyword(count);
        } else {
            assert count == 1 : "the argument don't have key word should only have 1 argument";
            return new String[]{getDescription()};
        }
    }
    /**
     * Retrieves arguments that are explicitly associated with keywords and places them in standardized positions.
     *
     * @param numberOfArguments The expected number of arguments.
     * @return An array of strings representing the arguments in their correct positions.
     * @throws IncorrectArgumentNumberException If the number of provided arguments does not match the expected count
     *         or if the number of keyword arguments is incorrect.
     */
    private String[] getArgumentWithKeyword(int numberOfArguments) throws IncorrectArgumentNumberException {
        Map<String, Keyword> map = getStringKeywordMap(numberOfArguments);
        return getKeywordArgument(numberOfArguments, map);
    }

    private Map<String, Keyword> getStringKeywordMap(int numberOfArguments) {
        StringBuilder builder = new StringBuilder();
        Map<String, Keyword> map = new HashMap<>();
        int index = 2;
        //find the first keyword
        while (index < blocks.length && Keyword.parseKeyword(blocks[index]) == Keyword.UNKNOWN) {
            index++;
        }
        Keyword previousKeyword = null;
        if (index < blocks.length) {
            previousKeyword = Keyword.parseKeyword(blocks[index]);
        }
        index++;
        while (index < blocks.length) {
            Keyword current = Keyword.parseKeyword(blocks[index]);
            if (current != Keyword.UNKNOWN) {
                map.put(builder.toString(), previousKeyword);
                previousKeyword = current;
                builder = new StringBuilder();
            } else {
                if (!builder.isEmpty()) {
                    builder.append(" ");
                }
                builder.append(blocks[index]);
            }
            index++;
        }
        if (!builder.isEmpty()) {
            map.put(builder.toString(), previousKeyword);
        }
        if (map.size() != numberOfArguments - 1) {
            throw new IncorrectArgumentNumberException("Expected argument keyword numbers: " + numberOfArguments
                    + "Got: " + map.size() + 1);
        }
        return map;
    }
    private String[] getKeywordArgument(int numberOfArguments, Map<String, Keyword> map) {
        String[] ans = new String[numberOfArguments];
        ans[0] = getDescription();
        for (Map.Entry<String, Keyword> entry : map.entrySet()) {
            ans[entry.getValue().getPosition()] = entry.getKey();
        }
        return ans;
    }
    private String getDescription() {
        int i = 1;
        StringBuilder builder = new StringBuilder();
        while (i < blocks.length && Keyword.parseKeyword(blocks[i]) == Keyword.UNKNOWN) {
            if (!builder.isEmpty()) {
                builder.append(" ");
            }
            builder.append(blocks[i]);
            i++;
        }
        return builder.toString();
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
            String[] arg = getArgument(type.getArgumentCount(), type);
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
