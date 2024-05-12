package Tests;

import managers.Managers;
import managers.TaskManager;
import models.Status;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HistoryManagerTest {
    @Test
    void shouldNotUpdateInHistory() {
        TaskManager taskManger = Managers.getDefault();
        int taskId = taskManger.createTask(
                new Task(
                        "d",
                        "d",
                        2,
                        Status.NEW
                )
        );
        taskManger.getTaskById(taskId);
        taskManger.updateTask(
                new Task(
                        "v",
                        "v",
                        taskId,
                        Status.NEW
                )
        );
        List<Task> testList = taskManger.getHistory();
        Assertions.assertEquals("d", testList.get(0).getTaskName());
        Assertions.assertEquals("d", testList.get(0).getDescription());
        Assertions.assertEquals(taskId, testList.get(0).getId());
        Assertions.assertEquals(Status.NEW, testList.get(0).getStatus());

    }
}
