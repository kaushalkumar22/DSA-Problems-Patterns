package code.dynamicprogramming;

public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] nums = {5,5,5,5};
        int[] seq = new int[nums.length];

        seq[0] = nums[0];
        int index = 1 ;
        for(int i = 1;i<nums.length;i++){
            if(seq[index-1]<nums[i]){
                seq[index++] = nums[i];
            }else{
                int pivot = binarySearch(seq, index,nums[i]);
                seq[pivot] = nums[i];
            }
        }
//        System.out.println(index);
    }
    private static int binarySearch(int[] seq, int index, int num) {
        int low = 0 ,high = index;
        while(low<high){
            int mid = (low+high)/2;
            if(seq[mid]==num){
                return mid;
            }else if(seq[mid]<num){
                low = mid+1;
            }else{
                high = mid;
            }
        }
        return low;
    }
}
