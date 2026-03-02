package threading.abcabc;

import java.util.concurrent.Semaphore;

public class PrintABCSemaphore {
    Semaphore a = new Semaphore(1);
    Semaphore b = new Semaphore(0);
    Semaphore c = new Semaphore(0);
    int n;
    PrintABCSemaphore(int n){
        this.n = n;
    }
    public void printA() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            a.acquire();
            System.out.print("A");
            b.release();
        }
    }

    public void printB() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            b.acquire();
            System.out.print("B");
            c.release();
        }
    }

    public void printC() throws InterruptedException {
        for (int i = 0; i < n; i++) {
            c.acquire();
            System.out.print("C");
            a.release();
        }
    }

    public static void main(String[] args) {

        PrintABCSemaphore abc = new PrintABCSemaphore(5);

        new Thread(() -> {
            try { abc.printA(); } catch (InterruptedException ignored) {}
        }).start();

        new Thread(() -> {
            try { abc.printB(); } catch (InterruptedException ignored) {}
        }).start();

        new Thread(() -> {
            try { abc.printC(); } catch (InterruptedException ignored) {}
        }).start();
    }
}
