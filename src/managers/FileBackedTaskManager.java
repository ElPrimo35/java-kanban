package managers;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.io.*;
import java.nio.file.Path;


public class FileBackedTaskManager extends InMemoryTaskManager {
    private final String fileName;

    public FileBackedTaskManager(String fileName) {
        this.fileName = fileName;
    }

    public String taskToString(Task task) {
        String string;
        if (task instanceof Epic) {
            string = task.getId() + "," +
                    TaskType.EPIC + "," +
                    task.getTaskName() + "," +
                    task.getStatus() + "," +
                    task.getDescription();
            return string;
        }
        if (task instanceof Subtask) {
            string = task.getId() + "," +
                    TaskType.SUBTASK + "," +
                    task.getTaskName() + "," +
                    task.getStatus() + "," +
                    task.getDescription() + "," +
                    ((Subtask) task).getEpicId();
            return string;
        }
        string = task.getId() + "," +
                TaskType.TASK + "," +
                task.getTaskName() + "," +
                task.getStatus() + "," +
                task.getDescription();
        return string;

    }

    public static Task fromString(String value) {
        String[] split = value.split(",");
        String TypeTask = TaskType.TASK.toString();
        String TypeEpic = TaskType.EPIC.toString();
        String TypeSubtask = TaskType.SUBTASK.toString();
        int id = Integer.parseInt(split[0]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];

        if (split[1].equals(TypeSubtask)) {
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
        if (split[1].equals(TypeTask)) {
            return new Task(
                    name,
                    description,
                    id,
                    status
            );
        }
        if (split[1].equals(TypeEpic)) {
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
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

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

    public static FileBackedTaskManager loadFromFile(File file) throws IOException {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file.getName());
        FileReader reader = new FileReader(file);
        BufferedReader br = new BufferedReader(reader);
        while(br.ready()) {
            String line = br.readLine();
            Task task = fromString(line);
            if (task instanceof Epic) {
                fileBackedTaskManager.createEpic((Epic) task);
                continue;
            }
            if (task instanceof Subtask) {
                fileBackedTaskManager.createSubtask((Subtask) task);
                continue;
            }
            fileBackedTaskManager.createTask(task);
        }
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
