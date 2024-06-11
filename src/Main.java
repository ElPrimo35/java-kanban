import managers.InMemoryHistoryManager;
import managers.InMemoryTaskManager;
import models.Epic;
import models.Status;
import models.Subtask;
import models.Task;

public class Main {

    public static void main(String[] args) {

//        InMemoryTaskManager taskManager = new InMemoryTaskManager();
//
//
//        Task firstTask = new Task(
//                "Уборка",
//                "Убраться в доме",
//                1,
//                Status.NEW
//        );
//        int firstTaskId = taskManager.createTask(firstTask);
//
//
//        Task secondTask = new Task(
//                "Сходить погулять",
//                "Прийти в центр города",
//                1,
//                Status.NEW
//        );
//        int secondTaskId = taskManager.createTask(secondTask);
//
//
//
//        Epic firstEpic = new Epic(
//                "Выучить джаву",
//                "Пройти курс от яндекса",
//                -1,
//                Status.NEW
//        );
//        int firstEpicId = taskManager.createEpic(firstEpic);
//
//        Subtask firstSubtask = new Subtask(
//                "Сдать ТЗ4",
//                "Сделать тесты",
//                3,
//                Status.NEW,
//                firstEpicId
//        );
//        int firstSubtaskId = taskManager.createSubtask(firstSubtask);
//
//
//
//        Subtask secondSubtask = new Subtask(
//                "Сдать дипломный проект",
//                "Сдать все остальные ТЗ",
//                3,
//                Status.NEW,
//                firstEpicId
//        );
//        int secondSubtaskId = taskManager.createSubtask(secondSubtask);
//
//
//        Epic secondEpic = new Epic(
//                "Помыть посуду",
//                "собраться с силами и встать с кровати",
//                -1,
//                Status.NEW
//        );
//        int secondEpicId = taskManager.createEpic(secondEpic);
//
//
//        Subtask thirdSubtask = new Subtask(
//                "Сдать дипломный проект",
//                "Сдать все остальные ТЗ",
//                6,
//                Status.NEW,
//                secondEpicId
//        );
//        int thirdSubtaskId = taskManager.createSubtask(thirdSubtask);
//
//
//        Subtask updateThirdSubtask = new Subtask(
//                "Сдать дипломный проект",
//                "Сдать все остальные ТЗ",
//                thirdSubtaskId,
//                Status.DONE,
//                secondEpicId
//        );
//        taskManager.updateSubtask(updateThirdSubtask);
//
//
//        System.out.println(taskManager.getHistory());
//
//
//        System.out.println(taskManager.getTaskById(firstTaskId));
//        System.out.println(taskManager.getTaskById(secondTaskId));
//        System.out.println(taskManager.getEpicById(firstEpicId));
//        System.out.println(taskManager.getSubtaskById(firstSubtaskId));
//
//        System.out.println(taskManager.getHistory());
//
//        System.out.println(taskManager.getSubtaskById(secondSubtaskId));
//        System.out.println(taskManager.getEpicById(secondEpicId));
//        System.out.println(taskManager.getSubtaskById(thirdSubtaskId));
//
//
//        System.out.println(taskManager.getHistory());
//
//        taskManager.getEpicById(3);
//        taskManager.getSubtaskById(4);
//        taskManager.getTaskById(1);
//        taskManager.getTaskById(1);
//
//        System.out.println(taskManager.getHistory());

        InMemoryTaskManager taskManager = new InMemoryTaskManager();



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
                2,
                Status.NEW
        );
        int secondTaskId = taskManager.createTask(secondTask);

        Epic epic = new Epic(
                "Выучить джаву",
                "Пройти курс от яндекса",
                -1,
                Status.NEW


        );
        int firstEpicId = taskManager.createEpic(epic);

        Subtask subtask = new Subtask(
                "Сдать ТЗ4",
                "Сделать тесты",
                3,
                Status.NEW,
                epic.getId()
        );
        int subtaskId = taskManager.createSubtask(subtask);

        Subtask subtask1 = new Subtask(
                "Сдать ТЗ5",
                "Сделать тесты",
                3,
                Status.NEW,
                epic.getId()
        );
        int subtask1Id = taskManager.createSubtask(subtask1);

        Subtask subtask2 = new Subtask(
                "Сдать ТЗ6",
                "Сделать тесты",
                3,
                Status.NEW,
                epic.getId()
        );
        int subtask2Id = taskManager.createSubtask(subtask2);

        Epic epic1 = new Epic(
                "Сдать все зачёты",
                "подготовиться к ним",
                -1,
                Status.NEW
        );
        int secondEpicId = taskManager.createEpic(epic1);




        taskManager.getSubtaskById(subtaskId);
        taskManager.getHistory();
        subtask.setStatus(Status.DONE);
        taskManager.getHistory();
//        history.getTasks();
//        taskManager.getTaskById(firstTaskId);
//        history.getHistory();
//        history.getTasks();
//        taskManager.getSubtaskById(subtask1Id);
//        history.getHistory();
//        history.getTasks();
//        taskManager.getTaskById(firstTaskId);
//        history.getHistory();
//        history.getTasks();
//        taskManager.getEpicById(firstEpicId);
//        history.getHistory();
//        history.getTasks();
//        taskManager.getEpicById(firstEpicId);
//        history.getHistory();
//        history.getTasks();
//




//        history.getHistory();
//        history.getTasks();
    }
}
