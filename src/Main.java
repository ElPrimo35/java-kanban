import managers.FileBackedTaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;


public class Main {

    public static void main(String[] args) {


        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager("tasks.cvs");


        Task task1 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = fileBackedTaskManager.createTask(task1);

        Task task2 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondTaskId = fileBackedTaskManager.createTask(task2);

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = fileBackedTaskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = fileBackedTaskManager.createSubtask(subtask);


        Epic epic1 = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1225, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int epic1Id = fileBackedTaskManager.createEpic(epic1);

        Subtask subtask1 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic1Id,
                LocalDateTime.of(1225, Month.JANUARY, 1, 1, 1),
                Duration.ofHours(12)
        );
        int subtask1Id = fileBackedTaskManager.createSubtask(subtask1);


        Subtask subtask2 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic1Id,
                LocalDateTime.of(1226, Month.JANUARY, 1, 1, 1),
                Duration.ofHours(12)
        );
        int subtask2Id = fileBackedTaskManager.createSubtask(subtask2);

        Subtask subtask3 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic1Id,
                LocalDateTime.of(1227, Month.JANUARY, 1, 1, 1),
                Duration.ofHours(12)
        );
        int subtask3Id = fileBackedTaskManager.createSubtask(subtask3);


        FileBackedTaskManager fileBackedTaskManager1 = FileBackedTaskManager.loadFromFile(Path.of("tasks.cvs").toFile());

        System.out.println(fileBackedTaskManager.getEpicById(epic1Id).getStartTime());
        System.out.println(fileBackedTaskManager.getEpicById(epic1Id).getEndTime());
        System.out.println(fileBackedTaskManager1.getEpicById(epic1Id).getStartTime());
        System.out.println(fileBackedTaskManager1.getEpicById(epic1Id).getEndTime());

        System.out.println(fileBackedTaskManager1.getTaskList());
        System.out.println(fileBackedTaskManager1.getPrioritizedTasks());


    }
}
