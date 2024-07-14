package managers;

import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


public class InMemoryTaskManager implements TaskManager {
    protected int nextId = 1;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();

    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    protected final Comparator<Task> taskComparator = new Comparator<>() {
        @Override
        public int compare(Task o1, Task o2) {
            if (o1.getStartTime().isAfter(o2.getStartTime())) return 1;
            if (o1.getStartTime().isBefore(o2.getStartTime())) return -1;
            return 0;
        }
    };
    protected final Set<Task> prioritizedTasks = new TreeSet<>(taskComparator);

    @Override
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<>(prioritizedTasks);
    }

    protected boolean isCrossed(Task task) {

        return prioritizedTasks.stream()
                .anyMatch((currentTask) -> {
                    if (task.getId() == currentTask.getId()) {
                        return false;
                    }
                    return (currentTask.getStartTime().isEqual(task.getStartTime()) || currentTask.getStartTime().isBefore(task.getStartTime())) && currentTask.getEndTime().isAfter(task.getStartTime())
                            || (task.getStartTime().isEqual(currentTask.getStartTime()) || task.getStartTime().isBefore(currentTask.getStartTime())) && task.getEndTime().isAfter(currentTask.getStartTime());
                });
    }

    protected void changeEpicStatus(Epic epic) {
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


    protected void setEpicTime(Epic epic) {
        List<Subtask> epicSubtasks = epic.getSubtaskIds().stream()
                .map(subtasks::get)
                .toList();

        if (epicSubtasks.isEmpty()) {
            epic.setStartTime(null);
            epic.setDuration(Duration.ZERO);
            epic.setEndTime(null);
            return;
        }
        LocalDateTime epicStartTime = epicSubtasks.getFirst().getStartTime();
        if (!subtasks.isEmpty()) {
            for (Subtask subtask : epicSubtasks) {
                if (epicStartTime.isAfter(subtask.getStartTime())) {
                    epicStartTime = subtask.getStartTime();
                }
            }
        }
        epic.setStartTime(epicStartTime);

        Duration duration = Duration.ZERO;
        for (Subtask subtask : epicSubtasks) {
            duration = duration.plus(subtask.getDuration());
        }
        epic.setDuration(duration);

        LocalDateTime epicEndTime = epicSubtasks.getFirst().getEndTime();
        for (Subtask subtask : epicSubtasks) {
            if (epicEndTime.isBefore(subtask.getEndTime())) {
                epicEndTime = subtask.getEndTime();
            }
        }
        epic.setEndTime(epicEndTime);
    }

    @Override
    public int createTask(Task task) {
        if (!isCrossed(task)) {
            task.setId(nextId);
            nextId++;
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
        }
        return task.getId();
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId()) && !isCrossed(task)) {
            prioritizedTasks.remove(tasks.get(task.getId()));
            tasks.put(task.getId(), task);
            prioritizedTasks.add(task);
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

        if (epics.containsKey(subtask.getEpicId()) && !isCrossed(subtask)) {
            subtask.setId(nextId);
            nextId++;
            epics.get(subtask.getEpicId()).getSubtaskIds().add(subtask.getId());
            subtasks.put(subtask.getId(), subtask);
            prioritizedTasks.add(subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
            setEpicTime(epics.get(subtask.getEpicId()));
        }
        return subtask.getId();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId()) && epics.containsKey(subtask.getEpicId()) && !isCrossed(subtask)) {
            prioritizedTasks.remove(subtasks.get(subtask.getId()));
            subtasks.put(subtask.getId(), subtask);
            changeEpicStatus(epics.get(subtask.getEpicId()));
            setEpicTime(epics.get(subtask.getEpicId()));
            prioritizedTasks.add(subtask);
        }
    }

    @Override
    public void deleteAllTasks() {
        tasks.values().forEach(task -> {
            historyManager.remove(task.getId());
            prioritizedTasks.remove(tasks.get(task.getId()));
        });
        tasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {

        subtasks.values().forEach(prioritizedTasks::remove);

        subtasks.clear();
        epics.values().forEach(epic -> {
            epic.getSubtaskIds().forEach(historyManager::remove);
            epic.getSubtaskIds().clear();
            epic.setStatus(Status.NEW);
            setEpicTime(epic);
        });
    }

    @Override
    public void deleteAllEpics() {

        epics.values().forEach(epic -> {
            epic.getSubtaskIds().forEach(historyManager::remove);
            historyManager.remove(epic.getId());
        });
        prioritizedTasks.removeIf(task -> task instanceof Subtask);
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
        prioritizedTasks.remove(tasks.get(taskId));
        tasks.remove(taskId);
        historyManager.remove(taskId);
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {

        if (subtasks.containsKey(subtaskId)) {
            prioritizedTasks.remove(subtasks.get(subtaskId));
            changeEpicStatus(epics.get(subtasks.get(subtaskId).getEpicId()));
            setEpicTime(epics.get(subtasks.get(subtaskId).getEpicId()));
            epics.get(subtasks.get(subtaskId).getEpicId()).getSubtaskIds().remove(Integer.valueOf(subtaskId));
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }

    }

    @Override
    public void deleteEpicById(int epicId) {
        epics.get(epicId).getSubtaskIds().forEach(subtaskId -> {
            prioritizedTasks.remove(subtasks.get(subtaskId));
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        });
        epics.remove(epicId);
        historyManager.remove(epicId);
    }

    @Override
    public List<Task> getTaskList() {
        return new ArrayList<>(tasks.values());
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
        return epics.get(epicId).getSubtaskIds().stream()
                .map(subtasks::get)
                .toList();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}


