package managers;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    private int nextId = 1;


    private void changeEpicStatus(Epic epic) {
        ArrayList<Subtask> subtaskList = new ArrayList<>();

        for (Integer subtaskId : epic.getSubtaskIds()) {
            subtaskList.add(subtasks.get(subtaskId));
        }

        if (subtaskList.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : subtaskList) {
            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
                break;
            }
        }
        if (allNew) {
            epic.setStatus(Status.NEW);
            return;
        }

        for (Subtask subtask : subtaskList) {
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
                break;
            }
        }
        if (allDone) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);
    }

    public int createTask(Task task) {
        task.setId(nextId);
        nextId++;
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public int createEpic(Epic epic) {
        epic.setId(nextId);
        nextId++;
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.put(epic.getId(), epic);
        }
    }

    public void createSubtask(Subtask subtask) {

        subtask.setId(nextId);
        nextId++;

        if (epics.containsKey(subtask.getEpicId())) {
            epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId()) && epics.containsKey(subtask.getEpicId())) {
            subtasks.put(subtask.getId(), subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllSubtasks() {

        for (Subtask subtask : subtasks.values()) {
            epics.get(subtask.getEpicId()).getSubtaskIds().clear();
        }
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.setStatus(Status.NEW);
        }
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Task getTaskById(int taskId) {
        return tasks.get(taskId);
    }

    public Subtask getSubtaskById(int subtaskId) {
        return subtasks.get(subtaskId);
    }

    public Epic getEpicById(int epicId) {
        return epics.get(epicId);
    }

    public void deleteTaskById (int taskId) {
        tasks.remove(taskId);
    }

    public void deleteSubtaskById (int subtaskId) {
        epics.get(subtasks.get(subtaskId).getEpicId()).getSubtaskIds().remove(subtaskId);
        subtasks.remove(subtaskId);
        changeEpicStatus(epics.get(subtasks.get(subtaskId).getEpicId()));
    }

    public void deleteEpicById (int epicId) {

        for (Subtask subtask : subtasks.values()) {
            if (epics.get(epicId).getSubtaskIds().contains(subtask.getId())) {
                subtasks.remove(subtask.getId());
            }
        }
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
        for (Integer subtaskId : epics.get(epicId).getSubtaskIds()) {
            list.add(subtasks.get(subtaskId));
        }
        return list;
    }
}
