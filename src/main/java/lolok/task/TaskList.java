package lolok.task;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import lolok.command.Action;
import lolok.exception.IncorrectArgumentNumberException;
import lolok.exception.InvalidDataException;
import lolok.ui.Ui;
/**
 * Represents a list of tasks with functionality to add, print, mark, and delete tasks.
 */
public class TaskList {
    private final List<Task> list = new ArrayList<>();
    /**
     * Creates an instance of a list of tasks that can perform adding, printing, marking, and deleting.
     *
     * @param stringList a list of formatted strings to convert to task instances
     */
    public TaskList(List<String> stringList) {
        for (String s : stringList) {
            loadTask(s);
        }
    }

    /**
     * Loads a task into the task list, which represents a list of tasks.
     *
     * @param taskString - String representation of a task,
     *                     formatted as `[Type]|[Status]|[Name]|[Argument1]|[Argument2]|...`
     */
    private void loadTask(String taskString) {
        try {
            String[] stringArray = taskString.split("\\|");
            if (stringArray[0].equals("C")) {
                //comment, do nothing
                return;
            }
            Action action = Action.parseData(stringArray[0]);
            if (action.getArgumentCount() + 2 != stringArray.length) {
                throw new IncorrectArgumentNumberException("Incorrect argument numbers for action: " + taskString);
            }
            Task addTask = switch (action) {
            case TODO -> new Todo(stringArray[2]);
            case DEADLINE -> new Deadline(stringArray[2], stringArray[3]);
            case EVENT -> new Event(stringArray[2], stringArray[3], stringArray[4]);
            default -> throw new InvalidDataException("Unknown action: " + action);
            };
            list.add(addTask);
            addTask.setDone(stringArray[1].equals("X"));
        } catch (InvalidDataException e) {
            Ui.printErrorMessage("Some errors occur while reading data. " + e.getMessage());
        } catch (IllegalArgumentException | DateTimeParseException e) {
            Ui.printErrorMessage(e.toString());
        }
    }

    /**
     * Adds the task instance to the list.
     *
     * @param task the task to be added to the list
     */
    public void addToList(Task task) {
        if (isDuplicate(task)) {
            Ui.printMessage(task.toString(), "The task is duplicated and we don't allow duplicate task");
            return;
        }
        list.add(task);
        String message = "Got it. I've added this task:\n" + task.toString() + "\n";
        message += String.format("Now you have %d tasks in the list.%n", list.size());
        Ui.printMessage(message);
    }
    private boolean isDuplicate(Task task) {
        return list.stream().anyMatch(t -> t.equals(task));
    }
    /**
     * Prints the tasks in the list.
     */
    public void printList() {
        StringBuilder builder = new StringBuilder("Here are the tasks in your list: \n");
        IntStream.range(0, list.size())
                 .forEach(i -> builder.append(String.format("%d. %s \n", (i + 1), list.get(i).toString())));
        Ui.printMessage(builder.toString());
    }

    /**
     * Deletes the task from the list, with the index starting from 1.
     *
     * @param index the index of the task to be deleted
     */
    public void deleteTask(int index) {
        index = index - 1;
        try {
            Task task = list.get(index);
            list.remove(index);
            String message = "Noted. I've removed this task:\n";
            String taskAmount = String.format("Now you have %d tasks in the list.", list.size());
            Ui.printMessage(message + task.toString() + "\n" + taskAmount);
        } catch (IndexOutOfBoundsException e) {
            Ui.printErrorMessage("Index " + (index + 1) + " is invalid or doesn't have any task.");
        }
    }

    /**
     * Marks the task in the list as done or undone, starting from index 1.
     *
     * @param index  the index of the task to be marked
     * @param isDone true to mark the task as done, false to mark it as undone
     */
    public void markTask(int index, boolean isDone) {
        index--;
        Task task = this.list.get(index);
        task.setDone(isDone);
        String message = isDone ? "Nice! I've marked this task as done:" : "OK, I've marked this task as not done yet:";
        Ui.printMessage(message + "\n" + task);
    }

    public List<Task> getList() {
        return list;
    }

    /**
     * Searches and prints the tasks that match the provided keyword.
     * @param keyword The keyword to search for in the task descriptions.
     */
    public void searchTask(String keyword) {
        List<Task> ans = list.stream().filter(task -> task.matchKeyword(keyword)).toList();
        StringBuilder builder = new StringBuilder("Here are the matching tasks in your list:\n");
        IntStream.range(0, ans.size())
                 .forEach(i -> builder.append(String.format("%d. %s \n", (i + 1), ans.get(i).toString())));
        Ui.printMessage(builder.toString());
    }
}
