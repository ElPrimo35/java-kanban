package managers;

import exceptions.ManagerLoadException;
import exceptions.ManagerSaveException;
import models.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final String fileName;

    public FileBackedTaskManager(String fileName) {
        this.fileName = fileName;
    }

    protected String taskToString(Task task) {
        StringBuilder builder = new StringBuilder();
        if (task instanceof Epic) {
            builder.append(task.getId());
            builder.append(',');
            builder.append(TaskType.EPIC);
            builder.append(',');
            builder.append(task.getTaskName());
            builder.append(',');
            builder.append(task.getStatus());
            builder.append(',');
            builder.append(task.getDescription());
            builder.append(",");
            builder.append(task.getStartTime());
            builder.append(",");
            builder.append(task.getDuration());

            return builder.toString();
        }
        if (task instanceof Subtask) {
            builder.append(task.getId());
            builder.append(',');
            builder.append(TaskType.SUBTASK);
            builder.append(',');
            builder.append(task.getTaskName());
            builder.append(',');
            builder.append(task.getStatus());
            builder.append(',');
            builder.append(task.getDescription());
            builder.append(',');
            builder.append(((Subtask) task).getEpicId());
            builder.append(",");
            builder.append(task.getStartTime());
            builder.append(",");
            builder.append(task.getDuration());
            return builder.toString();
        }
        builder.append(task.getId());
        builder.append(',');
        builder.append(TaskType.TASK);
        builder.append(',');
        builder.append(task.getTaskName());
        builder.append(',');
        builder.append(task.getStatus());
        builder.append(',');
        builder.append(task.getDescription());
        builder.append(",");
        builder.append(task.getStartTime());
        builder.append(",");
        builder.append(task.getDuration());
        return builder.toString();
    }

    private Task fromString(String value) {
        String[] split = value.split(",");
        String typeTask = TaskType.TASK.toString();
        String typeEpic = TaskType.EPIC.toString();
        String typeSubtask = TaskType.SUBTASK.toString();
        int id = Integer.parseInt(split[0]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];

        if (split[1].equals(typeSubtask)) {
            int epicID = Integer.parseInt(split[5]);
            LocalDateTime startTime = LocalDateTime.parse(split[6]);
            Duration duration = Duration.parse(split[7]);
            Subtask subtask1 = new Subtask(
                    name,
                    description,
                    id,
                    status,
                    epicID,
                    startTime,
                    duration
            );
            return subtask1;
        }
        if (split[1].equals(typeTask)) {
            LocalDateTime startTime = LocalDateTime.parse(split[5]);
            Duration duration = Duration.parse(split[6]);
            return new Task(
                    name,
                    description,
                    id,
                    status,
                    startTime,
                    duration
            );
        }
        if (split[1].equals(typeEpic)) {
            LocalDateTime startTime = LocalDateTime.parse(split[5]);
            Duration duration = Duration.parse(split[6]);
            return new Epic(
                    name,
                    description,
                    id,
                    status,
                    startTime,
                    duration
            );
        }
        return null;
    }

    private void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("id,type,name,status,description,epic,startTime,Duration" + "\n");
            for (Task task : tasks.values()) {
                String taskFile = taskToString(task);
                bw.write(taskFile + "," + "\n");
            }

            for (Epic epic : epics.values()) {
                String epicFile = taskToString(epic);
                bw.write(epicFile + "," + "\n");
                for (Integer subtaskId : epic.getSubtaskIds()) {
                    String subtaskFile = taskToString(subtasks.get(subtaskId));
                    bw.write(subtaskFile + "," + "\n");
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения данных", e);
        }
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getName());
        int idCounter = 0;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                Task task = fileBackedTaskManager.fromString(line);
                if (task == null) {
                    continue;
                }
                if (task instanceof Epic) {
                    fileBackedTaskManager.epics.put(task.getId(), (Epic) task);


                    if (task.getId() > idCounter) idCounter = task.getId();
                    continue;
                }
                if (task instanceof Subtask) {
                    fileBackedTaskManager.subtasks.put(task.getId(), (Subtask) task);
                    fileBackedTaskManager.epics.get(((Subtask) task).getEpicId()).getSubtaskIds().add(task.getId());
                    fileBackedTaskManager.prioritizedTasks.add(task);
                    if (task.getId() > idCounter) idCounter = task.getId();
                    continue;
                }
                fileBackedTaskManager.tasks.put(task.getId(), task);
                fileBackedTaskManager.prioritizedTasks.add(task);
                if (task.getId() > idCounter) idCounter = task.getId();
            }
        } catch (IOException e) {
            throw new ManagerLoadException("Ошибка при выгрузке файлов.", e);
        }
        fileBackedTaskManager.nextId = idCounter;
        for (Epic epic : fileBackedTaskManager.getEpicList()) {
            fileBackedTaskManager.setEpicTime(epic);
        }
        return fileBackedTaskManager;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }

    @Override
    public int createTask(Task task) {
        super.createTask(task);
        save();
        return task.getId();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public int createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public int createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
        return subtask.getId();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteTaskById(int taskId) {
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteSubtaskById(int subtaskId) {
        super.deleteSubtaskById(subtaskId);
        save();
    }

    @Override
    public void deleteEpicById(int epicId) {
        super.deleteEpicById(epicId);
        save();
    }
}
