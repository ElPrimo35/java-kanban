
import managers.InMemoryTaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {


        InMemoryTaskManager taskManager = new InMemoryTaskManager();


        Task firstTask = new Task("Уборка", "Убраться в доме", 1, Status.NEW);
        int firstTaskId = taskManager.createTask(firstTask);

        Task secondTask = new Task("Сходить погулять", "Прийти в центр города", 2, Status.NEW);
        int secondTaskId = taskManager.createTask(secondTask);

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());
        int firstSubtaskId = taskManager.createSubtask(subtask);

        Subtask subtask1 = new Subtask("Сдать ТЗ5", "Сделать тесты", 3, Status.NEW, epic.getId());
        int secondSubtaskId = taskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask("Сдать ТЗ6", "Сделать тесты", 3, Status.NEW, epic.getId());
        int thirdSubtaskId = taskManager.createSubtask(subtask2);

        Epic epic1 = new Epic("Сдать все зачёты", "подготовиться к ним", -1, Status.NEW);
        int secondEpicId = taskManager.createEpic(epic1);

        Subtask subtask3 = new Subtask("Сдать ТЗ7", "Сделать тесты", 3, Status.NEW, epic1.getId());
        int fourthSubtaskId = taskManager.createSubtask(subtask3);


        taskManager.getTaskById(firstTaskId);
        taskManager.getTaskById(secondTaskId);
        taskManager.getTaskById(firstTaskId);
        System.out.println(taskManager.getHistory());
        taskManager.deleteAllTasks();
        System.out.println(taskManager.getHistory());

        taskManager.getEpicById(firstEpicId);
        taskManager.getSubtaskById(firstSubtaskId);
        taskManager.getSubtaskById(secondSubtaskId);
        taskManager.getSubtaskById(thirdSubtaskId);
        taskManager.getEpicById(secondEpicId);
        taskManager.getSubtaskById(fourthSubtaskId);
        System.out.println(taskManager.getHistory());
        taskManager.deleteAllEpics();
        System.out.println(taskManager.getHistory());
    }
}
