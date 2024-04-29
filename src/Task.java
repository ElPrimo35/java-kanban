public class Task {
    protected String taskName;
    protected String description;
    protected int id;
    protected Status status;

    public Task(String taskName, String description, int id, Status status) {
        this.taskName = taskName;
        this.description = description;
        this.id = id;
        this.status = status;
    }
}
