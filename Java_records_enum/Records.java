package Java_records_enum;

import java.util.ArrayList;
import java.util.List;

public class Records {
    public static void main(String[] args) {
        Person p = new Person("John", 30);
        System.out.println(p.age()); // accessor
        Dog d = new Dog(new ArrayList<>());
        d.update();
        System.out.println(d.names().get(0)); // accessor
        Point point = new Point(10,15);
        point.distance();
        point.draw();
    }
}
record Person(String name, int age) {
    //int z; Instance field is not allowed in record
    public Person {// use only for validation
        if (age < 0)
            throw new IllegalArgumentException();
    }
    void update(){
        //age++//Cannot assign a value to final variable 'age'
    }
}
record Dog(List<String> names){
    void update(){
        names.add("TUFFY");
    }
}
interface Drawable {
    void draw();
}
record Point(int x, int y) implements Drawable {
    public void draw() {
        System.out.println("Drawing");
    }
    double distance() {
        return Math.sqrt(x * x + y * y);
    }
}

// Records are final so we cannot inherit it can implements interface can create constructor , also fuction
//cannot create instance variable , can have constructo

