package code.arrays;

import java.util.ArrayList;
import java.util.List;

public class FindAllDuplicatesInAnArray {
    public static void main(String[] args) {
        //Input:
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(findDuplicates( nums));
        //Output: [2,3]
    }
    //4,-3,-2,-7,8,2,-3,-1
    public static List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for(int num : nums){
            int index = Math.abs(num) -1;
            if(nums[index]<0){
                res.add(index+1);
            }else {
                nums[index] = -nums[index];
            }
        }
        return res;
    }
}
