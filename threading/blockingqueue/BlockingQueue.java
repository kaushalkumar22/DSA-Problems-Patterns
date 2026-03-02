package threading.blockingqueue;


import java.util.LinkedList;
import java.util.Queue;
//A blocking queue blocks producers when the queue is full and blocks consumers when the queue is empty.
class BlockingQueue {
    private final Queue<Integer> queue = new LinkedList<>();
    private final int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(int item) throws InterruptedException {
        while (queue.size() == capacity){
            wait(); // wait if full
        }
        queue.add(item);
        notifyAll();
    }

    public synchronized int take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // wait if empty
        }
        int item = queue.poll();
        notifyAll();
        return item;
    }
    public static void main(String[] args) {

        System.out.println("CountDownLatchCyclicBarrier".toLowerCase());
        BlockingQueue queue = new BlockingQueue(3);

        // Producer
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    queue.put(i);
                    System.out.println("Produced: " + i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException ignored) {}
        }).start();

        //consumer
        new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    int item = queue.take();
                    System.out.println("Consumed: " + item);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ignored) {}
        }).start();

    }
}
