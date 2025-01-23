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
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    private void getUserInput() {
        //https://stackoverflow.com/questions/39514730/how-to-take-input-as-string-with-spaces-in-java-using-scanner
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine().toLowerCase();

            if(input.equals("bye")) {
                printLine();
                this.exit();
                break;
            } else if(input.equals("list")){
                this.printList();
            } else {
                this.addToList(input);
            }
        }
        scanner.close();
    }
    private void echo(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }

    private void addToList(String message) {
        this.echo("added: " + message);
        this.list.add(new Task(message));
    }

    private void printList() {
        printLine();
        System.out.println("Here are the task in your list");
        for(int i = 0; i < list.size(); i++) {
            System.out.print(i + ".[" + list.get(i).getStatusIcon() + "] ");
            System.out.println(list.get(i).getDescription());
        }
        printLine();
    }
    public static void main(String[] args) {
       Lolok lolok = new Lolok();

       lolok.greet();
       lolok.getUserInput();
    }
}
