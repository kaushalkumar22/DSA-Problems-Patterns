package code.aa_practice;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestJ {

    public static void main(String[] args) {
        new TestY().display(100.0);
    }
}
class TestX{

    public void display(int x)  {
        System.out.println("X");
    }
    public void display(double y) throws RuntimeException {
        System.out.println("Y");
    }
}
class TestY extends TestX {
    @Override
    public void display(double y) throws NullPointerException{
        System.out.println("Z");
    }
}
