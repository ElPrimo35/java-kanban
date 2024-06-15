package managers;

import models.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    Node<Task> head;
    Node<Task> tail;
    private final Map<Integer, Node<Task>> historyMap = new HashMap<>();


    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> currentNode = head;
        while (currentNode != null) {
            history.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return history;
    }

    @Override
    public void addTask(Task task) {
        if (task == null) return;

        if (historyMap.containsKey(task.getId())) {
            removeNode(historyMap.get(task.getId()));
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (historyMap.containsKey(id)) {
            removeNode(historyMap.get(id));
        }

    }


    public void linkLast(Task task) {
        if (head == null) {
            head = new Node<>(null, task, null);
            tail = head;
            historyMap.put(task.getId(), head);
        } else {
            Node<Task> node = tail;
            node.next = new Node<>(node, task, null);
            tail = node.next;
            historyMap.put(task.getId(), tail);
        }
    }


    private void removeNode(Node<Task> node) {
        if (node == null) return;

        if (node.equals(head)) {
            head = head.next;
            if (head != null) {
                head.prev = null;
            }
            return;
        }
        if (node.equals(tail)) {
            tail = tail.prev;
            tail.next = null;
            return;
        }

        if (node.equals(tail) && node.equals(head)) {
            tail = null;
            head = null;
        }


        Node<Task> prev = node.prev;
        Node<Task> next = node.next;

        prev.next = next;
        next.prev = prev;
    }
}
