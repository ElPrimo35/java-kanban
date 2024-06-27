package managers;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();

    private final HistoryManager historyManager = Managers.getDefaultHistory();


    public int nextId = 1;


    private void changeEpicStatus(Epic epic) {
        List<Subtask> subtaskList = new ArrayList<>();

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

    @Override
    public int createTask(Task task) {
        task.setId(nextId);
        nextId++;
        tasks.put(task.getId(), task);
        return task.getId();
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public int createEpic(Epic epic) {
        epic.setId(nextId);
        nextId++;
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    @Override
    public void updateEpic(Epic epic) {

        if (epics.containsKey(epic.getId())) {
            Epic savedEpic = epics.get(epic.getId());
            savedEpic.setTaskName(epic.getTaskName());
            savedEpic.setDescription(epic.getDescription());
        }
    }

    @Override
    public int createSubtask(Subtask subtask) {

        if (epics.containsKey(subtask.getEpicId())) {
            subtask.setId(nextId);
            nextId++;
            epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
        }
        return subtask.getId();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId()) && epics.containsKey(subtask.getEpicId())) {
            subtasks.put(subtask.getId(), subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
        }
    }

    @Override
    public void deleteAllTasks() {

        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();

    }

    @Override
    public void deleteAllSubtasks() {

        subtasks.clear();
        for (Epic epic : epics.values()) {
            for (int subtaskId : epic.getSubtaskIds()) {
                historyManager.remove(subtaskId);
            }
            epic.getSubtaskIds().clear();
            epic.setStatus(Status.NEW);
        }

    }

    @Override
    public void deleteAllEpics() {

        for (Epic epic : epics.values()) {
            for (int subtaskId : epic.getSubtaskIds()) {
                historyManager.remove(subtaskId);
            }
            historyManager.remove(epic.getId());
        }

        epics.clear();
        subtasks.clear();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public Subtask getSubtaskById(int subtaskId) {
        Subtask subtask = subtasks.get(subtaskId);
        historyManager.addTask(subtask);
        return subtask;
    }

    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public void deleteTaskById(int taskId) {
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {

        if (subtasks.containsKey(subtaskId)) {
            changeEpicStatus(epics.get(subtasks.get(subtaskId).getEpicId()));
            epics.get(subtasks.get(subtaskId).getEpicId()).getSubtaskIds().remove(Integer.valueOf(subtaskId));
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }

    }

    @Override
    public void deleteEpicById(int epicId) {

        for (Integer subtaskId : epics.get(epicId).getSubtaskIds()) {
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    @Override
    public List<Task> getTaskList() {
        return new ArrayList<Task>(tasks.values());
    }

    @Override
    public List<Subtask> getSubtaskList() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getEpicList() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasksByEpic(int epicId) {
        List<Subtask> list = new ArrayList<>();
        for (Integer subtaskId : epics.get(epicId).getSubtaskIds()) {
            list.add(subtasks.get(subtaskId));
        }
        return list;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}


