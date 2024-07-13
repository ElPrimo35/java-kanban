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
import java.util.ArrayList;
import java.util.List;

public class HistoryManagerTest {
    @Test
    void shouldNotUpdateInHistory() {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("d", "d", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int taskId = taskManager.createTask(task);
        taskManager.getTaskById(taskId);
        taskManager.updateTask(task);

        List<Task> testList = taskManager.getHistory();
        Assertions.assertEquals("d", testList.get(0).getTaskName());
        Assertions.assertEquals("d", testList.get(0).getDescription());
        Assertions.assertEquals(taskId, testList.get(0).getId());
        Assertions.assertEquals(Status.NEW, testList.get(0).getStatus());

    }

    @Test
    void shouldReturnHistory() {
        TaskManager taskManager = Managers.getDefault();
        List<Task> tasks = new ArrayList<>();

        Task task = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = taskManager.createTask(task);


        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);


        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12));
        int subtaskId = taskManager.createSubtask(subtask);

        taskManager.getTaskById(task.getId());
        taskManager.getEpicById(epic.getId());
        taskManager.getSubtaskById(subtask.getId());

        tasks.add(task);
        tasks.add(epic);
        tasks.add(subtask);

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldAddAndDelete() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = taskManager.createTask(task);


        Task task1 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondTaskId = taskManager.createTask(task);

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int subtaskId = taskManager.createSubtask(subtask);

        InMemoryHistoryManager history = new InMemoryHistoryManager();

        history.addTask(task);
        history.addTask(task1);
        history.addTask(task);
        history.addTask(task1);
        history.addTask(epic);
        history.addTask(subtask);
        history.addTask(epic);
        history.addTask(subtask);


        tasks.add(task);
        tasks.add(task1);
        tasks.add(epic);
        tasks.add(subtask);

        Assertions.assertEquals(tasks, history.getHistory());
    }

    @Test
    void shouldDeleteTaskFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondTaskId = taskManager.createTask(task1);

        Task task = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = taskManager.createTask(task);

        taskManager.getTaskById(firstTaskId);
        taskManager.getTaskById(secondTaskId);
        taskManager.deleteTaskById(firstTaskId);

        tasks.add(task1);

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldDeleteAllTasksFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondTaskId = taskManager.createTask(task1);

        Task task = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = taskManager.createTask(task);

        taskManager.getTaskById(firstTaskId);
        taskManager.getTaskById(secondTaskId);

        taskManager.deleteAllTasks();

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldDeleteSubtaskFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = taskManager.createSubtask(subtask);
        Subtask subtask1 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int secondSubtaskId = taskManager.createSubtask(subtask1);
        taskManager.getEpicById(firstEpicId);
        taskManager.getSubtaskById(firstSubtaskId);
        taskManager.getSubtaskById(secondSubtaskId);
        taskManager.deleteSubtaskById(secondSubtaskId);

        tasks.add(epic);
        tasks.add(subtask);

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldDeleteAllSubtaskFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = taskManager.createSubtask(subtask);
        Subtask subtask1 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int secondSubtaskId = taskManager.createSubtask(subtask1);

        taskManager.getEpicById(firstEpicId);
        taskManager.getSubtaskById(firstSubtaskId);
        taskManager.getSubtaskById(secondSubtaskId);
        taskManager.deleteAllSubtasks();

        tasks.add(epic);

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldDeleteEpicFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = taskManager.createSubtask(subtask);
        Subtask subtask1 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1225, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int secondSubtaskId = taskManager.createSubtask(subtask1);

        Epic epic1 = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondEpicId = taskManager.createEpic(epic1);

        Subtask subtask2 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                secondEpicId,
                LocalDateTime.of(1226, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int thirdSubtaskId = taskManager.createSubtask(subtask2);

        taskManager.getEpicById(firstEpicId);
        taskManager.getSubtaskById(firstSubtaskId);
        taskManager.getSubtaskById(secondSubtaskId);
        taskManager.getEpicById(secondEpicId);
        taskManager.getSubtaskById(thirdSubtaskId);

        taskManager.deleteEpicById(firstEpicId);

        tasks.add(epic1);
        tasks.add(subtask2);

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldDeleteAllEpicsFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = taskManager.createSubtask(subtask);

        Subtask subtask1 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int secondSubtaskId = taskManager.createSubtask(subtask1);

        Epic epic1 = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondEpicId = taskManager.createEpic(epic1);

        Subtask subtask2 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int thirdSubtaskId = taskManager.createSubtask(subtask2);

        taskManager.getEpicById(firstEpicId);
        taskManager.getSubtaskById(firstSubtaskId);
        taskManager.getSubtaskById(secondSubtaskId);
        taskManager.getEpicById(secondEpicId);
        taskManager.getSubtaskById(thirdSubtaskId);

        taskManager.deleteAllEpics();

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }
}
