public class Subtask extends Task {
    protected int epicId;

    public Subtask(String taskName, String description, int id, Status status, int epicId) {
        super (taskName, description, id, status);
        this.epicId = epicId;
    }
}
