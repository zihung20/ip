package loklok.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StorageTest {
    private final String path = "./data/LokLok_data.txt";
    private final Storage test = new Storage(path);

    @Test
    public void testLoadContent() throws IOException {
        List<String> expected = Files.readAllLines(Path.of(path));
        assertEquals(expected, test.loadData());
    }
}
