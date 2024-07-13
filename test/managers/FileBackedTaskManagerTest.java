package managers;

import exceptions.ManagerLoadException;
import exceptions.ManagerSaveException;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    @Override
    protected FileBackedTaskManager createTaskManager() {
        try {
            return new FileBackedTaskManager(File.createTempFile("testDirectory", ".cvs").getName());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Test
    void shouldSaveToFile() throws IOException {
        File file = File.createTempFile("testDirectory1", ".cvs");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getName());

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


        FileReader reader = new FileReader(Path.of(file.getName()).toFile());
        BufferedReader br = new BufferedReader(reader);
        String finalLine = "";
        while (br.ready()) {
            String line = br.readLine();
            finalLine = finalLine + line;
        }
        Assertions.assertEquals(finalLine, "id,type,name,status,description,epic,startTime,Duration1,TASK,Уборка,NEW,Убраться в доме,1222-01-02T02:11,PT12H,2,TASK,Уборка,NEW,Убраться в доме,1222-01-02T02:11,PT12H,3,EPIC,Выучить джаву,NEW,Пройти курс от яндекса,1224-01-02T02:11,PT12H,4,SUBTASK,Сдать ТЗ4,NEW,Сделать тесты,3,1224-01-02T02:11,PT12H,");
    }

    @Test
    void shouldLoadFromFile() throws IOException {
        File file = File.createTempFile("testDirectory2", ".cvs");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getName());

        Task task1 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstTaskId = fileBackedTaskManager.createTask(task1);

        Task task2 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1223, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
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
        List<Task> prioritizedTaskTestList = new ArrayList<>();
        prioritizedTaskTestList.add(task1);
        prioritizedTaskTestList.add(task2);
        prioritizedTaskTestList.add(subtask);

        FileBackedTaskManager file2 = FileBackedTaskManager.loadFromFile(Path.of(file.getName()).toFile());

        Assertions.assertEquals(prioritizedTaskTestList, file2.getPrioritizedTasks());
        Assertions.assertEquals(fileBackedTaskManager.getTaskList(), file2.getTaskList());
        Assertions.assertEquals(fileBackedTaskManager.getEpicById(firstEpicId), file2.getEpicById(firstEpicId));
        Assertions.assertEquals(fileBackedTaskManager.getSubtaskList(), file2.getSubtaskList());
        Assertions.assertEquals(fileBackedTaskManager.getTaskById(firstTaskId).getStartTime(), file2.getTaskById(firstTaskId).getStartTime());
        Assertions.assertEquals(fileBackedTaskManager.getTaskById(firstTaskId).getEndTime(), file2.getTaskById(firstTaskId).getEndTime());
        Assertions.assertEquals(fileBackedTaskManager.getTaskById(firstTaskId).getDuration(), file2.getTaskById(firstTaskId).getDuration());
        Assertions.assertEquals(fileBackedTaskManager.getSubtaskById(firstSubtaskId).getStartTime(), file2.getSubtaskById(firstSubtaskId).getStartTime());
        Assertions.assertEquals(fileBackedTaskManager.getSubtaskById(firstSubtaskId).getEndTime(), file2.getSubtaskById(firstSubtaskId).getEndTime());
        Assertions.assertEquals(fileBackedTaskManager.getSubtaskById(firstSubtaskId).getDuration(), file2.getSubtaskById(firstSubtaskId).getDuration());
        Assertions.assertEquals(fileBackedTaskManager.getEpicById(firstEpicId).getStartTime(), file2.getEpicById(firstEpicId).getStartTime());
        Assertions.assertEquals(fileBackedTaskManager.getEpicById(firstEpicId).getEndTime(), file2.getEpicById(firstEpicId).getEndTime());
        Assertions.assertEquals(fileBackedTaskManager.getEpicById(firstEpicId).getDuration(), file2.getEpicById(firstEpicId).getDuration());
    }

    @Test
    void shouldThrowExceptionOnLoad() {
        assertThrows(ManagerLoadException.class, () -> FileBackedTaskManager.loadFromFile(new File("NotExistFile.csv")));
    }

    @Test
    void shouldThrowExceptionOnSave() throws IOException {
        Path directory = Files.createTempDirectory("SomeDirectory");

        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(directory.toAbsolutePath().toString());
        Task task1 = new Task("Уборка", "Убраться в доме", 1, Status.NEW, LocalDateTime.of(1222, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        assertThrows(ManagerSaveException.class, () -> fileBackedTaskManager.createTask(task1));
    }
}
