//code refined by claude
package lolok.command;

import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

import lolok.exception.IncorrectArgumentNumberException;
import lolok.exception.InvalidCommandException;
import lolok.exception.LolokException;
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
     * @throws InvalidCommandException if the command cannot be parsed
     */
    public Command(String[] block) throws InvalidCommandException {
        if (block.length == 0) {
            throw new InvalidCommandException("Empty command");
        }
        this.blocks = block;
        type = Action.parseCommand(block[0]);
    }

    /**
     * Gets argument array based on command type and expected number of arguments.
     *
     * @param count Expected number of arguments
     * @param type The action type
     * @return An array of arguments in standard positions
     * @throws IncorrectArgumentNumberException if argument count doesn't match expected
     */
    private String[] getArgument(int count, Action type) throws IncorrectArgumentNumberException {
        // There must be at least one argument for multiple argument commands
        assert count > 0;
        // type should be instanceof Action, if not, valid value is only null
        assert type != null;

        // Check if minimum arguments are present
        if (blocks.length < 2) {
            throw new IncorrectArgumentNumberException("Missing arguments: expected " + count
                    + ", got " + (blocks.length - 1));
        }

        if (type.hasKeyword()) {
            return getArgumentWithKeyword(count);
        } else {
            assert count == 1 : "Commands without keywords should only have 1 argument";
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
        Map<String, Keyword> map = parseKeywordArguments(numberOfArguments);
        return assembleKeywordArguments(numberOfArguments, map);
    }

    /**
     * Parses the command blocks to extract keyword-argument pairs.
     *
     * @param expectedArgumentCount The expected number of arguments
     * @return A map of argument values to their corresponding keywords
     * @throws IncorrectArgumentNumberException if argument count doesn't match expected
     */
    private Map<String, Keyword> parseKeywordArguments(int expectedArgumentCount)
            throws IncorrectArgumentNumberException {
        StringBuilder builder = new StringBuilder();
        Map<String, Keyword> map = new HashMap<>();

        // Find the first keyword
        int index = 2;
        while (index < blocks.length && Keyword.parseKeyword(blocks[index]) == Keyword.UNKNOWN) {
            index++;
        }

        // If we reached the end without finding a keyword, all text is description
        if (index >= blocks.length && expectedArgumentCount > 1) {
            throw new IncorrectArgumentNumberException("Missing keyword arguments: expected "
                    + (expectedArgumentCount - 1) + " keywords");
        }

        Keyword previousKeyword = null;
        if (index < blocks.length) {
            previousKeyword = Keyword.parseKeyword(blocks[index]);
        }
        index++;

        // Process remaining arguments
        while (index < blocks.length) {
            Keyword current = Keyword.parseKeyword(blocks[index]);
            if (current != Keyword.UNKNOWN) {
                // We found a new keyword, save the accumulated argument with previous keyword
                map.put(builder.toString().trim(), previousKeyword);
                previousKeyword = current;
                builder = new StringBuilder();
            } else {
                // Add current word to argument string
                if (!builder.isEmpty()) {
                    builder.append(" ");
                }
                builder.append(blocks[index]);
            }
            index++;
        }

        // Add the last argument if exists
        if (!builder.isEmpty() && previousKeyword != null) {
            map.put(builder.toString().trim(), previousKeyword);
        }

        // Validate we got the expected number of keyword arguments
        if (map.size() != expectedArgumentCount - 1) {
            throw new IncorrectArgumentNumberException("Incorrect number of keyword arguments: expected "
                    + (expectedArgumentCount - 1) + ", got " + map.size());
        }

        return map;
    }

    /**
     * Builds the final array of arguments from the parsed map.
     *
     * @param numberOfArguments The total number of arguments expected
     * @param map The map of arguments to keywords
     * @return The final array of arguments in standard positions
     */
    private String[] assembleKeywordArguments(int numberOfArguments, Map<String, Keyword> map) {
        String[] result = new String[numberOfArguments];
        result[0] = getDescription();

        for (Map.Entry<String, Keyword> entry : map.entrySet()) {
            int position = entry.getValue().getPosition();
            if (position >= 0 && position < numberOfArguments) {
                result[position] = entry.getKey();
            }
        }

        return result;
    }

    /**
     * Extracts the description part of the command (text before the first keyword).
     *
     * @return The description string
     */
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

        return builder.toString().trim();
    }

    /**
     * Executes the command accordingly, using the environment passed as parameters.
     *
     * @param taskList the TaskList instance to process the command
     */
    public void executeCommand(TaskList taskList) {
        try {
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
        } catch (LolokException e) {
            Ui.printMessage("Error executing command: " + e.getMessage());
        }
    }

    /**
     * Executes commands that require one or more arguments.
     *
     * @param taskList The TaskList to operate on
     */
    private void executeMultipleArgumentCommand(TaskList taskList) {
        assert type != null;

        try {
            String[] arg = getArgument(type.getArgumentCount(), type);

            switch (type) {
            case MARK, UNMARK -> {
                try {
                    int taskIndex = Integer.parseInt(arg[0]);
                    taskList.markTask(taskIndex, type == Action.MARK);
                } catch (NumberFormatException e) {
                    throw new InvalidCommandException("Task index must be a number: " + arg[0]);
                }
            }
            case DELETE -> {
                try {
                    int taskIndex = Integer.parseInt(arg[0]);
                    taskList.deleteTask(taskIndex);
                } catch (NumberFormatException e) {
                    throw new InvalidCommandException("Task index must be a number: " + arg[0]);
                }
            }
            case TODO -> taskList.addToList(new Todo(arg[0]));
            case DEADLINE -> taskList.addToList(new Deadline(arg[0], arg[1]));
            case EVENT -> taskList.addToList(new Event(arg[0], arg[1], arg[2]));
            case FIND -> taskList.searchTask(arg[0]);
            default -> throw new InvalidCommandException("Invalid Command. Don't know what '" + blocks[0] + "' means");
            }
        } catch (InvalidCommandException | IncorrectArgumentNumberException | DateTimeParseException
                 | IllegalArgumentException e) {
            Ui.printErrorMessage(e.toString());
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
