package threading.foobar;
public class FooBarWaitNotify {
    private final int n;
    private boolean fooTurn = true;

    public FooBarWaitNotify(int n) {
        this.n = n;
    }

    public synchronized void foo() throws InterruptedException {

        for(int i = 0 ;i<n;i++){
            while(!fooTurn){
                wait();
            }
            System.out.println("Foo");
            fooTurn= false;
            notifyAll();
        }

    }

    public synchronized void bar() throws InterruptedException {
        for(int i = 0 ;i<n;i++){
            while(fooTurn){
                wait();
            }
            System.out.println("Bar");
            fooTurn= true;
            notifyAll();
        }
    }

    public static void main(String[] args) {
        FooBarWaitNotify fb = new FooBarWaitNotify(5);
        new Thread(()->{
            try {
                fb.foo();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try {
                fb.bar();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}