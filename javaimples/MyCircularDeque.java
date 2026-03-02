package javaimples;

class MyCircularDeque {

    private final int[] arr;
    private final int capacity;
    private int front;
    private int rear;
    private int size;

    public MyCircularDeque(int k) {
        capacity = k;
        arr = new int[k];
        front = 0;
        rear = -1;
        size = 0;
    }

    // Insert at front
    public boolean insertFront(int value) {
        if (isFull()) return false;

        front = (front - 1 + capacity) % capacity;
        arr[front] = value;
        size++;
        if (size == 1) rear = front;
        return true;
    }

    // Insert at rear
    public boolean insertLast(int value) {
        if (isFull()) return false;

        rear = (rear + 1) % capacity;
        arr[rear] = value;
        size++;
        if (size == 1) front = rear;
        return true;
    }

    // Delete front
    public boolean deleteFront() {
        if (isEmpty()) return false;

        front = (front + 1) % capacity;
        size--;
        return true;
    }

    // Delete rear
    public boolean deleteLast() {
        if (isEmpty()) return false;

        rear = (rear - 1 + capacity) % capacity;
        size--;
        return true;
    }

    public int getFront() {
        return isEmpty() ? -1 : arr[front];
    }

    public int getRear() {
        return isEmpty() ? -1 : arr[rear];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }
}
