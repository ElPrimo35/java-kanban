import managers.FileBackedTaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import java.io.IOException;
import java.nio.file.Path;


public class Main {

    public static void main(String[] args) throws IOException {


        FileBackedTaskManager file = new FileBackedTaskManager("tasks.cvs");


        Task firstTask = new Task("Уборка", "Убраться в доме", 1, Status.NEW);
        int firstTaskId = file.createTask(firstTask);

        Task secondTask = new Task("Сходить погулять", "Прийти в центр города", 2, Status.NEW);


        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);


        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());


        Subtask subtask1 = new Subtask("Сдать ТЗ5", "Сделать тесты", 3, Status.NEW, epic.getId());


        Subtask subtask2 = new Subtask("Сдать ТЗ6", "Сделать тесты", 3, Status.NEW, epic.getId());


        Epic epic1 = new Epic("Сдать все зачёты", "подготовиться к ним", -1, Status.NEW);


        Subtask subtask3 = new Subtask("Сдать ТЗ7", "Сделать тесты", 3, Status.NEW, epic1.getId());


        Subtask subtask4 = new Subtask("Сдать ТЗ7", "Сделать тесты", 3, Status.NEW, epic1.getId());


        System.out.println(file.getEpicList());
        System.out.println(file.getSubtaskList());
        System.out.println(file.getTaskList());
        System.out.println();
        FileBackedTaskManager file1 = FileBackedTaskManager.loadFromFile(Path.of("tasks.cvs").toFile());
        System.out.println(file1.getEpicList());
        System.out.println(file1.getSubtaskList());
        System.out.println(file1.getTaskList());
    }
}
