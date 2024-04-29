import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subtaskIds = new ArrayList<>();
    public Epic(String taskName, String description, int id, Status status) {
        super (taskName, description, id, status);
    }
}
