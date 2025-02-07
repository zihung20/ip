package lolok.ui;

import java.util.Scanner;

/**
 * Represents the UI class that handles all print messages and UI-related methods.
 */
public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public static void printLine() {
        System.out.println("_".repeat(32));
    }

    /**
     * Prints each message on a new line
     * @param messages - A variable number of messages to be printed, each appearing on a separate line
     */
    public static void printMessage(String... messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

    /**
     * Prints an error message.
     *
     * @param error - The error message to be printed
     */
    public static void printErrorMessage(String error) {
        System.out.println("Some errors happened...");
        System.out.println(error);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Greets the user
     * @param chatBotName The name of the chatbot
     */
    public void greet(String chatBotName) {
        Ui.printLine();
        System.out.println("Hello! I'm " + chatBotName);
        System.out.println("What can I do for you?");
        Ui.printLine();
    }
}
