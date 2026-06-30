package datastructures;

public class Queue<T> {
    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;
    private int capacity = -1; // Negative means unbounded

    public Queue() {
    }

    public Queue(int capacity) {
        this.capacity = capacity;
    }

    public void enqueue(T data) {
        if (capacity > 0 && size == capacity) {
            dequeue(); // Discard oldest
        }

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = head.data;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    public DynamicArray<T> toArray() {
        DynamicArray<T> arr = new DynamicArray<>();
        Node<T> current = head;
        while (current != null) {
            arr.add(current.data);
            current = current.next;
        }
        return arr;
    }
}
