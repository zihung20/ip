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

    private Storage storage;
    private TaskList taskList;

    public Lolok(String path) {
        readData(path);
    }

    private void greet() {
        printLine();
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
        printLine();
    }

    public void readData(String path) {
        this.storage = new Storage(path);
        this.taskList = new TaskList(storage.loadData());
    }

    private static void printLine() {
        System.out.println("_".repeat(32));
    }

    private void exit() {
        printLine();
        this.storage.saveData(taskList.getList(), false);
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
                        taskList.printList();
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
                    taskList.deleteTask(Integer.parseInt(arg[0]) - 1);
                } else {
                    taskList.markTask(Integer.parseInt(arg[0]) - 1, command.getType().equals("mark"));
                }
            } else if(type.equals("todo")) {
                String[] arg = command.getArgument(1);
                taskList.addToList(new Todo(arg[0]));
            } else if(type.equals("deadline")){
                String[] arg = command.getArgument(2);
                taskList.addToList(new Deadline(arg[0], arg[1]));
            } else if (type.equals("event")) {
                String[] arg = command.getArgument(3);
                taskList.addToList(new Event(arg[0], arg[1], arg[2]));
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
    public void run() {
        this.greet();
        this.getUserInput();
    }
    public static void main(String[] args) {
        String defaultPath = "./data/lolok_data.txt";
        Lolok lolok = new Lolok(defaultPath);
        lolok.run();
    }
}
