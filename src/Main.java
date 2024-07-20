import managers.FileBackedTaskManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;


public class Main {

    public static void main(String[] args) {


        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager("tasks.cvs");
        InMemoryTaskManager inMemoryTaskManager = (InMemoryTaskManager) Managers.getDefault();


        Task task1 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = inMemoryTaskManager.createTask(task1);

        Task task2 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int secondTaskId = inMemoryTaskManager.createTask(task2);

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = inMemoryTaskManager.createEpic(epic);
        System.out.println(inMemoryTaskManager.getEpicById(firstEpicId).getSubtaskIds());

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        int firstSubtaskId = inMemoryTaskManager.createSubtask(subtask);


        Epic epic1 = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1225, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int epic1Id = inMemoryTaskManager.createEpic(epic1);


        Subtask subtask1 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic1Id,
                LocalDateTime.of(1225, Month.JANUARY, 1, 1, 1),
                Duration.ofHours(12)
        );
        int subtask1Id = inMemoryTaskManager.createSubtask(subtask1);


        Subtask subtask2 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic1Id,
                LocalDateTime.of(1226, Month.JANUARY, 1, 1, 1),
                Duration.ofHours(12)
        );
        int subtask2Id = inMemoryTaskManager.createSubtask(subtask2);

        Subtask subtask3 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic1Id,
                LocalDateTime.of(1227, Month.JANUARY, 1, 1, 1),
                Duration.ofHours(12)
        );
        int subtask3Id = inMemoryTaskManager.createSubtask(subtask3);

        inMemoryTaskManager.getHistory();


    }
}
