import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Paths;

public class Lolok {
    private final String name = "Lolok";
    private final String logo = "";
    private final List<Task> list = new ArrayList<>();
    private final String defaultPath = "./data/lolok_data.txt";

    private void greet() {
        printLine();
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
        printLine();
    }

    public void readData() {
        printLine();
        try {
            System.out.println("Reading file from " + this.defaultPath + "...");
            List<String> data = Files.readAllLines(Paths.get(this.defaultPath));
            for(String d : data)   {
                loadToApplication(d);
            }
        } catch (IOException e) {
            System.out.println("We can't read data from the path: " + e.getMessage());
        }
        printLine();
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
                this.list.add(new Task(stringArray[2]));
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

    private void saveList(List<Task> tasks, boolean append) {
        System.out.println("Saving tasks...");
        //FileWriter create file when it does not exist
        try (FileWriter fw = new FileWriter(defaultPath, append)) {
            for (Task t : tasks) {
                fw.write(t.toFormatString() + System.lineSeparator());
            }
        } catch (IOException e) {

            System.out.println("Something wrong when saving the file" + e.getMessage());
        }
    }



    private static void printLine() {
        System.out.println("_".repeat(32));
    }

    private void exit() {
        printLine();
        saveList(list, false);
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }



    private void getUserInput() {
        //https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        try {
            while (!exit) {
                //lowercase can add in the future
                String input = scanner.nextLine();
                Command command = new Command(input.split(" "));
                switch (input) {
                    case "bye":
                        exit = true;
                        this.exit();
                        break;
                    case "list":
                        this.printList();
                        break;
                    default:
                        multipleArgumentCommand(command);
                }
            }
        } catch (LolokException e) {
            System.err.println(e);
        } finally {
            //clean up
            scanner.close();
        }
    }
    private void multipleArgumentCommand(Command command) throws InvalidCommandException{
        try {
            String type = command.getType();
            //https://www.geeksforgeeks.org/list-contains-method-in-java-with-examples/
            if(List.of("mark", "unmark", "delete").contains(type)) {
                String[] arg = command.getArgument(1);
                if(type.equals("delete")) {
                    deleteTask(Integer.parseInt(arg[0]) - 1);
                } else {
                    markTask(Integer.parseInt(arg[0]) - 1, command.getType().equals("mark"));
                }
            } else if(type.equals("todo")) {
                String[] arg = command.getArgument(1);
                addToList(new Todo(arg[0]));
            } else if(type.equals("deadline")){
                String[] arg = command.getArgument(2);
                addToList(new Deadline(arg[0], arg[1]));
            } else if (type.equals("event")) {
                String[] arg = command.getArgument(3);
                addToList(new Event(arg[0], arg[1], arg[2]));
            } else {
                throw new InvalidCommandException(type);
            }
        } catch (LolokException e) {
            printMessageBlock(e.toString());
        }

    }

    private void printMessageBlock(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    private void addToList(Task task) {
        this.list.add(task);
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(task.toString());
        System.out.printf("Now you have %d tasks in the list.%n", list.size());
        printLine();
    }

    private void printList() {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for(int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + "." + list.get(i).toString());
        }
        printLine();
    }

    private void markTask(int index, boolean isDone) {
        Task task = this.list.get(index);
        task.setDone(isDone);
        String message = isDone
                        ? "Nice! I've marked this task as done:"
                        :"OK, I've marked this task as not done yet:";
        printMessageBlock(message + "\n" + task.toString());
    }

    private void deleteTask(int index) {
        try {
            Task task = this.list.get(index);
            this.list.remove(index);
            String message = "Noted. I've removed this task:\n";
            String taskAmount = String.format("Now you have %d tasks in the list.", list.size());
            printMessageBlock(message + task.toString() + "\n" + taskAmount);
        } catch (IndexOutOfBoundsException e) {
            printMessageBlock("Oops! Index" + (index+1) + "is invalid or doesn't have any task.");
        }
    }

    public static void main(String[] args) {
       Lolok lolok = new Lolok();
       lolok.greet();
       lolok.readData();
       lolok.getUserInput();
    }
}
