import managers.FileBackedTaskManager;
import managers.InMemoryTaskManager;
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
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();


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

        System.out.println(fileBackedTaskManager.getTaskList());
        System.out.println(fileBackedTaskManager.getEpicList());
        System.out.println(fileBackedTaskManager.getSubtaskList());
        //System.out.println(file1.getPrioritizedTasks());

    }
}
