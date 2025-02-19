package loklok.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import loklok.exception.IncorrectArgumentNumberException;
// Test cases generated using Claude and refined manually.
public class TaskListTest {

    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    void testConstructorWithValidStringList() {
        List<String> validStringList = Arrays.asList(
                "T| |Read book",
                "D| |Submit report|2023-01-15, 14:30",
                "E| |Team meeting|2023-01-15, 14:00|2023-01-15, 15:30"
        );

        TaskList taskList = new TaskList(validStringList);
        List<Task> tasks = taskList.getList();

        assertEquals(3, tasks.size());
        assertInstanceOf(Todo.class, tasks.get(0));
        assertInstanceOf(Deadline.class, tasks.get(1));
        assertInstanceOf(Event.class, tasks.get(2));

        assertEquals("Read book", tasks.get(0).getDescription());
        assertEquals("Submit report", tasks.get(1).getDescription());
        assertEquals("Team meeting", tasks.get(2).getDescription());
    }

    @Test
    void testConstructorWithInvalidTaskString() {
        // Save the original System.err
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        try {
            List<String> invalidStringList = Arrays.asList(
                    "T| |Valid todo",
                    "X| |Invalid task type",
                    "D| |Missing deadline date"
            );

            Exception exception = assertThrows(IncorrectArgumentNumberException.class, () ->
                    new TaskList(invalidStringList));
            assertTrue(exception.getMessage().contains("Incorrect argument numbers"));
        } finally {
            // Restore the original System.err
            System.setErr(originalErr);
        }
    }

    @Test
    void testAddToListWithValidTask() {
        Task todo = new Todo("Read book");
        taskList.addToList(todo);

        List<Task> tasks = taskList.getList();
        assertEquals(1, tasks.size());
        assertEquals(todo.getDescription(), tasks.get(0).getDescription());
    }

    @Test
    void testAddToListWithDuplicateTask() {
        // Save the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Task todo = new Todo("Read book");
            taskList.addToList(todo);
            taskList.addToList(todo);

            List<Task> tasks = taskList.getList();
            assertEquals(1, tasks.size(), "Duplicate task should not be added");

            // Verify duplicate message was printed
            String output = outContent.toString();
            assertTrue(output.contains("The task is duplicated"),
                    "Duplicate task message should be displayed");
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testPrintListWithTasks() {
        // Save the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            taskList.addToList(new Todo("Read book"));
            taskList.addToList(new Deadline("Submit report", "2023-01-15, 14:30"));

            outContent.reset(); // Clear previous output from addToList
            taskList.printList();

            String output = outContent.toString();
            assertTrue(output.contains("1. [T][ ] Read book"));
            assertTrue(output.contains("2. [D][ ] Submit report"));
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testDeleteTaskValid() {
        // Save the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            taskList.addToList(new Todo("Read book"));
            taskList.addToList(new Deadline("Submit report", "2023-01-15, 14:30"));

            outContent.reset(); // Clear previous output
            taskList.deleteTask(1); // Delete first task

            List<Task> tasks = taskList.getList();
            assertEquals(1, tasks.size());
            assertInstanceOf(Deadline.class, tasks.get(0));

            String output = outContent.toString();
            assertTrue(output.contains("Noted. I've removed this task:"));
            assertTrue(output.contains("[T][ ] Read book"));
            assertTrue(output.contains("Now you have 1 tasks in the list."));
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testDeleteTaskInvalidIndex() {
        // Save the original System.err
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(errContent));

        try {
            taskList.addToList(new Todo("Read book"));

            taskList.deleteTask(2); // Invalid index

            List<Task> tasks = taskList.getList();
            assertEquals(1, tasks.size(), "Task should not be deleted for invalid index");

            String errorOutput = errContent.toString();
            System.out.println("Index 2 is invalid or doesn't have any task.");
            assertTrue(errorOutput.contains("Index 2 is invalid or doesn't have any task."),
                    "Error message for invalid index should be displayed");
        } finally {
            // Restore the original System.err
            System.setErr(originalErr);
        }
    }

    @Test
    void testMarkTaskAsDone() {
        // Save the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            taskList.addToList(new Todo("Read book"));

            outContent.reset(); // Clear previous output
            taskList.markTask(1, true); // Mark as done

            List<Task> tasks = taskList.getList();
            assertEquals("X", tasks.get(0).getStatusIcon());

            String output = outContent.toString();
            assertTrue(output.contains("Nice! I've marked this task as done:"));
            assertTrue(output.contains("[T][X] Read book"));
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testMarkTaskAsNotDone() {
        // Save the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            Task todo = new Todo("Read book");
            todo.setDone(true);
            taskList.addToList(todo);

            outContent.reset(); // Clear previous output
            taskList.markTask(1, false); // Mark as not done

            List<Task> tasks = taskList.getList();
            assertEquals(" ", tasks.get(0).getStatusIcon());

            String output = outContent.toString();
            assertTrue(output.contains("OK, I've marked this task as not done yet:"));
            assertTrue(output.contains("[T][ ] Read book"));
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testSearchTaskWithMatches() {
        // Save the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            taskList.addToList(new Todo("Read Java book"));
            taskList.addToList(new Todo("Watch movie"));
            taskList.addToList(new Deadline("Java assignment", "2023-01-15, 14:30"));

            outContent.reset(); // Clear previous output
            taskList.searchTask("Java");

            String output = outContent.toString();
            assertTrue(output.contains("Here are the matching tasks in your list:"));
            assertTrue(output.contains("1. [T][ ] Read Java book"));
            assertTrue(output.contains("2. [D][ ] Java assignment"));
            assertFalse(output.contains("Watch movie"));
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testSearchTaskWithNoMatches() {
        // Save the original System.out
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            taskList.addToList(new Todo("Read book"));
            taskList.addToList(new Todo("Watch movie"));

            outContent.reset(); // Clear previous output
            taskList.searchTask("homework");

            String output = outContent.toString();
            assertTrue(output.contains("Here are the matching tasks in your list:"));
            // Output should not contain any task listings
            assertFalse(output.contains("Read book"));
            assertFalse(output.contains("Watch movie"));
        } finally {
            // Restore the original System.out
            System.setOut(originalOut);
        }
    }

    @Test
    void testLoadingTasksWithCorrectDoneStatus() {
        List<String> stringList = Arrays.asList(
                "T|X|Read book",
                "D| |Submit report|2023-01-15, 14:30",
                "E|X|Team meeting|2023-01-15, 14:00|2023-01-15, 15:30"
        );

        TaskList taskList = new TaskList(stringList);
        List<Task> tasks = taskList.getList();
        System.out.println(" " + tasks.get(0) + tasks.get(1) + tasks.get(2));
        assertEquals(3, tasks.size());
        assertEquals("X", tasks.get(0).getStatusIcon(), "Todo should be marked as done");
        assertEquals(" ", tasks.get(1).getStatusIcon(), "Deadline should be marked as not done");
        assertEquals("X", tasks.get(2).getStatusIcon(), "Event should be marked as done");
    }

    @Test
    void testLoadTaskWithComment() {
        List<String> stringList = Arrays.asList(
                "C|This is a comment",
                "T| |Read book"
        );

        TaskList taskList = new TaskList(stringList);
        List<Task> tasks = taskList.getList();

        assertEquals(1, tasks.size(), "Comments should be ignored when loading tasks");
        assertInstanceOf(Todo.class, tasks.get(0));
    }
}
