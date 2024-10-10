# Introduction

Often, problems ask for either the count or the length (minimum/maximum) of a subarray based on certain mathematical conditions, such as subarray sum, divisibility, modulo, vowel counting, etc. Initially, you may attempt to solve these problems using the Sliding Window technique but soon realize it's not feasible due to negative elements or other conditions. For these kinds of problems, we have the **Prefix Sum approach**.

This guide is divided into four sections:
- **Section 1:** Standard prefix sum problems.
- **Section 2:** Problems on divisibility and modulo.
- **Section 3:** Prefix sum and XOR.
- **Section 4:** 2D Prefix Sum Problems.

Please drop comments if any interesting problems are missed. I have kept the code (in C++) collapsed, and I encourage you to solve the problems yourself first. If you get stuck, expand to see the code. Most of these problems have a time complexity of O(n) and a space complexity of O(n) due to maps.

## Basic Idea
Let's understand this with an introductory problem: [560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/).

The question asks for the count of subarrays whose sum equals K (given as an input along with the array). Let `psum_i` be the current running subarray sum. If we find in the past a `psum_j` such that:
```
psum_i - psum_j = K
```
It means the array from [j...i] has a sum of K, which is what we are looking for! After accumulating `psum_i`, we try to find the existence of `psum_j` in our map using the formula:
```
psum_j = psum_i - K
```

### Template:
1. Maintain a `prefix_sum = 0` (this is our `psum_i`).
2. Set up an unordered map (or vector if the range is known).
3. Use a variable to store the answer = 0.
4. Initialize the map with `m[0]`:
   - If the question asks for a **count**, set `m[0] = 1`. This is because, if `psum_i - K = 0`, the entire subarray is an answer.
   - If looking for **length**, set `m[0] = -1`. If at index `i`, `psum_i - K = 0`, the length is `i + 1`.
5. Iterate through the array and accumulate `psum`.
6. Check if `psum - K` exists in the map. If it does, add to the answer.
7. Increment (count) or store the index (length) of the current `psum`.

### Easy Problem: [1480. Running Sum of 1d Array](https://leetcode.com/problems/running-sum-of-1d-array/)

## Section 1

### Warmup Problems:
1. [303. Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/)
2. [560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)
3. [1310. XOR Queries of a Subarray](https://leetcode.com/problems/xor-queries-of-a-subarray/)

Just follow the above template step by step.

```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> countMap = new HashMap<>();
    int pSum = 0;
    int ans = 0;
    countMap.put(0, 1);
    for (int num : nums) {
        pSum += num;
        if (countMap.containsKey(pSum - k)) {
            ans += countMap.get(pSum - k);
        }
        countMap.put(pSum, countMap.getOrDefault(pSum, 0) + 1);
    }
    return ans;
}
```

### Problem: [2559. Count Vowel Strings in Ranges](https://leetcode.com/problems/count-vowel-strings-in-ranges/)

In this problem, each string index is marked as `1` if a condition is satisfied, then we use prefix sum to answer queries in O(1) time.

```java
class Solution {
    public int[] vowelStrings(String[] words, int[][] queries) {
        int n = words.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            int l = words[i].length() - 1;
            if ("aeiou".indexOf(words[i].charAt(0)) >= 0 && "aeiou".indexOf(words[i].charAt(l)) >= 0) {
                arr[i] = 1;
            }
        }
        int[] psum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            psum[i + 1] = arr[i] + psum[i];
        }
        int[] ans = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            ans[i] = psum[queries[i][1] + 1] - psum[queries[i][0]];
        }
        return ans;
    }
}
```

### Problem: [325. Maximum Size Subarray Sum Equals k](https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/)

This problem is similar to the previous one but asks for the **longest** subarray size.

```java
public int maxSubArrayLen(int[] nums, int k) {
    Map<Integer, Integer> lenMap = new HashMap<>();
    int pSum = 0;
    int ans = 0;
    lenMap.put(0, -1);
    for (int i = 0; i < nums.length; i++) {
        pSum += nums[i];
        if (lenMap.containsKey(pSum - k)) {
            ans = Math.max(ans, i - lenMap.get(pSum - k));
        }
        lenMap.putIfAbsent(pSum, i);
    }
    return ans;
}
```

### Problem: [930. Binary Subarrays With Sum](https://leetcode.com/problems/binary-subarrays-with-sum/)

This is exactly the same as the 560 problem but with binary arrays.

### Problem: [2364. Count Number of Bad Pairs](https://leetcode.com/problems/count-number-of-bad-pairs/)

```java
public long countBadPairs(int[] nums) {
    long n = nums.length;
    long ans = (n * (n - 1)) / 2;
    Map<Integer, Long> map = new HashMap<>();
    for (int i = 0; i < n; i++) {
        int diff = i - nums[i];
        ans -= map.getOrDefault(diff, 0L);
        map.put(diff, map.getOrDefault(diff, 0L) + 1);
    }
    return ans;
}
```

### Problem: [1658. Minimum Operations to Reduce X to Zero](https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/)

```java
public int minOperations(int[] nums, int x) {
    int target = -x;
    for (int num : nums) {
        target += num;
    }
    if (target == 0) return nums.length;

    int ans = Integer.MIN_VALUE;
    int left = 0, right = 0;
    long sum = 0;
    for (; right < nums.length; right++) {
        sum += nums[right];
        while (sum >= target) {
            if (sum == target) ans = Math.max(ans, right - left + 1);
            sum -= nums[left++];
        }
    }
    return ans == Integer.MIN_VALUE ? -1 : nums.length - ans;
}
```

## Section 2: Prefix Sum & Division/Modulo

### Problem: [974. Subarray Sums Divisible by K](https://leetcode.com/problems/subarray-sums-divisible-by-k/)

This problem uses the modulo approach discussed earlier.

```java
public int subarraysDivByK(int[] nums, int k) {
    Map<Integer, Integer> countMap = new HashMap<>();
    int pSum = 0, ans = 0;
    countMap.put(0, 1);
    for (int num : nums) {
        pSum = (pSum + num % k + k) % k;
        ans += countMap.getOrDefault(pSum, 0);
        countMap.put(pSum, countMap.getOrDefault(pSum, 0) + 1);
    }
    return ans;
}
```
