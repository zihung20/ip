package lolok;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import lolok.command.Command;
import lolok.exception.InvalidCommandException;
import lolok.exception.LolokException;
import lolok.storage.Storage;
import lolok.task.TaskList;
import lolok.ui.Ui;

/**
 * main class of the chatbot lolok
 */
public class Lolok {
    private Storage storage;
    private TaskList taskList;
    private final Ui ui;

    /**
     * Creates an instance of the Lolok chatbot that loads and stores data using the given file path.
     *
     * @param path the file path where data is stored; the file must exist
     */
    public Lolok(String path) {
        readData(path);
        ui = new Ui();
    }

    /**
     * Greets the user.
     *
     * @param name The name of the chatbot, e.g., "lolok" for this case.
     * @return The string representation of the greeting message.
     */
    public String greet(String name) {
        String greetMessage = "Hello! I'm " + name + ". ";
        greetMessage += "What can I do for you?";
        return greetMessage;
    }
    /**
     * Reads data from the specified file.
     *
     * @param path the file path to read data from
     */
    private void readData(String path) {
        storage = new Storage(path);
        taskList = new TaskList(storage.loadData());
    }

    private void exit() {
        storage.saveData(taskList.getList(), false);
        Ui.printMessage("Bye. Hope to see you again soon!");
    }

    public String getResponse(String input) {
        //ChatGpt:how to get the response from system.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream originalOut = System.out;
        String response;
        try {
            System.setOut(ps);

            Command command = new Command(input.split(" "));
            command.executeCommand(taskList, ui, storage);
            boolean exit = command.isExit();
            if (exit) {
                this.exit();
            }
        } catch (InvalidCommandException e) {
            Ui.printErrorMessage(e.toString());
        } finally {
            response = baos.toString();
            baos.reset();
            System.setOut(originalOut);
            ps.flush();
        }
        return response;
    }

    private void startTest() {
        boolean isExit = false;
        Scanner scanner = new Scanner(System.in);
        while (!isExit) {
            try {
                String input = scanner.nextLine();
                if (input.equals("exit")) {
                    isExit = true;
                }
                String response = getResponse(input);
                System.out.print(response);
            } catch (LolokException e) {
                Ui.printErrorMessage(e.toString());
            }
        }
        this.exit();
    }
}
