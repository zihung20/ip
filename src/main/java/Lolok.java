import java.util.List;
import java.util.Scanner;

public class Lolok {
    private final String name = "Lolok";
    private Storage storage;
    private TaskList taskList;

    public Lolok(String path) {
        readData(path);
    }

    public void run() {
        this.greet();
        this.getUserInput();
    }

    private void greet() {
        Ui.printLine();
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
        Ui.printLine();
    }

    public void readData(String path) {
        this.storage = new Storage(path);
        this.taskList = new TaskList(storage.loadData());
    }

    private void exit() {
        Ui.printLine();
        this.storage.saveData(taskList.getList(), false);
        System.out.println("Bye. Hope to see you again soon!");
        Ui.printLine();
    }

    private void getUserInput() {
        //https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
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
        }
        //clean up
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
            Ui.printMessageBlock(e.toString());
        }
    }

    public static void main(String[] args) {
        String defaultPath = "./data/lolok_data.txt";
        Lolok lolok = new Lolok(defaultPath);
        lolok.run();
    }
}
