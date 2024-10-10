# Prefix Sum

---

## Introduction

Many problems require finding the count or length (minimum/maximum) of a subarray based on specific conditions, such as subarray sum, divisibility, modulo, or vowel counting. Initially, you might try to solve these using the Sliding Window method, but sometimes, this won't work—especially when there are negative numbers or other conditions. In these cases, the **Prefix Sum** technique is useful.

This guide is divided into four parts:

1. Standard prefix sum problems.
2. Problems involving divisibility and modulo.
3. Prefix sum with XOR.
4. 2D Prefix Sum problems.

## Basic Idea

Let’s start with an example:  
[560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)

The problem asks you to find how many subarrays have a sum equal to K. Suppose `psum_i` is the running total of the current subarray. If there was a previous sum `psum_j` such that:

`psum_i - psum_j = K`

This means that the subarray from index `j` to `i` has a sum of K, which is exactly what we’re looking for. After calculating `psum_i`, we check if `psum_j` exists in our map, where:

`psum_j = psum_i - K`

## Template:

- Start with a `prefix_sum = 0` (this is `psum_i`).
- Use an unordered map (or vector if the range is known).
- Have a variable to store the result (e.g., count or length).
- Initialize the map with `m[0]`:
  - If the task is to count subarrays, set `m[0] = 1`. This is because if `psum_i - K = 0`, the entire subarray is a solution.
  - If the task is to find a subarray's length, set `m[0] = -1`. If at index `i`, `psum_i - K = 0`, the length is `i + 1`.
- Loop through the array and update the `prefix_sum`.
- Check if `prefix_sum - K` exists in the map. If it does, update your result.
- Either increase the count or store the index (for length) of the current `prefix_sum`.

- [1480. Running Sum of 1d Array](https://leetcode.com/problems/running-sum-of-1d-array/)

---

## Section 1 : Standard prefix sum problems.

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

[2559. Count Vowel Strings in Ranges](https://leetcode.com/problems/count-vowel-strings-in-ranges/)

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

[325. Maximum Size Subarray Sum Equals k](https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/)

Same problem as above but instead of asking count, here we need to find the longest subarray size.  
So we have initialize lenMap[0] = -1  
Secondly if the pSum doexnt exist in hasmap only then update the index, this is because we haev to find the longest length, so the earlier index are better.  

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

[930. Binary Subarrays With Sum](https://leetcode.com/problems/binary-subarrays-with-sum/)

This is exactly the same as the 560 problem but with binary arrays.

[2364. Count Number of Bad Pairs](https://leetcode.com/problems/count-number-of-bad-pairs/)

Total pairs would be n * (n-1) / 2
And remove tose pairs which dont satisfy the rule i- nums[i] == j - nums[j]

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

[1658. Minimum Operations to Reduce X to Zero](https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/)   

This question ask for removal from left or right, that means , all other leftover element would be a subarray.  
We have to minimum operation if leftover element subarray would be as large as possiible.  
Lets see with an example to understand better.  

[1,1,4,2,3], x = 5  
total sum of element is 10 , and we need to reduce x to 0, so that means leftover subarray sum would be total - x  
So try to find a largest subarray where this holds true and if not return -1.  

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

The next few problems will involve some math and divisibility rules.

For problems where we need to find subarrays divisible by a number `K`, the key idea is to check if a subarray sum between two indices `[j, i]` satisfies the condition:

`psum_i - psum_j = q * K`  
(where `q` is some integer)

If we take modulo `K` on both sides, we get:

`(psum_i % K) - (psum_j % K) = 0`

This means that while iterating through the array, we need to track `psum_i % K`. We’ll use a map to keep count of how often each remainder occurs, updating it like this:

`countMap[psum % K]++`

#### Handling Negative Remainders:

According to the Euclidean division algorithm, for two integers `a` and `b`, where `b ≠ 0`, the equation is:

`a = bq + r`, where `0 ≤ r < |b|`

The key point here is that the remainder `r` should always be positive. However, if either `a` or `b` is negative, the remainder can be negative. For example, in C++:

`-5 % 2 = -1`

But by the Euclidean algorithm, the remainder should be positive. So, we adjust it like this:

`r = ((r + 2) % 2)`

In the case of prefix sum problems, we can fix this by adjusting the remainder:

`psum_j = ((psum + K) % K + K) % K`

This ensures we always have a positive remainder. As we iterate through the array, we add each element to the prefix sum and adjust for negative remainders by adding `K` and then taking modulo `K` again.

[974. Subarray Sums Divisible by K](https://leetcode.com/problems/subarray-sums-divisible-by-k/)

 As discussed , at each iteration look for (psum +i )%K and to fix negative remainded add +K and again take modulo.

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
