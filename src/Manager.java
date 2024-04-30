import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Manager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();

    private int nextId = 1;


    public int createTask(Task task) {
        task.id = nextId;
        nextId++;
        Status status = task.status;
        tasks.put(task.id, task);
        epics.get(task.id).subtaskIds.add(task.id);
        return task.id;
    }

    public void updateTask(Task task) {
        tasks.put(task.id, task);
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
        Status status = epic.status;
        epics.put(epic.id, epic);

        syncEpic(epic);

        return epic.id;
    }

    public void updateEpic(Epic epic) {
        epics.put(epic.id, epic);
        syncEpic(epic);
    }

    public int createSubtask(Subtask subtask) {
        subtask.id = nextId;
        nextId++;
        Status status = subtask.status;
        subtasks.put(subtask.id, subtask);
        return subtask.id;
    }

    public void updateSubtask(Subtask subtask) {
        subtasks.put(subtask.id, subtask);
        if (subtask.epicId == 0) {
            return;
        }
        Epic epic = epics.get(subtask.epicId);
        syncEpic(epic);
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

    public Subtask GetSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public Epic GetEpicById(int epicId) {
        return epics.get(epicId);
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

    public  ArrayList<Task> getTaskList() {
        return new ArrayList<Task>(tasks.values());
    }

    public ArrayList<Subtask> getSubtaskList() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    public List<Subtask> getAllSubtasksByEpic(int epicId) {
        List<Subtask> list = new ArrayList<>();
        for (Integer subtaskId : epics.get(epicId).subtaskIds) {
            list.add(subtasks.get(subtaskId));
        }
        return list;
    }




}
