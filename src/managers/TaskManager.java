package managers;

import models.Epic;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    public int createTask(Task task);

    public void updateTask(Task task);

    public int createEpic(Epic epic);

    public void updateEpic(Epic epic);

    public int createSubtask(Subtask subtask);

    public void updateSubtask(Subtask subtask);

    public void deleteAllTasks();

    public void deleteAllSubtasks();

    public void deleteAllEpics();

    public Task getTaskById(int taskId);

    public Subtask getSubtaskById(int subtaskId);

    public Epic getEpicById(int epicId);

    public void deleteTaskById (int taskId);

    public void deleteSubtaskById (int subtaskId);

    public void deleteEpicById (int epicId);

    public  List<Task> getTaskList();

    public List<Subtask> getSubtaskList();

    public List<Epic> getEpicList();

    public List<Subtask> getAllSubtasksByEpic(int epicId);

    public List<Task> getHistory();

}
