package threading.producerconsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerBlockingQueue {
    BlockingQueue<Integer> que ;
    ProducerConsumerBlockingQueue(int size){
        que = new ArrayBlockingQueue<>(size);
    }

    public void produce(int val) throws InterruptedException {
        que.put(val);
        System.out.println("Produce: "+val);
    }

    public void consume() throws InterruptedException {
        var val = que.take();
        System.out.println("Consumed: "+val);
    }

    public static void main(String[] args) {
        ProducerConsumerBlockingQueue pc = new ProducerConsumerBlockingQueue(5);
        new Thread(()->{
            try{
                for (int i = 0 ;i<20;i++){
                    pc.produce(i);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(()->{
            try{
                for (int i = 0 ;i<20;i++){
                    pc.consume();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
