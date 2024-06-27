package models;

import java.util.Objects;

public class Subtask extends Task {
    protected int epicId;

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Subtask subtask = (Subtask) o;
        return epicId == subtask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicId);
    }

    @Override
    public String toString() {
        return "models.Subtask{" +
                "epicId=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public Subtask(String taskName, String description, int id, Status status, int epicId) {
        super(taskName, description, id, status);
        this.epicId = epicId;
    }
}
