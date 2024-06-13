package managers;

import models.Task;

import java.util.List;
import java.util.Set;

public interface HistoryManager {
    void addTask(Task task);

    void remove(int id);

    List<Task> getHistory();
}
