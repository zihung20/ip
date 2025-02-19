package loklok.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
// Test cases generated using Claude and refined manually.
class TaskTest {

    // Create a concrete implementation for testing abstract Task class
    private static class TestTask extends Task {
        public TestTask(String description) {
            super(description);
        }

        @Override
        public String toFormatString() {
            return "TEST|" + super.toFormatString();
        }
    }

    @Test
    void constructorValid() {
        Task task = new TestTask("Test task");
        assertEquals("Test task", task.getDescription());
    }

    @Test
    void constructorWithNullOrEmptyDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new TestTask(null);
        });
        assertTrue(exception.getMessage().contains("cannot be null or empty"));

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            new TestTask("");
        });
        assertTrue(exception2.getMessage().contains("cannot be null or empty"));
    }

    @Test
    void setDoneAndGetStatusIcon() {
        Task task = new TestTask("Test task");
        assertEquals(" ", task.getStatusIcon(), "New task should not be done");

        task.setDone(true);
        assertEquals("X", task.getStatusIcon(), "Task should be marked as done");

        task.setDone(false);
        assertEquals(" ", task.getStatusIcon(), "Task should be marked as not done");
    }

    @Test
    void toFormatString() {
        Task task = new TestTask("Test task");
        assertEquals("TEST| |Test task", task.toFormatString());

        task.setDone(true);
        assertEquals("TEST|X|Test task", task.toFormatString());
    }

    @Test
    void toStringTest() {
        Task task = new TestTask("Test task");
        assertEquals("[ ] Test task", task.toString());

        task.setDone(true);
        assertEquals("[X] Test task", task.toString());
    }

    @Test
    void matchKeywordPositive() {
        Task task = new TestTask("Buy milk from the supermarket");
        assertTrue(task.matchKeyword("milk"));
        assertTrue(task.matchKeyword("super"));
        assertTrue(task.matchKeyword("supermarket"));
    }

    @Test
    void matchKeywordNegative() {
        Task task = new TestTask("Buy milk from the supermarket");
        assertFalse(task.matchKeyword("eggs"));
        assertFalse(task.matchKeyword("grocery"));
    }

    @Test
    void matchKeywordCaseInsensitive() {
        Task task = new TestTask("Buy MILK from the supermarket");
        assertTrue(task.matchKeyword("milk"));
        assertTrue(task.matchKeyword("MILK"));
        assertTrue(task.matchKeyword("Milk"));
    }

    @Test
    void matchKeywordWithNull() {
        Task task = new TestTask("Buy milk from the supermarket");
        assertFalse(task.matchKeyword(null));
    }

    @Test
    void equalsTest() {
        Task task1 = new TestTask("Test task");
        Task task2 = new TestTask("Test task");
        Task task3 = new TestTask("Different task");

        // Test equality
        assertEquals(task1, task2, "Tasks with same description should be equal");
        assertNotEquals(task1, task3, "Tasks with different descriptions should not be equal");
        assertNotEquals(null, task1, "Task should not be equal to null");
        assertNotEquals("Test task", task1, "Task should not be equal to a different type");
    }
}
