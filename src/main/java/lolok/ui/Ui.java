package lolok.ui;

/**
 * Represents the UI class that handles all print messages and UI-related methods.
 */
public class Ui {
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
}
