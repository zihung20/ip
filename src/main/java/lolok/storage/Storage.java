package lolok.storage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import lolok.task.Task;
import lolok.ui.Ui;

/**
 * Represents a storage that can store or load data from a file path.
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage instance that handles loading and saving data to the specified file path.
     *
     * @param filePath the file path for storing and retrieving data
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the data and returns a list of strings, each representing a task in a specific format.
     *
     * @return a list of strings, where each string represents a task
     */
    public List<String> loadData() {
        try {
            Ui.printMessage("Reading file from " + filePath + "...");
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            Ui.printErrorMessage("We can't read data from the path: " + e.getMessage());
            return List.of();
        }
    }

    /**
     * Saves the data to the file path defined in this instance.
     *
     * @param tasks the list of tasks to be saved to the file
     * @param append true if the data should be appended to the existing file
     */
    public void saveData(List<Task> tasks, boolean append) {
        Ui.printMessage("Saving tasks...");
        //FileWriter create file when it does not exist
        try (FileWriter fw = new FileWriter(filePath, append)) {
            for (Task t : tasks) {
                fw.write(t.toFormatString() + System.lineSeparator());
            }
        } catch (IOException e) {
            Ui.printErrorMessage("Something wrong when saving the file" + e.getMessage());
        }
    }
}
