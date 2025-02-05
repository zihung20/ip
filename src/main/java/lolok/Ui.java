package lolok;

import java.util.Scanner;

/**
 * Represents the UI class that handles all print messages and UI-related methods.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public static void printLine() {
        System.out.println("_".repeat(32));
    }

    public static void printMessageBlock(String message) {
        System.out.println(message);
    }

    public String readCommand() {
        return this.scanner.nextLine();
    }

    /**
     * Greets the user.
     * @param myName The name of the chatbot.
     */
    public void greet(String myName) {
        Ui.printLine();
        System.out.println("Hello! I'm " + myName);
        System.out.println("What can I do for you?");
        Ui.printLine();
    }
}
