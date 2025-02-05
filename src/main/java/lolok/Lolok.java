package lolok;

import lolok.command.Command;
import lolok.exception.LolokException;
import lolok.task.TaskList;

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
        Ui.printLine();
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
                // should already handle exception in duke.Command
                System.out.println(e.toString());
            } finally {
                if (!exit) {
                    Ui.printLine();
                }
            }
        }
        this.exit();
    }

    public static void main(String[] args) {
        String defaultPath = "./data/lolok_data.txt";
        Lolok lolok = new Lolok(defaultPath);
        lolok.run();
    }
}
