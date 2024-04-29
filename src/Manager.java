import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private HashMap<Integer, Task> tasks = new HashMap<>();

    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private HashMap<Integer, Epic> epics = new HashMap<>();

    private int nextId = 1;
    public int createTask(Task task) {
        task.id = nextId;
        nextId++;
        tasks.put(task.id, task);
        return task.id;
    }

    public int createSubtask(Subtask task) {
        task.id = nextId;
        nextId++;
        subtasks.put(task.id, task);
        return task.id;
    }

    private void syncEpic(Epic epic) {
        for (Integer subtaskId : epic.subtaskIds) {
            Subtask subtask = subtasks.get(subtaskId);
            subtask.epicId = epic.id;
        }
    }

    public int createEpic(Epic epic) {
        epic.id = nextId;
        nextId++;
        epics.put(epic.id, epic);

        syncEpic(epic);

        return epic.id;
    }

    public  ArrayList<Task> getTaskList() {
        return new ArrayList<Task>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    public void deleteTask() {
        tasks.clear();
    }

    public void deleteSubtask() {
        subtasks.clear();
    }

    public void deleteEpic() {
        epics.clear();
    }

    public Task GetTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public void updateTask(Task task) {
        tasks.put(task.id, task);
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.id, subtask);
        if (subtask.epicId == 0) {
            return;
        }

        Epic epic = epics.get(subtask.epicId);

        syncEpic(epic);

    }

    public void updateEpic(Epic epic) {
        epics.put(epic.id, epic);
        syncEpic(epic);
    }

    public void deleteTaskById (int taskId) {
        tasks.remove(taskId);
    }

    public void deleteSubtaskById (int subtaskId) {
        subtasks.remove(subtaskId);
    }

    public void deleteEpicById (int epicId) {
        epics.remove(epicId);
    }

    public void getAllSubtasksByEpic(Epic epic) {
        for (Integer subtaskId : epic.subtaskIds) {
            subtasks.get(subtaskId);
        }
    }
}
