package designpattern.iterator;

import java.util.ArrayList;
import java.util.List;

interface MyIterator<T>{
    boolean hasNext();
    T next();
}
class ListIterator<T> implements MyIterator<T>{

    List<T> list;
    int position;

    public ListIterator(List<T> list) {
        this.list = list;
        position=0;
    }

    @Override
    public boolean hasNext() {
        return position < list.size();
    }

    @Override
    public T next() {
        if(hasNext()) {
            return list.get(position++);
        }
        throw new IllegalStateException("No more elements");
    }
}
interface MyAggregator<T>{
    MyIterator<T> createIterator();
}
class MyList<T> implements MyAggregator<T>{

    List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }
public void add(T item){
        list.add(item);
}
    @Override
    public MyIterator<T> createIterator() {
        return new ListIterator<>(list);
    }
}
public class IteratorDemo {
    public static void main(String[] args) {
        MyList<Integer> myList = new MyList<>();
        myList.add(1);
        myList.add(2);
        myList.add(3);

        MyIterator<Integer> iterator = myList.createIterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
