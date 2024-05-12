package Tests;

import models.Status;
import models.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    void shouldBeEquals() {
        Task task1 = new Task(
                "Уборка",
                "Убраться в доме",
                1,
                Status.NEW
        );


        Task task2 = new Task(
                "Уборка",
                "Убраться в доме",
                task1.getId(),
                Status.NEW
        );


        Assertions.assertEquals(task1, task2);
    }

}
