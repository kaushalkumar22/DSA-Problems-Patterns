package threading.oddeven;

public class OddEvenWaitNotify {
    private final int n;
    private int start = 1;
    public OddEvenWaitNotify(int n) {
        this.n = n;
    }

    public synchronized void printOdd() throws InterruptedException {

        while(start<=n){
            while(start%2==0){
                wait();
            }
            System.out.println("Odd: "+start);
            start++;
            notifyAll();
        }

    }

    public synchronized void printEven() throws InterruptedException {
        while(start<=n){
            while(start%2!=0){
                wait();
            }
            System.out.println("Even: "+start);
            start++;
            notifyAll();
        }
    }

    public static void main(String[] args) {
        OddEvenWaitNotify oe = new OddEvenWaitNotify(10);
        new Thread(()->{
            try {
                oe.printOdd();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                oe.printEven();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
