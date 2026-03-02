package code.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DeleteAndEarn {
    public static void main(String[] args) {
         // int[] nums = {3, 4, 2};
          int[] nums = {2,2,3,3,3,4};
        System.out.println( deleteAndEarn(nums));
    }

    public static int deleteAndEarn(int[] nums) {

        int max  = Arrays.stream(nums).max().orElse(0);
        int[] points = new int[max + 1];
        Arrays.stream(nums).forEach(num->points[num] += num); // accumulate total points for each number
        int[] dp = new int[max + 1];
        dp[0] = 0;
        dp[1] = points[1];

        for (int i = 2; i <= max; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + points[i]);
        }

        return dp[max];
    }
}

