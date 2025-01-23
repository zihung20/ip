import java.util.Scanner;

public class Lolok {
    private final String name = "Lolok";
    private final String logo = "";
    private void greet() {
        printLine();
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
    }

    private static void printLine() {
        System.out.println("__________________________________");
    }

    private void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        printLine();
    }

    private void echo(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }
    public static void main(String[] args) {
       Lolok lolok = new Lolok();

       lolok.greet();
        Scanner scanner = new Scanner(System.in);
       while (true) {
           String input = scanner.next();
           if(input.equals("bye")) {
               printLine();
               lolok.exit();
               break;
           } else {
               lolok.echo(input);
           }
       }
    }
}
