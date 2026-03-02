package code.dynamicprogramming;

public class HouseRobberII {
    public static void main(String[] args) {
        int[] nums = {2,3,2};
        // Output: 4
       System.out.println(Math.max( rob( nums),robII( nums)));
    }
    public static int rob(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[1] = nums[0];
        dp[2] = Math.max(nums[0],nums[1]);
        for(int i = 3;i<n;i++){
            dp[i] = Math.max(dp[i-2]+nums[i],dp[i-1]);
        }
        System.out.println(dp[n-1]);
        return dp[n-1];
    }
    public static int robII(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);
        for(int i = 2;i<n-1;i++){
            dp[i] = Math.max(dp[i-2]+nums[i],dp[i-1]);
        }
        System.out.println(dp[n-2]);
        return dp[n-2];
    }
}
