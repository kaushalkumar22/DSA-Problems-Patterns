package threading.foobar;

import java.util.concurrent.Semaphore;

public class FooBarSemaphore {
    private final int n;
    FooBarSemaphore(int n){
        this.n = n;
    }
    Semaphore a = new Semaphore(1);
    Semaphore b = new Semaphore(0);
    private void printFoo() throws InterruptedException {
        for (int i = 0 ;i<n;i++) {
            a.acquire();
            System.out.print("Foo");
            b.release();
        }
    }
    private void printBar() throws InterruptedException {
        for (int i = 0 ;i<n;i++) {
            b.acquire();
            System.out.print("Bar");
            a.release();
        }
    }
    public static void main(String[] args) {
        FooBarSemaphore fb = new FooBarSemaphore(5);
        new Thread(()->{
            try {
                fb.printFoo();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                fb.printBar();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
