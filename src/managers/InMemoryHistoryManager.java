package managers;

import models.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private List<Task> historyList = new ArrayList<>();
    @Override
    public List<Task> getHistory() {
        return historyList;
    }
    @Override
    public void addTask(Task task) {
        if (historyList.size() < 10) {
            historyList.add(task);
        }
        if (historyList.size() == 10) {
            historyList.remove(0);
            historyList.add(task);
        }
    }
}
