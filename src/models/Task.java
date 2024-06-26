package models;

import java.util.Objects;

public class Task {
    protected String taskName;
    protected String description;
    protected int id;
    protected Status status;


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "models.Task{" + "taskName='" + taskName + '\'' + ", description='" + description + '\'' + ", id=" + id + ", status=" + status + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && Objects.equals(taskName, task.taskName) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, description, id, status);
    }

    public Task(String taskName, String description, int id, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.id = id;
        this.status = status;
    }
}
