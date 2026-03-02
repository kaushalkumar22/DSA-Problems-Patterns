package code.arrays;

public class SmallerNumbersThanCurrent {
    public static void main(String[] args) {

    }
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] freq = new int[101];
        for(int num :nums){
            freq[num]++;
        }
        int n = nums.length;
        int[]  map = new int[101];
        int count = 0 ;
        for(int i = 0 ;i<101 ;i++){
            int num = freq[i];
            if(num>0){
                map[i] = count;
                count += num;
            }
        }
        int[] res = new int[n];
        for(int i = 0 ;i<n ;i++){
            res[i] = map[nums[i]];
        }
        return res;
    }
}