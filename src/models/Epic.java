package models;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subtaskIds = new ArrayList<>();


    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    @Override
    public String toString() {
        return "models.Epic{" +
                "subtaskIds=" + subtaskIds +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public Epic(String taskName, String description, int id, Status status) {
        super (taskName, description, id, status);
    }
}
