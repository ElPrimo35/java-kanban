package managers;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;


public class FileBackedTaskManagerTest {
    @Test
    void shouldSaveToFile() throws IOException {
        File file = File.createTempFile("testDirectory", "taskTest.cvs");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getName());

        Task firstTask = new Task("Уборка", "Убраться в доме", 1, Status.NEW);
        int firstTaskId = fileBackedTaskManager.createTask(firstTask);

        Task secondTask = new Task("Сходить погулять", "Прийти в центр города", 2, Status.NEW);
        int secondTaskId = fileBackedTaskManager.createTask(secondTask);

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);
        int firstEpicId = fileBackedTaskManager.createEpic(epic);

        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());
        int firstSubtaskId = fileBackedTaskManager.createSubtask(subtask);


        FileReader reader = new FileReader(Path.of(file.getName()).toFile());
        BufferedReader br = new BufferedReader(reader);
        String finalLine = "";
        while (br.ready()) {
            String line = br.readLine();
            finalLine = finalLine + line;
        }
        Assertions.assertEquals(finalLine, "id,type,name,status,description,epic1,TASK,Уборка,NEW,Убраться в доме,2,TASK,Сходить погулять,NEW,Прийти в центр города,3,EPIC,Выучить джаву,NEW,Пройти курс от яндекса,4,SUBTASK,Сдать ТЗ4,NEW,Сделать тесты,3,");
    }

    @Test
    void shouldLoadFromFile() throws IOException {
        File file = File.createTempFile("testDirectory", "taskTest.cvs");
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getName());

        Task firstTask = new Task("Уборка", "Убраться в доме", 1, Status.NEW);
        int firstTaskId = fileBackedTaskManager.createTask(firstTask);

        Task secondTask = new Task("Сходить погулять", "Прийти в центр города", 2, Status.NEW);
        int secondTaskId = fileBackedTaskManager.createTask(secondTask);

        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW);
        int firstEpicId = fileBackedTaskManager.createEpic(epic);

        Subtask subtask = new Subtask("Сдать ТЗ4", "Сделать тесты", 3, Status.NEW, epic.getId());
        int firstSubtaskId = fileBackedTaskManager.createSubtask(subtask);


        FileBackedTaskManager file2 = FileBackedTaskManager.loadFromFile(Path.of(file.getName()).toFile());

        Assertions.assertEquals(fileBackedTaskManager.getTaskList(), file2.getTaskList());
        Assertions.assertEquals(fileBackedTaskManager.getEpicById(firstEpicId), file2.getEpicById(firstEpicId));
        Assertions.assertEquals(fileBackedTaskManager.getSubtaskList(), file2.getSubtaskList());
    }
}
