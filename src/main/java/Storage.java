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
}
