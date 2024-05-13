package models;

import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubtaskTest {

    InMemoryTaskManager taskManager = new InMemoryTaskManager();
    @Test
    void shouldBeEquals() {
        Epic firstEpic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW
        );
        int firstEpicId = taskManager.createEpic(firstEpic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId
        );

        Subtask subtask2 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                subtask.getId(),
                Status.NEW,
                firstEpicId
        );
        Assertions.assertEquals(subtask, subtask2);
    }
}
