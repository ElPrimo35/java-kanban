public class Subtask extends Task {
    protected int epicId;

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public Subtask(String taskName, String description, int id, Status status, int epicId) {
        super (taskName, description, id, status);
        this.epicId = epicId;
    }
}
