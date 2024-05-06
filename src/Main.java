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
        int firstTaskId = taskManager.createTask(firstTask);


        Task secondTask = new Task(
                "Сходить погулять",
                "Прийти в центр города",
                1,
                Status.NEW
        );
        int secondTaskId = taskManager.createTask(secondTask);



        Epic firstEpic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW
        );
        int firstEpicId = taskManager.createEpic(firstEpic);

        Subtask firstSubtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                firstEpicId
        );
        int firstSubtaskId = taskManager.createSubtask(firstSubtask);



        Subtask secondSubtask = new Subtask(
                "Сдать дипломный проект",
                "Сдать все остальные ТЗ",
                3,
                Status.NEW,
                firstEpicId
        );
        int secondSubtaskId = taskManager.createSubtask(secondSubtask);


        Epic secondEpic = new Epic(
                "Помыть посуду",
                "собраться с силами и встать с кровати",
                -1,
                Status.NEW
        );
        int secondEpicId = taskManager.createEpic(secondEpic);


        Subtask thirdSubtask = new Subtask(
                "Сдать дипломный проект",
                "Сдать все остальные ТЗ",
                6,
                Status.NEW,
                secondEpicId
        );
        int thirdSubtaskId = taskManager.createSubtask(thirdSubtask);


        Subtask updateThirdSubtask = new Subtask(
                "Сдать дипломный проект",
                "Сдать все остальные ТЗ",
                thirdSubtaskId,
                Status.DONE,
                secondEpicId
        );
        taskManager.updateSubtask(updateThirdSubtask);

        taskManager.deleteTaskById(firstTaskId);
        taskManager.deleteEpicById(firstEpicId);


        System.out.println(taskManager.getTaskById(firstTaskId));
        System.out.println(taskManager.getTaskById(secondTaskId));
        System.out.println(taskManager.getEpicById(firstEpicId));
        System.out.println(taskManager.getSubtaskById(firstSubtaskId));
        System.out.println(taskManager.getSubtaskById(secondSubtaskId));
        System.out.println(taskManager.getEpicById(secondEpicId));
        System.out.println(taskManager.getSubtaskById(thirdSubtaskId));
    }
}
