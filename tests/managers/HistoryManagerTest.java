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
}
