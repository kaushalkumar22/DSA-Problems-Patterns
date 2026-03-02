package javaimples;

public class CircularQueueArray{

    private final int[] arr;
    private final int k;
    private int front = 0;
    private int rear = -1;
    int count =0;

    public CircularQueueArray(int k) {
        this.k = k;
        arr = new int[k];
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        rear = (rear + 1) % k;
        arr[rear] = value;
        count++;
        return true;
    }

    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        front = (front + 1) % k;
        count--;
        return true;
    }

    public int Front() {
        return count==0?-1: arr[front];
    }

    public int Rear() {
        return count==0?-1: arr[rear];
    }

    public boolean isEmpty() {
        return count==0;
    }

    public boolean isFull() {
        return count==k;
    }

    public static void main(String[] args) {
        CircularQueueArray myCircularQueue = new CircularQueueArray(3);
        myCircularQueue.enQueue(1); // return True
        myCircularQueue.enQueue(2); // return True
        myCircularQueue.enQueue(3); // return True
        myCircularQueue.enQueue(4); // return False
        System.out.println( myCircularQueue.Rear());// return 3
        System.out.println( myCircularQueue.isFull()); // return True

          // return True
        System.out.println(myCircularQueue.deQueue());

        myCircularQueue.enQueue(4); // return True
       // return 4
        System.out.println( myCircularQueue.Rear());
    }
}
