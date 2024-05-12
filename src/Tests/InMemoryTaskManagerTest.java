package Tests;
import managers.TaskManager;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import managers.InMemoryTaskManager;
import models.Epic;
import models.Status;

public class InMemoryTaskManagerTest {

    @Test
    void shouldAddSubtasks() {
        TaskManager taskManager = new InMemoryTaskManager();
        Epic firstEpic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW
        );
        int firstEpicId = taskManager.createEpic(firstEpic);

        Subtask firstSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId
        );
        int firstSubtaskId = taskManager.createSubtask(firstSubtask);

        Subtask subtask = taskManager.getSubtaskById(firstSubtaskId);
        Assertions.assertEquals(firstSubtask.getTaskName(), subtask.getTaskName());
        Assertions.assertEquals(firstSubtask.getDescription(), subtask.getDescription());
        Assertions.assertEquals(firstSubtask.getEpicId(), subtask.getEpicId());
        Assertions.assertEquals(firstSubtask.getStatus(), subtask.getStatus());
        Assertions.assertEquals(firstSubtask.getId(), subtask.getId());

    }


    @Test
    void shouldAddTasks() {
        TaskManager taskManager = new InMemoryTaskManager();

        Task task = new Task(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW
        );
        int TaskId = taskManager.createTask(task);

        Task task1 = taskManager.getTaskById(TaskId);
        Assertions.assertEquals(task.getTaskName(), task1.getTaskName());
        Assertions.assertEquals(task.getDescription(), task1.getDescription());
        Assertions.assertEquals(task.getStatus(), task1.getStatus());
        Assertions.assertEquals(task.getId(), task1.getId());
    }

    @Test
    void shouldAddEpics() {
        TaskManager taskManager = new InMemoryTaskManager();

        Epic epic = new Epic(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW
        );
        int EpicId = taskManager.createEpic(epic);

        Epic epic1 = taskManager.getEpicById(EpicId);

        Assertions.assertEquals(epic.getTaskName(), epic1.getTaskName());
        Assertions.assertEquals(epic.getDescription(), epic1.getDescription());
        Assertions.assertEquals(epic.getStatus(), epic1.getStatus());
        Assertions.assertEquals(epic.getId(), epic1.getId());
    }
}
