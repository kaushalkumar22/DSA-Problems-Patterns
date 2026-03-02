package threading.producerconsumer;

public class ProducerConsumerWaitNotify {

    private int buffer;
    private boolean isAvailable = false;
    private final int n;

    public ProducerConsumerWaitNotify(int n) {
        this.n = n;
    }

    public synchronized void produce() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            while (isAvailable) {
                wait();
            }
            buffer = i;
            System.out.println("Produced: " + buffer);
            isAvailable = true;
            notifyAll();
        }
    }

    public synchronized void consume() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            while (!isAvailable) {
                wait();
            }
            System.out.println("Consumed: " + buffer);
            isAvailable = false;
            notifyAll();
        }
    }

    public static void main(String[] args) {

        ProducerConsumerWaitNotify pc =
                new ProducerConsumerWaitNotify(10);

        new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
