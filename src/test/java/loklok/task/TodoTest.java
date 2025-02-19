package loklok.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
// Test cases generated using Claude and refined manually.
class TodoTest {

    @Test
    void constructorValid() {
        Todo todo = new Todo("Read book");
        assertEquals("Read book", todo.getDescription());
    }

    @Test
    void constructorInvalid() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Todo(null);
        });
        assertTrue(exception.getMessage().contains("cannot be null or empty"));
    }

    @Test
    void toFormatString() {
        Todo todo = new Todo("Read book");
        assertEquals("T| |Read book", todo.toFormatString());

        todo.setDone(true);
        assertEquals("T|X|Read book", todo.toFormatString());
    }

    @Test
    void toStringTest() {
        Todo todo = new Todo("Read book");
        assertEquals("[T][ ] Read book", todo.toString());

        todo.setDone(true);
        assertEquals("[T][X] Read book", todo.toString());
    }

    @Test
    void equalsAndHashCode() {
        Todo todo1 = new Todo("Read book");
        Todo todo2 = new Todo("Read book");
        Todo todo3 = new Todo("Write report");
        Task otherTask = new Deadline("Read book", "2023-01-01, 12:00");

        // Test equality
        assertEquals(todo1, todo2, "Todos with same description should be equal");
        assertNotEquals(todo1, todo3, "Todos with different descriptions should not be equal");
        assertNotEquals(todo1, otherTask, "Todo should not be equal to a different task type");
        assertNotEquals(null, todo1, "Todo should not be equal to null");
        assertNotEquals("Read book", todo1, "Todo should not be equal to a string");
    }
}
