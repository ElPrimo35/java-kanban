package managers;

import models.Epic;
import models.Subtask;
import models.Task;

import java.io.IOException;
import java.util.List;

public interface TaskManager {

    int createTask(Task task);

    void updateTask(Task task);

    int createEpic(Epic epic);

    void updateEpic(Epic epic);

    int createSubtask(Subtask subtask);

    void updateSubtask(Subtask subtask);

    void deleteAllTasks();

    void deleteAllSubtasks();

    void deleteAllEpics();

    Task getTaskById(int taskId);

    Subtask getSubtaskById(int subtaskId);

    Epic getEpicById(int epicId);

    void deleteTaskById(int taskId);

    void deleteSubtaskById(int subtaskId);

    void deleteEpicById(int epicId);

    List<Task> getTaskList();

    List<Subtask> getSubtaskList();

    List<Epic> getEpicList();

    List<Subtask> getAllSubtasksByEpic(int epicId);

    List<Task> getHistory();

}
