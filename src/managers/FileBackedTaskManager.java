package managers;

import exceptions.ManagerSaveException;
import models.*;

import java.io.*;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final String fileName;

    public FileBackedTaskManager(String fileName) {
        this.fileName = fileName;
    }

    public String taskToString(Task task) {
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
        return builder.toString();
    }

    public static Task fromString(String value) {
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
            Subtask subtask1 = new Subtask(
                    name,
                    description,
                    id,
                    status,
                    epicID
            );
            return subtask1;
        }
        if (split[1].equals(typeTask)) {
            return new Task(
                    name,
                    description,
                    id,
                    status
            );
        }
        if (split[1].equals(typeEpic)) {
            return new Epic(
                    name,
                    description,
                    id,
                    status
            );
        }
        return null;
    }

    public void save() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write("id,type,name,status,description,epic" + "\n");
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
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                Task task = fromString(line);
                if (task == null) {
                    continue;
                }

                if (task instanceof Epic) {
                    fileBackedTaskManager.epics.put(task.getId(), (Epic) task);
                    continue;
                }
                if (task instanceof Subtask) {
                    fileBackedTaskManager.subtasks.put(task.getId(), (Subtask) task);
                    fileBackedTaskManager.epics.get(((Subtask) task).getEpicId()).getSubtaskIds().add(task.getId());
                    continue;
                }
                fileBackedTaskManager.tasks.put(task.getId(), task);
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при выгрузке файлов.", e);
        }
        fileBackedTaskManager.nextId = fileBackedTaskManager.epics.size() + fileBackedTaskManager.subtasks.size() + fileBackedTaskManager.tasks.size();
        return fileBackedTaskManager;
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
