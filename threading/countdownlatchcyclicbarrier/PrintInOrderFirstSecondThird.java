package threading.countdownlatchcyclicbarrier;

import java.util.concurrent.CountDownLatch;

public class PrintInOrderFirstSecondThird {

    private final CountDownLatch l1 = new CountDownLatch(1);
    private final CountDownLatch l2 = new CountDownLatch(1);

    public void first() {
        System.out.print("first");
        l1.countDown();
    }

    public void second() throws InterruptedException {
        l1.await();
        System.out.print("second");
        l2.countDown();
    }

    public void third() throws InterruptedException {
        l2.await();
        System.out.print("third");
    }

    public static void main(String[] args) {

        PrintInOrderFirstSecondThird order =
                new PrintInOrderFirstSecondThird();

        Thread t1 = new Thread(() -> {
            order.first();
        });

        Thread t2 = new Thread(() -> {
            try {
                order.second();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread t3 = new Thread(() -> {
            try {
                order.third();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Start in ANY order
        t3.start();
        t2.start();
        t1.start();
    }
}
