package managers;

import models.Task;
import java.util.List;

public interface HistoryManager {
    void addTask(Task task);

    void remove(int id);

    List<Task> getHistory();
}
