package lolok;

import lolok.task.TaskList;
import lolok.task.Todo;

import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskListTest {
    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList(List.of());
        taskList.addToList(new Todo("test object"));

        assertEquals(List.of(new Todo("test object")), taskList.getList());
    }
}
