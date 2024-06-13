import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {



        InMemoryTaskManager taskManager = new InMemoryTaskManager();




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

        Subtask subtask1 = new Subtask(
                "Сдать ТЗ5",
                "Сделать тесты",
                3,
                Status.NEW,
                epic.getId()
        );
        int subtask1Id = taskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask(
                "Сдать ТЗ6",
                "Сделать тесты",
                3,
                Status.NEW,
                epic.getId()
        );
        int subtask2Id = taskManager.createSubtask(subtask2);

        Epic epic1 = new Epic(
                "Сдать все зачёты",
                "подготовиться к ним",
                -1,
                Status.NEW
        );
        int secondEpicId = taskManager.createEpic(epic1);


        taskManager.getSubtaskById(subtaskId);
        taskManager.getHistory();

        taskManager.getTaskById(firstTaskId);
        taskManager.getHistory();
        taskManager.getTaskById(firstTaskId);
        taskManager.getHistory();
        taskManager.getEpicById(firstEpicId);
        taskManager.getHistory();
        taskManager.getSubtaskById(subtaskId);
        taskManager.getSubtaskById(subtask1Id);
        taskManager.getSubtaskById(subtask2Id);
        taskManager.getHistory();
        taskManager.removeEpicFromHistory(firstEpicId);
        taskManager.getHistory();
    }
}
