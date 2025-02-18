package lolok.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;
// Test cases generated using Claude and refined manually.
class DeadlineTest {

    private final String date = "2023-01-15, 14:30";

    @Test
    void constructorValid() {
        Deadline deadline = new Deadline("Submit report", date);
        assertEquals("Submit report", deadline.getDescription());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Task.DATA_DATETIME_FORMAT);
        LocalDateTime expectedDateTime = LocalDateTime.parse(date, formatter);
        assertEquals(expectedDateTime, deadline.getBy());
    }

    @Test
    void constructorNullDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Deadline(null, date);
        });
        assertTrue(exception.getMessage().contains("description cannot be null or empty"));
    }

    @Test
    void constructorNullDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Deadline("Submit report", null);
        });
        assertTrue(exception.getMessage().contains("date cannot be null or empty"));
    }

    @Test
    void constructorInvalidDateFormat() {
        Exception exception = assertThrows(DateTimeParseException.class, () -> {
            new Deadline("Submit report", "2023/01/15 14:30");
        });
        assertTrue(exception.getMessage().contains("Invalid date format"));
    }

    @Test
    void toFormatString() {
        Deadline deadline = new Deadline("Submit report", date);
        assertEquals("D| |Submit report|2023-01-15, 14:30", deadline.toFormatString());

        deadline.setDone(true);
        assertEquals("D|X|Submit report|2023-01-15, 14:30", deadline.toFormatString());
    }

    @Test
    void toStringTest() {
        Deadline deadline = new Deadline("Submit report", date);
        assertEquals("[D][ ] Submit report (by: Jan 15 2023, 14:30)", deadline.toString());

        deadline.setDone(true);
        assertEquals("[D][X] Submit report (by: Jan 15 2023, 14:30)", deadline.toString());
    }

    @Test
    void equalsTest() {
        Deadline deadline1 = new Deadline("Submit report", date);
        Deadline deadline2 = new Deadline("Submit report", date);
        Deadline deadline3 = new Deadline("Submit report", "2023-01-16, 14:30");
        Deadline deadline4 = new Deadline("Different task", date);
        Task otherTask = new Todo("Submit report");

        // Test equality
        assertEquals(deadline1, deadline2, "Deadlines with same description and date should be equal");
        assertNotEquals(deadline1, deadline3, "Deadlines with different dates should not be equal");
        assertNotEquals(deadline1, deadline4, "Deadlines with different descriptions should not be equal");
        assertNotEquals(deadline1, otherTask, "Deadline should not be equal to a different task type");
        assertNotEquals(null, deadline1, "Deadline should not be equal to null");
    }
}
