package lolok;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public List<String> loadData() {
        try {
            System.out.println("Reading file from " + this.filePath + "...");
            return Files.readAllLines(Paths.get(this.filePath));
        } catch (IOException e) {
            System.out.println("We can't read data from the path: " + e.getMessage());
            return List.of();
        }
    }

    public void saveData(List<Task> tasks, boolean append) {
        System.out.println("Saving tasks...");
        //FileWriter create file when it does not exist
        try (FileWriter fw = new FileWriter(this.filePath, append)) {
            for (Task t : tasks) {
                fw.write(t.toFormatString() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.out.println("Something wrong when saving the file" + e.getMessage());
        }
    }
}
