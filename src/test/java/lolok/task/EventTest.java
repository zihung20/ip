package lolok.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;


class EventTest {
    private final String description = "Team Meeting";
    private final String fromDate = "2025-02-15, 14:30";
    private final String toDate = "2025-02-16, 13:30";

    @Test
    void constructorNullDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Event(null, fromDate, toDate);
        });
        assertTrue(exception.getMessage().contains("description cannot be null or empty"));
    }

    @Test
    void constructorNullDate() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Event(description, null, toDate);
        });
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Event(description, fromDate, null);
        });
        assertTrue(exception.getMessage().toLowerCase().contains("cannot be null or empty"));
        assertTrue(exception1.getMessage().toLowerCase().contains("cannot be null or empty"));
    }

    @Test
    void constructorInvalidDateFormat() {
        Exception exception1 = assertThrows(DateTimeParseException.class, () -> {
            new Event(description, "2025/01/15 14:30", toDate);
        });
        Exception exception2 = assertThrows(DateTimeParseException.class, () -> {
            new Event(description, fromDate, "2025/04/15 14:30");
        });
        assertTrue(exception1.getMessage().toLowerCase().contains("invalid date format"));
        assertTrue(exception2.getMessage().toLowerCase().contains("invalid date format"));
    }

    @Test
    void constructorEndDateBeforeStarDate() {
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            new Event(description, toDate, fromDate);
        });
        assertTrue(exception1.getMessage().contains("end time cannot be before start time"));
    }

    @Test
    void toFormatStringTest() {
        Event event = new Event(description, fromDate, toDate);
        assertEquals(String.format("E| |%s|%s|%s", description, fromDate, toDate), event.toFormatString());

        event.setDone(true);
        assertEquals(String.format("E|X|%s|%s|%s", description, fromDate, toDate), event.toFormatString());
    }

    @Test
    void toStringTest() {
        Event event = new Event(description, fromDate, toDate);
        String fromDateString = "Feb 15 2025, 14:30";
        String toDateString = "Feb 16 2025, 13:30";
        assertEquals(String.format("[E][ ] %s (from: %s to: %s)", description, fromDateString, toDateString),
                event.toString());

        event.setDone(true);
        assertEquals(String.format("[E][X] %s (from: %s to: %s)", description, fromDateString, toDateString),
                event.toString());
    }

    @Test
    void equalsTest() {

        Event event1 = new Event("Team meeting", fromDate, toDate);
        Event event2 = new Event("Team meeting", fromDate, toDate);
        Event event3 = new Event("Team meeting", "2023-01-16, 14:00", "2023-01-19, 16:30");
        Event event5 = new Event("Different meeting", fromDate, toDate);
        Task otherTask = new Todo("Team meeting");

        assertEquals(event1, event2, "Events with same description and dates should be equal");
        assertNotEquals(event1, event3, "Events with different from dates should not be equal");
        assertNotEquals(event1, event5, "Events with different descriptions should not be equal");
        assertNotEquals(event1, otherTask, "Event should not be equal to a different task type");
        assertNotEquals(null, event1, "Event should not be equal to null");
    }
}
