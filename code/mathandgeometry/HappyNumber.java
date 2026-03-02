package code.mathandgeometry;

import java.util.HashSet;
import java.util.Set;

public class HappyNumber {
    public static void main(String[] args) {
        System.out.println(isHappy( 2));
    }
    public static boolean isHappy(int n) {
        int slow = n;
        int fast = nextNum(n);
        while(fast!=1 && slow!=fast){
          slow = nextNum(slow);
          fast = nextNum(nextNum(fast));
        }
        return fast==1;
    }
    public static boolean isHappy1(int n) {
        Set<Integer> set = new HashSet<>();
        while(n!=1 && !set.contains(n)){
            set.add(n);
            n = nextNum(n);
        }
        return n==1;
    }
    static int nextNum(int n){
        int next = 0 ;
        while(n!=0){
            int val = n%10;
            next += val*val;
            n=n/10;
        }
        return next;
    }
}
