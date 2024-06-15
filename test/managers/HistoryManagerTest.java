package managers;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

    @Test
    void shouldReturnHistory() {
        TaskManager taskManger = Managers.getDefault();
        List<Task> tasks = new ArrayList<>();

        Task task = new Task(
                "d",
                "d",
                2,
                Status.NEW
        );
        taskManger.createTask(task);


        Epic epic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW
        );
        taskManger.createEpic(epic);


        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic.getId()
        );
        taskManger.createSubtask(subtask);


        taskManger.getTaskById(task.getId());
        taskManger.getEpicById(epic.getId());
        taskManger.getSubtaskById(subtask.getId());

        tasks.add(task);
        tasks.add(epic);
        tasks.add(subtask);

        Assertions.assertEquals(tasks, taskManger.getHistory());
    }

    @Test
    void shouldAddAndDelete() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();
        Task firstTask = new Task(
                "Уборка",
                "Убраться в доме",
                1,
                Status.NEW
        );
        int firstTaskId = taskManager.createTask(firstTask);


        Task secondTask = new Task(
                "Сходить погулять",
                "Прийти в центр города",
                2,
                Status.NEW
        );
        int secondTaskId = taskManager.createTask(secondTask);

        Epic epic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW
        );
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic.getId()
        );
        int subtaskId = taskManager.createSubtask(subtask);


        InMemoryHistoryManager history = new InMemoryHistoryManager();

        history.addTask(firstTask);
        history.addTask(secondTask);
        history.addTask(firstTask);
        history.addTask(secondTask);
        history.addTask(epic);
        history.addTask(subtask);
        history.addTask(epic);
        history.addTask(subtask);


        tasks.add(firstTask);
        tasks.add(secondTask);
        tasks.add(epic);
        tasks.add(subtask);

        Assertions.assertEquals(tasks, history.getHistory());
    }

    @Test
    void shouldDeleteTaskFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Task firstTask = new Task("Уборка", "Убраться в доме", 1, Status.NEW);
        int firstTaskId = taskManager.createTask(firstTask);

        Task secondTask = new Task("Сходить погулять", "Прийти в центр города", 2, Status.NEW);
        int secondTaskId = taskManager.createTask(secondTask);

        taskManager.getTaskById(firstTaskId);
        taskManager.getTaskById(secondTaskId);
        taskManager.deleteTaskById(firstTaskId);

        tasks.add(secondTask);

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldDeleteAllTasksFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Task firstTask = new Task("Уборка", "Убраться в доме", 1, Status.NEW);
        int firstTaskId = taskManager.createTask(firstTask);

        Task secondTask = new Task("Сходить погулять", "Прийти в центр города", 2, Status.NEW);
        int secondTaskId = taskManager.createTask(secondTask);

        taskManager.getTaskById(firstTaskId);
        taskManager.getTaskById(secondTaskId);

        taskManager.deleteAllTasks();

        Assertions.assertEquals(tasks, taskManager.getHistory());
    }

    @Test
    void shouldDeleteSubtaskFromHistory() {
        List<Task> tasks = new ArrayList<>();
        TaskManager taskManager = Managers.getDefault();

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());
        int firstSubtaskId = taskManager.createSubtask(subtask);

        Subtask subtask1 = new Subtask("Сдать ТЗ5", "Сделать тесты", 3, Status.NEW, epic.getId());
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

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());
        int firstSubtaskId = taskManager.createSubtask(subtask);

        Subtask subtask1 = new Subtask("Сдать ТЗ5", "Сделать тесты", 3, Status.NEW, epic.getId());
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

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());
        int firstSubtaskId = taskManager.createSubtask(subtask);

        Subtask subtask1 = new Subtask("Сдать ТЗ5", "Сделать тесты", 3, Status.NEW, epic.getId());
        int secondSubtaskId = taskManager.createSubtask(subtask1);

        Epic epic1 = new Epic("Сдать все зачёты", "подготовиться к ним", -1, Status.NEW);
        int secondEpicId = taskManager.createEpic(epic1);

        Subtask subtask2 = new Subtask("Сдать ТЗ7", "Сделать тесты", 3, Status.NEW, epic1.getId());
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

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());
        int firstSubtaskId = taskManager.createSubtask(subtask);

        Subtask subtask1 = new Subtask("Сдать ТЗ5", "Сделать тесты", 3, Status.NEW, epic.getId());
        int secondSubtaskId = taskManager.createSubtask(subtask1);

        Epic epic1 = new Epic("Сдать все зачёты", "подготовиться к ним", -1, Status.NEW);
        int secondEpicId = taskManager.createEpic(epic1);

        Subtask subtask2 = new Subtask("Сдать ТЗ7", "Сделать тесты", 3, Status.NEW, epic1.getId());
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
