package lolok;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final List<Task> list;

    public TaskList(List<String> stringList) {
        this.list = new ArrayList<>();
        for(String s: stringList) {
            loadToApplication(s);
        }
    }

    private void loadToApplication(String taskString) {
        //[Type]|[Status]|[Name]|[Argument1]|[Argument2]|..
        try {
            String[] stringArray = taskString.split("\\|");
            if (stringArray[0].equals("C")) {
                //comment, do nothing
                return;
            }
            Action action = Action.parseData(stringArray[0]);
            if (action.getArgumentCount() + 2 != stringArray.length) {
                throw new InvalidDataException("Invalid arguments for action: " + taskString);
            }

            switch (action) {
            case TODO:
                this.list.add(new Todo(stringArray[2]));
                break;
            case DEADLINE:
                this.list.add(new Deadline(stringArray[2], stringArray[3]));
                break;
            case EVENT:
                this.list.add(new Event(stringArray[2], stringArray[3], stringArray[4]));
                break;
            default:
                throw new InvalidDataException("Unsupported action: " + action);
            }
        } catch (InvalidDataException e) {
            System.out.println("Some errors occur while reading data. "+e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("The date format of the data cannot be parsed. " + e.getMessage());
        }
    }

    public List<Task> getList() {
        return this.list;
    }

    public void addToList(Task task) {
        this.list.add(task);
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.printf("Now you have %d tasks in the list.%n", list.size());
    }

    public void printList() {
        System.out.println("Here are the tasks in your list:");
        for(int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i).toString());
        }
    }

    public void deleteTask(int index) {
        try {
            Task task = this.list.get(index);
            this.list.remove(index);
            String message = "Noted. I've removed this task:\n";
            String taskAmount = String.format("Now you have %d tasks in the list.", list.size());
            Ui.printMessageBlock(message + task.toString() + "\n" + taskAmount);
        } catch (IndexOutOfBoundsException e) {
            Ui.printMessageBlock("Oops! Index" + (index+1) + "is invalid or doesn't have any task.");
        }
    }

    public void markTask(int index, boolean isDone) {
        Task task = this.list.get(index);
        task.setDone(isDone);
        String message = isDone
                ? "Nice! I've marked this task as done:"
                :"OK, I've marked this task as not done yet:";
        Ui.printMessageBlock(message + "\n" + task);
    }
}
