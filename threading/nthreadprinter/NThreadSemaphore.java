package threading.nthreadprinter;

import java.util.concurrent.Semaphore;

public class NThreadSemaphore {

    private final int n;
    private final int limit;
    private int number = 1;
    private final Semaphore[] semaphores;

    public NThreadSemaphore(int n, int limit) {
        this.n = n;
        this.limit = limit;
        semaphores = new Semaphore[n];

        for (int i = 0; i < n; i++) {
            semaphores[i] = new Semaphore(0);
        }
        semaphores[0].release(); // Thread-0 starts
    }

    public void print(int threadId) throws InterruptedException {
        while (true) {
            semaphores[threadId].acquire();

            if (number > limit) {
                // wake next thread so it can exit
                semaphores[(threadId + 1) % n].release();
                return;
            }

            System.out.println("Thread-" + (threadId + 1) + " Sequence: " + number);
            number++;

            semaphores[(threadId + 1) % n].release();
        }
    }

    public static void main(String[] args) {

        int n = 5;
        int limit = 20;
        NThreadSemaphore printer = new NThreadSemaphore(n, limit);

        for (int i = 0; i < n; i++) {
            int threadId = i;
            new Thread(() -> {
                try {
                    printer.print(threadId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
