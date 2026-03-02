package threading.nthreadprinter;

public class NThreadWaitNotify {

    private int number = 1;
    private final int limit;
    private final int totalThreads;

    public NThreadWaitNotify(int limit, int totalThreads) {
        this.limit = limit;
        this.totalThreads = totalThreads;
    }

    public synchronized void print(int threadId) throws InterruptedException {
        while (true) {
            while (number <= limit &&
                    (number - 1) % totalThreads != threadId) {
                wait();
            }

            // termination check AFTER waking up
            if (number > limit) {
                notifyAll(); // wake others so they can exit
                return;
            }

            System.out.println("Thread-" + threadId + " : " + number);
            number++;
            notifyAll();
        }
    }
    public static void main(String[] args) {

        int n = 10;
        NThreadWaitNotify printer = new NThreadWaitNotify(10, n);

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
