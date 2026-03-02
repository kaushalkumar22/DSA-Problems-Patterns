package threading.countdownlatchcyclicbarrier;

import java.util.concurrent.CyclicBarrier;
class Worker implements Runnable {
    private final CyclicBarrier barrier;
    Worker(CyclicBarrier barrier) {
        this.barrier = barrier;
    }
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " working");
            barrier.await(); // wait for others
            System.out.println(Thread.currentThread().getName() + " continues");
        } catch (Exception ignored) {}
    }
}
public class PhaseSynchronization {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3,
                () -> System.out.println("All threads reached barrier")
        );
        new Thread(new Worker(barrier)).start();
        new Thread(new Worker(barrier)).start();
        new Thread(new Worker(barrier)).start();
    }
}
