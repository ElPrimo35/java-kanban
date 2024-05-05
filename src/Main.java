import managers.TaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();

        Task firstTask = new Task(
                "Уборка",
                "Убраться в доме",
                1,
                Status.NEW
        );
        taskManager.createTask(firstTask);


        Task secondTask = new Task(
                "Сходить погулять",
                "Прийти в центр города",
                1,
                Status.NEW
        );
        taskManager.createTask(secondTask);


        Epic firstEpic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW
        );
        taskManager.createEpic(firstEpic);

        Subtask firstSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                3
        );
        taskManager.createSubtask(firstSubtask);



        Subtask secondSubtask = new Subtask(
                "Сдать дипломный проект",
                "Сдать все остальные ТЗ",
                3,
                Status.NEW,
                1
        );
        taskManager.createSubtask(secondSubtask);


        Epic secondEpic = new Epic(
                "Помыть посуду",
                "собраться с силами и встать с кровати",
                -1,
                Status.NEW
        );
        taskManager.createEpic(secondEpic);


        Subtask thirdSubtask = new Subtask(
                "Сдать дипломный проект",
                "Сдать все остальные ТЗ",
                6,
                Status.NEW,
                1
        );
        taskManager.createSubtask(thirdSubtask);


        Subtask updateThirdSubtask = new Subtask(
                "Сдать дипломный проект",
                "Сдать все остальные ТЗ",
                3,
                Status.DONE,
                1
        );
        taskManager.updateSubtask(updateThirdSubtask);



        System.out.println(firstTask);
        System.out.println(secondTask);
        System.out.println(firstEpic);
        System.out.println(firstSubtask);
        System.out.println(secondSubtask);
        System.out.println(secondEpic);
        System.out.println(thirdSubtask);
        System.out.println(updateThirdSubtask);
        System.out.println(secondEpic);



    }
}
