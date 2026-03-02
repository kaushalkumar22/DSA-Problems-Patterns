package threading.countdownlatchcyclicbarrier;
import java.util.concurrent.CountDownLatch;

public class NSequentialTasks {

    public static void main(String[] args) {

        int N = 5; // number of tasks
        CountDownLatch[] latches = new CountDownLatch[N - 1];

        // Initialize N-1 latches
        for (int i = 0; i < N - 1; i++) {
            latches[i] = new CountDownLatch(1);
        }

        for (int i = 0; i < N; i++) {
            final int taskId = i;

            new Thread(() -> {
                try {
                    // Wait for previous task (except first)
                    if (taskId > 0) {
                        latches[taskId - 1].await();
                    }

                    // Execute task
                    System.out.println("Task " + (taskId + 1) + " executed");

                    // Signal next task (except last)
                    if (taskId < N - 1) {
                        latches[taskId].countDown();
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}

