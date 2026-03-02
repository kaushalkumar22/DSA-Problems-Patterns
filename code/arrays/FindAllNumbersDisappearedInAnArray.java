package code.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindAllNumbersDisappearedInAnArray {
    public static void main(String[] args) {
        int[] nums = {4,3,2,7,8,2,3,1};
        System.out.println(findDisappearedNumbers( nums));
    }
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();
        for(int num : nums){
            int index = Math.abs(num) -1;
            if(nums[index]>0){
                nums[index] = -nums[index];
            }
        }
        for(int i = 0 ;i<nums.length ;i++){
            if(nums[i]>0){
                res.add(i+1);
            }
        }
        return res;
    }
}
