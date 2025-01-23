import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Lolok {
    private final String name = "Lolok";
    private final String logo = "";
    private List<Task> list = new ArrayList<>();

    private void greet() {
        printLine();
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
        printLine();
    }

    private static void printLine() {
        System.out.println("_".repeat(32));
    }

    private void exit() {
        printLine();
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    private void getUserInput() {
        //https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
        Scanner scanner = new Scanner(System.in);
        while (true) {
            //lowercase can add in the future
            String input = scanner.nextLine();
            String[] block = input.split(" ");
            if(block.length >= 2 && (block[0].equals("mark") || block[0].equals("unmark"))) {
                int index = Integer.parseInt(block[1]);

                markTask(index - 1, block[0].equals("mark"));
            }else if(input.equals("bye")) {
                this.exit();
                break;
            } else if(input.equals("list")){
                this.printList();
            } else {
                //add to list
                Command command = new Command(block);
                addTaskToList(block[0], command.getArgument());
            }
        }
        scanner.close();
    }
    private void addTaskToList(String type, String[] argument) {

        switch(type){
        case "todo":
            addToList(new Todo(argument[0]));
            break;
        case "deadline":
            addToList(new Deadline(argument[0], argument[1]));
            break;
        case "event":
            addToList(new Event(argument[0], argument[1], argument[2]));
            break;
        }
    }
    private void echo(String message) {
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
        echo(message + "\n" + task.toString());
    }
    public static void main(String[] args) {
       Lolok lolok = new Lolok();

       lolok.greet();
       lolok.getUserInput();
    }
}
