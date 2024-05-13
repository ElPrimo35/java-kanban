package managers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ManagersTest {
    @Test
    void shouldReturnManager() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    void shouldReturnHistoryManager() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }
}
