package lolok.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void testAddTask() {
        TaskList taskList = new TaskList(List.of());
        taskList.addToList(new Todo("test object"));

        assertEquals(List.of(new Todo("test object")), taskList.getList());
    }
}
