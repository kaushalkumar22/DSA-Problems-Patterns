package code.aa_practice;

import java.util.*;

public class Test1 {
    public static void main(String[] args) {
        Test1 freqStack= new Test1();
        freqStack.push(5); // The stack is [5]
        freqStack.push(7); // The stack is [5,7]
        freqStack.push(5); // The stack is [5,7,5]
        freqStack.push(7); // The stack is [5,7,5,7]
        freqStack.push(4); // The stack is [5,7,5,7,4]
        freqStack.push(5); // The stack is [5,7,5,7,4,5]
        freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].
        freqStack.pop();   // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].
        freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,4].
        freqStack.pop();   // return 4, as 4, 5 and 7 is the most frequent, but 4 is closest to the top. The stack becomes [5,7].
    }

    Map<Integer , Integer> map = new HashMap<>();
    List<Stack<Integer>> liStack = new ArrayList<>();
   private void push(int val){
        map.merge(val ,1, Integer::sum);
        int index = map.get(val);

        if(liStack.isEmpty() || liStack.size()<index){
            liStack.add(new Stack<>());
        }
        liStack.get(index-1).push(val);
    }
    //O(1)
    private int pop(){
       if(liStack.isEmpty()) return -1;
      int size = liStack.size();
      int val = liStack.get(size-1).pop();
      if(liStack.get(size-1).isEmpty()){
          liStack.remove(size-1);
      }
      map.put(val ,map.get(val)-1);
      System.out.println(val);
       return val ;
    }
}

/**
 *
 *

 Maximum frequency stack.

 Let's implement a stack which contains methods push(), pop() of a stack
 and perform operations as below example:

 freqStack.push(5); // The stack is [5]
 freqStack.push(7); // The stack is [5,7]
 freqStack.push(5); // The stack is [5,7,5]
 freqStack.push(7); // The stack is [5,7,5,7]
 freqStack.push(4); // The stack is [5,7,5,7,4]
 freqStack.push(5); // The stack is [5,7,5,7,4,5]
 freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,5,7,4].
 freqStack.pop();   // return 7, as 5 and 7 is the most frequent, but 7 is closest to the top. The stack becomes [5,7,5,4].
 freqStack.pop();   // return 5, as 5 is the most frequent. The stack becomes [5,7,4].
 freqStack.pop();   // return 4, as 4, 5 and 7 is the most frequent, but 4 is closest to the top. The stack becomes [5,7].

 **/
