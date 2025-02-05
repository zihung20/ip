package lolok;

import lolok.task.Deadline;
import lolok.task.Task;

import org.junit.jupiter.api.Test;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineTest {

    @Test
    public void testCorrectFormat() {
        Task test = new Deadline("test object", "2025-01-30, 21:32");
        assertEquals("D| |test object|2025-01-30, 21:32", test.toFormatString());
    }

    @Test
    public void testWrongFormat() {
        assertThrows(DateTimeParseException.class, () ->
                new Deadline("test object", "2025/01/30, 21:32"));
    }
}
