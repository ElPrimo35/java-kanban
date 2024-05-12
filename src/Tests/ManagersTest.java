package Tests;

import managers.HistoryManager;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ManagersTest {
    @Test
    void shouldReturnManager() {
        TaskManager taskManager = Managers.getDefault();
        Assertions.assertEquals(new ArrayList<>(), taskManager.getTaskList());
    }

    @Test
    void shouldReturnHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        Assertions.assertEquals(new ArrayList<>(), historyManager.getHistory());
    }
}
