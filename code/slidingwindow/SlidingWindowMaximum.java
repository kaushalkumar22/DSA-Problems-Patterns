package code.slidingwindow;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowMaximum {
    public static void main(String[] args) {
    }
    //Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int[] res = new int[n-k+1];
        Deque<Integer> dq = new ArrayDeque<>();
        for (int i = 0 ;i<n; i++){

           // 1. remove out of window indexes
            while(!dq.isEmpty() && dq.peekLast()<=i-k){
                dq.pollLast();
            }
            //2. first check if dq top is always have
            while(!dq.isEmpty() && nums[i]>nums[dq.peekFirst()]){
                dq.pollFirst();
            }
            dq.offerFirst(i);
            //first check if dq top is always have
            if(i>=k-1){
                res[i - k + 1] = nums[dq.peekFirst()];
            }
        }
        return res;
    }
}