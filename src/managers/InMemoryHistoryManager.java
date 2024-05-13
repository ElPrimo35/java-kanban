package managers;

import models.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> historyList = new ArrayList<>();
    @Override
    public List<Task> getHistory() {
        return historyList;
    }
    @Override
    public void addTask(Task task) {
        if (task == null) return;

        if (historyList.size() == 10) {
            historyList.remove(0);
        }
        historyList.add(task);
    }
}
