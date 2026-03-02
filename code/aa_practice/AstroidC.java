package code.aa_practice;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class AstroidC {
    public static void main(String[] args) {

    }
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> st = new Stack<>();
        for(int num :asteroids){
            while(!st.isEmpty() && st.peek()>0){
                if(st.peek()>Math.abs(num)){
                    break;
                }
                st.pop();
            }
            st.push(num);
        }
        return st.stream().mapToInt(i->i).toArray();
    }
}
