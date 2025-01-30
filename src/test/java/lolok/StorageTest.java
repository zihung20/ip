package lolok;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StorageTest {
    private String path = "./data/lolok_data.txt";
    private Storage test = new Storage(path);

    @Test
    public void testLoadContent() throws IOException{
        List<String> expected = Files.readAllLines(Path.of(path));
        assertEquals(expected, test.loadData());
    }
}
