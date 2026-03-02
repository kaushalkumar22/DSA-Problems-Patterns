package designpattern.observer;

import java.util.ArrayList;
import java.util.List;

interface Subject{
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}

interface Observer{
    void update(String event);
}

class ConcreteSubject implements Subject{

    List<Observer> observers = new ArrayList<>();
    String event = "New Event arrived ";
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers){
            observer.update(event);
        }
    }
    public void onChangeEvent(String event){
        this.event = event;
        notifyObserver();
    }
}
class ConcreteObserver implements Observer{

    @Override
    public void update(String event) {
        System.out.println("New Event: "+ event);
    }
}
public class Demo {

    public static void main(String[] args) {
        Observer obs = new ConcreteObserver();
        Observer obs1 = new ConcreteObserver();
        ConcreteSubject subject = new ConcreteSubject() ;
        subject.addObserver(obs);
        subject.addObserver(obs1);
        subject.onChangeEvent("XYZ");
    }
}
