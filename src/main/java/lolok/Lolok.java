package lolok;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import lolok.command.Command;
import lolok.exception.LolokException;
import lolok.task.TaskList;
import lolok.ui.Ui;

/**
 * main class of the chatbot lolok
 */
public class Lolok {
    private final String name = "Lolok";
    private Storage storage;
    private TaskList taskList;
    private Command command;
    private Ui ui;

    /**
     * Creates an instance of the Lolok chatbot that loads and stores data using the given file path.
     *
     * @param path the file path where data is stored; the file must exist
     */
    public Lolok(String path) {
        readData(path);
        this.ui = new Ui();
    }

    /**
     * run the program
     */
    public void run() {
        ui.greet(this.name);
        this.getUserInput();
    }

    /**
     * Reads data from the specified file.
     *
     * @param path the file path to read data from
     */
    private void readData(String path) {
        this.storage = new Storage(path);
        this.taskList = new TaskList(storage.loadData());
    }

    private void exit() {
        this.storage.saveData(taskList.getList(), false);
        System.out.println("Bye. Hope to see you again soon!");
    }

    private void getUserInput() {
        boolean exit = false;
        while (!exit) {
            try {
                String input = ui.readCommand();
                Ui.printLine();
                Command command = new Command(input.split(" "));
                command.executeCommand(taskList, ui, storage);
                exit = command.isExit();
            } catch (LolokException e) {
                System.out.println(e.toString());
            } finally {
                if (!exit) {
                    Ui.printLine();
                }
            }
        }
        this.exit();
    }

    public String getResponse(String input) {
        try {
            Command command = new Command(input.split(" "));
            //ChatGpt:how to get the response from system.out
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);

            // Save the original System.out
            PrintStream originalOut = System.out;

            // Redirect System.out to the custom PrintStream
            System.setOut(ps);
            command.executeCommand(taskList, ui, storage);
            boolean exit = command.isExit();
            if (exit) {
                this.exit();
            }
            String response = baos.toString();

            // Flush and reset the ByteArrayOutputStream
            baos.reset();

            // Restore the original System.out
            System.setOut(originalOut);

            // Optionally flush the custom PrintStream
            ps.flush();

            // Return the captured response
            return response;
        } catch (LolokException e) {
            // should already handle exception in lolok.Command
            System.out.println(e.toString());
            return "";
        }
    }

    public static void main(String[] args) {
        String defaultPath = "./data/lolok_data.txt";
        Lolok lolok = new Lolok(defaultPath);
        lolok.run();
    }
}
