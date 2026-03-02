package threading.abcabc;

public class ABCABCWaitNotify {

    private int state = 0; // 0=A, 1=B, 2=C
    private final int times;

    ABCABCWaitNotify(int times) {
        this.times = times;
    }

    public synchronized void printA() throws InterruptedException {
        for (int i = 0; i < times; i++) {
            while (state % 3 != 0) {
                wait();
            }
            System.out.print("A");
            state++;
            notifyAll();
        }
    }

    public synchronized void printB() throws InterruptedException {
        for (int i = 0; i < times; i++) {
            while (state % 3 != 1) {
                wait();
            }
            System.out.print("B");
            state++;
            notifyAll();
        }
    }

    public synchronized void printC() throws InterruptedException {
        for (int i = 0; i < times; i++) {
            while (state % 3 != 2) {
                wait();
            }
            System.out.print("C");
            state++;
            notifyAll();
        }
    }

    public static void main(String[] args) {

        ABCABCWaitNotify abc = new ABCABCWaitNotify(5);

        new Thread(() -> {
            try { abc.printA(); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try { abc.printB(); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();

        new Thread(() -> {
            try { abc.printC(); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}
