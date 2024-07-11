package managers;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public abstract class TaskManagerTest<T extends TaskManager> {
    protected abstract T createTaskManager();

    @Test
    void shouldAddSubtasks() {
        TaskManager taskManager = createTaskManager();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask firstSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
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
        TaskManager taskManager = createTaskManager();

        Task task = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = taskManager.createTask(task);

        Task task1 = taskManager.getTaskById(firstTaskId);
        Assertions.assertEquals(task.getTaskName(), task1.getTaskName());
        Assertions.assertEquals(task.getDescription(), task1.getDescription());
        Assertions.assertEquals(task.getStatus(), task1.getStatus());
        Assertions.assertEquals(task.getId(), task1.getId());
    }

    @Test
    void shouldAddEpics() {
        TaskManager taskManager = createTaskManager();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Epic epic1 = taskManager.getEpicById(firstEpicId);

        Assertions.assertEquals(epic.getTaskName(), epic1.getTaskName());
        Assertions.assertEquals(epic.getDescription(), epic1.getDescription());
        Assertions.assertEquals(epic.getStatus(), epic1.getStatus());
        Assertions.assertEquals(epic.getId(), epic1.getId());
    }

    @Test
    void shouldHaveStatusNew() {
        TaskManager taskManager = createTaskManager();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask firstSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = taskManager.createSubtask(firstSubtask);
        Assertions.assertEquals(epic.getStatus(), Status.NEW);
    }

    @Test
    void shouldHaveStatusDone() {
        TaskManager taskManager = createTaskManager();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask firstSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.DONE,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = taskManager.createSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.DONE,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int secondSubtaskId = taskManager.createSubtask(firstSubtask);
        Assertions.assertEquals(epic.getStatus(), Status.DONE);
    }

    @Test
    void shouldHaveStatusInProgress() {
        TaskManager taskManager = createTaskManager();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask firstSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = taskManager.createSubtask(firstSubtask);
        Subtask secondSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.DONE,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int secondSubtaskId = taskManager.createSubtask(secondSubtask);
        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus());

        Epic epic1 = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondEpicId = taskManager.createEpic(epic1);

        Subtask thirdSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                secondEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int thirdSubtaskId = taskManager.createSubtask(thirdSubtask);
        Subtask fourthSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.DONE,
                secondEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int fourthSubtaskId = taskManager.createSubtask(fourthSubtask);
        Assertions.assertEquals(Status.IN_PROGRESS, epic1.getStatus());
    }

}