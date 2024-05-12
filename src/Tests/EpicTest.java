package Tests;

import models.Epic;
import models.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicTest {

    @Test
    void shouldBeEquals() {
        Epic epic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW
        );

        Epic epic2 = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                epic.getId(),
                Status.NEW
        );

        Assertions.assertEquals(epic, epic2);
    }
}
