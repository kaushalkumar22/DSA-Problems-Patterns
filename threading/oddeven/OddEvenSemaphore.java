package threading.oddeven;

import java.util.concurrent.Semaphore;

public class OddEvenSemaphore {

    private final int n;
    private int num = 1;

    private final Semaphore oddSem = new Semaphore(1);
    private final Semaphore evenSem = new Semaphore(0);

    OddEvenSemaphore(int n) {
        this.n = n;
    }

    public void printOdd() throws InterruptedException {
        while (num <= n) {
            oddSem.acquire();
            if (num <= n) {
                System.out.println("Odd: " + num);
                num++;
            }
            evenSem.release();
        }
    }

    public void printEven() throws InterruptedException {
        while (num <= n) {
            evenSem.acquire();
            if (num <= n) {
                System.out.println("Even: " + num);
                num++;
            }
            oddSem.release();
        }
    }

    public static void main(String[] args) {

        OddEvenSemaphore oe = new OddEvenSemaphore(10);

        new Thread(() -> {
            try {
                oe.printOdd();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try {
                oe.printEven();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
