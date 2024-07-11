package models;

import managers.InMemoryTaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

public class SubtaskTest {

    InMemoryTaskManager taskManager = new InMemoryTaskManager();

    @Test
    void shouldBeEquals() {
        Epic epic = new Epic("Выучить джаву", "Пройти курс от яндекса", -1, Status.NEW, LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11), Duration.ofHours(12));
        int firstEpicId = taskManager.createEpic(epic);


        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)

        );

        Subtask subtask2 = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                subtask.getId(),
                Status.NEW,
                firstEpicId,
                LocalDateTime.of(1224, Month.JANUARY, 2, 2, 11),
                Duration.ofHours(12)
        );
        Assertions.assertEquals(subtask, subtask2);
    }
}
