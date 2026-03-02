package code.arrays;

import code.stacks.EvaluateReversePolishNotation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class SortArrayByIncreasingFrequency {
    public int[] frequencySort(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> freq = new HashMap<>();
        int[][] bucket = new int[n][2];
        // Count frequencies
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }


        for (int i = 0 ;i<n;i++) {
            bucket[i][0] = nums[i];
            bucket[i][1] = freq.get(nums[i]);
        }
        Arrays.sort(bucket,
                (a, b) -> {
                    int fa = freq.get(a[0]);
                    int fb = freq.get(b[0]);
                    if (fa != fb) return fa - fb;      // frequency ascending
                    return b[1] - a[1];                      // value descending
                }
        );

        for (int i = 0 ;i<n;i++) {
           nums[i] = bucket[i][0];
        }
        return nums;
    }
}
