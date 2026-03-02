package threading.producerconsumer;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ProducerConsumerSemaphore {

    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    private final Semaphore empty;
    private final Semaphore full;
    private final Semaphore mutex = new Semaphore(1);

    ProducerConsumerSemaphore(int capacity) {
        this.capacity = capacity;
        this.empty = new Semaphore(capacity);
        this.full = new Semaphore(0);
    }

    public void producer(int val) throws InterruptedException {
        empty.acquire();      // wait for empty slot
        mutex.acquire();      // enter critical section
        queue.add(val);
        System.out.println("Produced: " + val);
        mutex.release();
        full.release();       // signal item available
    }

    public void consumer() throws InterruptedException {
        full.acquire();       // wait for item
        mutex.acquire();
        int val = queue.poll();
        System.out.println("Consumed: " + val);
        mutex.release();
        empty.release();      // signal empty slot
    }


    public static void main(String[] args) {
        ProducerConsumerSemaphore fb = new ProducerConsumerSemaphore(5);
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    fb.producer(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    fb.consumer();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}

