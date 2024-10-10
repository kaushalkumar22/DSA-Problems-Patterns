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

[303. Range Sum Query - Immutable](https://leetcode.com/problems/range-sum-query-immutable/)
[560. Subarray Sum Equals K](https://leetcode.com/problems/subarray-sum-equals-k/)
[1310. XOR Queries of a Subarray](https://leetcode.com/problems/xor-queries-of-a-subarray/)

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
---

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
[1497. Check If Array Pairs Are Divisible by K](https://leetcode.com/problems/check-if-array-pairs-are-divisible-by-k/)

The goal of this problem is to determine if an array can be divided into pairs where each pair's sum is divisible by `K`. We use a map to keep track of the remainders when each element is divided by `K`.

```java
import java.util.HashMap;

public class Solution {
    public boolean canArrange(int[] arr, int k) {
        HashMap<Integer, Integer> remMap = new HashMap<>();
        
        for (int i : arr) {
            int r = (i % k + k) % k; // Ensure the remainder is positive
            int complement = (k - r) % k; // Find the complement remainder
            
            // Check if there's a complement available
            if (remMap.getOrDefault(complement, 0) > 0) {
                remMap.put(complement, remMap.get(complement) - 1); // Use a pair
            } else {
                remMap.put(r, remMap.getOrDefault(r, 0) + 1); // Add the current remainder
            }
        }
        
        // Check if all remainders have been paired
        int sum = 0;
        for (int count : remMap.values()) {
            sum += count;
        }
        
        return sum == 0;
    }
}
```

[1590. Make Sum Divisible by P](https://leetcode.com/problems/make-sum-divisible-by-p/)

Here, we are tasked with finding the smallest subarray that can be removed to make the sum of the remaining elements divisible by `P`. We first compute the total sum and its remainder when divided by `P`.

Here we are trying to find smallest subarray of size remainder i.e. k = totalSum % p, here totalSum is sum of all array element.  
psumi - psumj = k  
if we remove this smallest subarray of k that means entire subarray sum excluding this smallest subarray will be divisible of p.  
So we go and find the smallest subarray of size k  
Notice we haev to find Smallest Subarray so we overwite an existing sum , unlike 325.  

```java
import java.util.HashMap;

public class Solution {
    public int minSubarray(int[] nums, int p) {
        long totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        
        int k = (int)(totalSum % p);
        if (k == 0) return 0; // Already divisible
        
        HashMap<Integer, Integer> lenMap = new HashMap<>();
        lenMap.put(0, -1);
        
        int psum = 0, n = nums.length, ans = n;
        
        for (int i = 0; i < n; i++) {
            psum = (psum + nums[i]) % p;
            int target = (psum - k + p) % p; // Adjust for negative
            
            if (lenMap.containsKey(target)) {
                ans = Math.min(ans, i - lenMap.get(target)); // Update smallest length
            }
            
            lenMap.put(psum, i); // Update the current prefix sum index
        }
        
        return ans == n ? -1 : ans; // Return -1 if no valid subarray found
    }
}
```

3. [523. Continuous Subarray Sum](https://leetcode.com/problems/continuous-subarray-sum/)

This problem asks whether a subarray of size at least two exists whose sum is a multiple of `K`. We use a map to track the remainders of the cumulative sum.

```java
import java.util.HashMap;

public class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> m = new HashMap<>();
        int sum = 0;
        int n = nums.length;
        
        m.put(0, -1); // Handle case when the sum is exactly a multiple of k
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            sum %= k; // Get the remainder
            
            if (m.containsKey(sum)) {
                if (i - m.get(sum) > 1) { // Check for size >= 2
                    return true;
                }
            } else {
                m.put(sum, i); // Store the index of this remainder
            }
        }
        
        return false; // No valid subarray found
    }
}
```

[2575. Find the Divisibility Array of a String](https://leetcode.com/problems/find-the-divisibility-array-of-a-string/)

In this problem, we build an array that indicates whether the number formed by the first `i` digits of a string is divisible by `m`.  
We cant keep accumulating next number as it will go beyong range of even long long  
Check this  
ith number would be (psum10 + ith digit)%m  
i+1th digit is [(psum10 + ith digit)10 + i+1th digit]%m  
which will be [(psum10 + ith digit)%m ] *10 + i+1th digit  
that means we can do a modulo at ith iteration after calculating psum and that will keep psum range in check !  

```java
import java.util.Arrays;

public class Solution {
    public int[] divisibilityArray(String word, int m) {
        int n = word.length();
        int[] ans = new int[n];
        long psum = 0; // To avoid overflow
        
        for (int i = 0; i < n; i++) {
            psum = psum * 10 + (word.charAt(i) - '0');
            psum %= m; // Keep psum in range
            
            if (psum == 0) {
                ans[i] = 1; // Mark as divisible
            }
        }
        
        return ans;
    }
}
```

[2845. Count of Interesting Subarrays](https://leetcode.com/problems/count-of-interesting-subarrays/)

In this mixed problem, we need to count subarrays where the sum modulo a certain value equals `k`. We transform the input array and utilize a map for counting.

This is mix of couple of problem, first only modulo number considered , this is same as 930 & 1248, so input array can be first converted in binary array.  
Secondly insight is same divisibility rule,  
1.we add the ith element first  
2. do modulo  
3. then check psum -k we have seen in past or not  
4. To fix negative remainded we do +K and then modulo afterward again.  

```java
import java.util.HashMap;

public class Solution {
    public long countInterestingSubarrays(int[] nums, int modulo, int k) {
        int n = nums.length;

        // Step 1: Transform the array
        for (int i = 0; i < n; i++) {
            nums[i] = (nums[i] % modulo) == k ? 1 : 0; // Convert to binary
        }
        
        // Step 2: Count interesting subarrays
        long count = 0;
        HashMap<Long, Long> countMap = new HashMap<>();
        countMap.put(0L, 1L); // To handle subarrays starting from index 0
        long cnt = 0;
        
        for (int i = 0; i < n; i++) {
            cnt += nums[i]; // Accumulate count of 1's
            cnt %= modulo; // Keep cnt in range
            
            // Check if we have seen the required previous count
            long target = (cnt - k + modulo) % modulo;
            count += countMap.getOrDefault(target, 0L);
            
            countMap.put(cnt, countMap.getOrDefault(cnt, 0L) + 1); // Update the map
        }
        
        return count;
    }
}
```
---

## Section 3: Prefix Sum & XOR

**Identifying Problems:**
When you encounter problems asking about the **even or odd occurrence** of elements in a subarray (or substring), a useful technique is to use the **XOR** operation. The key properties of XOR that help us are:

1. **XOR of Same Numbers:** The XOR of a number with itself is zero (e.g., \( x \oplus x = 0 \)).
2. **XOR with Zero:** The XOR of a number with zero is the number itself (e.g., \( x \oplus 0 = x \)).
3. **Even and Odd Counts:** If a bit (in this context, it could represent a character's frequency) has an even number of occurrences, the final result for that bit is \( 0 \). If it has an odd number, the result is \( 1 \).

#### Sample Problems
Two examples of such problems are:
- [1442. Count Triplets That Can Form Two Arrays of Equal XOR](https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/)
- [1915. Number of Wonderful Substrings](https://leetcode.com/problems/number-of-wonderful-substrings/)

### Key Idea
For the problem, let's say we have an input string containing characters from 'a' to 'j'. We want to find substrings with at most **1 odd character**. 

**What does "at most 1 odd character" mean?**
- It means the substring can have:
  - **All even occurrences** of characters (i.e., all counts are even).
  - **One odd occurrence** of one character (i.e., counts are odd for just one character).

### Approach Using Sliding Window
At first, you might think to solve this using the **Sliding Window** technique. This involves maintaining a window of characters and expanding or contracting it to satisfy the conditions of the problem. 

**Dry Run Example:**
Consider the string `abb`. Here's how it works:
- When we encounter the first 'b', we have the characters: `a` (1 odd) and `b` (1 odd). Since there are two characters with odd occurrences, this violates our condition.
- To fix this, we move the left pointer to the next character (right after the first 'b').
- When we encounter the second 'b', the substring `abb` becomes valid because now we have `a` (1 odd) and `b` (even). 

### Using XOR with a Bitmask
Now, let's implement the idea using a **bitmask**. Each character from 'a' to 'j' can be represented by a bit in an integer (10 bits total). We use XOR to track odd/even occurrences of characters.

1. **Initialize a bitmask:** Start with a bitmask initialized to `0`.
2. **Count occurrences using XOR:** For each character in the string, we apply the XOR operation to the bitmask for that character.

### Finding Valid Substrings
Next, we check if the bitmask we've computed has been seen before. If it has, it indicates that the characters between the previous occurrence and the current position cancel each other out, resulting in an even frequency of all characters in that range. Thus, we can increment our result count.

**Example:**
Consider processing the string `a0a1b0b1`, where:
- `0` represents even occurrences and `1` represents odd occurrences.

After processing the last 'b', if the bitmask (let's call it `psum`) equals `0`, it means that all characters from the beginning to this index have even occurrences (they've canceled each other out). 

In this case, if `psum[0]` was initialized in a hashmap, finding `psum` as `0` again implies the substring from the start to this index has even occurrences of characters.

### At Most 1 Odd
For problems requiring "at most 1 odd," you can:
- Count how many characters have odd occurrences. If you encounter a character that makes it two odd counts, adjust your window by moving the left pointer until you're back to one odd count or none.

[1442. Count Triplets That Can Form Two Arrays of Equal XOR](https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/)

In this problem, the goal is to count the number of triplets `(i, j, k)` such that the XOR of elements between indices `i` and `j` is equal to the XOR of elements between indices `j+1` and `k`.

```java
public class Solution {
    public int countTriplets(int[] arr) {
        int n = arr.length;
        int[] prefixXor = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixXor[i] = prefixXor[i - 1] ^ arr[i - 1];
        }

        int count = 0;
        for (int j = 0; j < n; j++) {
            for (int k = j + 1; k <= n; k++) {
                if (prefixXor[j] == prefixXor[k]) {
                    count += k - j;
                }
            }
        }
        return count;
    }
}
```
[1915. Number of Wonderful Substrings](https://leetcode.com/problems/number-of-wonderful-substrings/)

The task is to count the number of substrings that have at most one character with an odd occurrence. We utilize a bitmask to represent the frequency of characters from 'a' to 'j'.

```java
import java.util.HashMap;

public class Solution {
    public long wonderfulSubstrings(String word) {
        long count = 0;
        int mask = 0;
        HashMap<Integer, Long> freqMap = new HashMap<>();
        freqMap.put(0, 1L); // To handle the case of odd counts being zero

        for (char c : word.toCharArray()) {
            mask ^= (1 << (c - 'a')); // Toggle the bit for the character
            
            // Add the count of previous occurrences of the same mask
            count += freqMap.getOrDefault(mask, 0L);

            // Check for all masks that differ by one bit (at most one odd character)
            for (int i = 0; i < 10; i++) {
                count += freqMap.getOrDefault(mask ^ (1 << i), 0L);
            }
            
            // Update the frequency map with the current mask
            freqMap.put(mask, freqMap.getOrDefault(mask, 0L) + 1);
        }

        return count;
    }
}
```
[1542. Find Longest Awesome Substring](https://leetcode.com/problems/find-longest-awesome-substring/)

This problem involves finding the longest substring where at most one character has an odd occurrence. We use a similar approach with a bitmask to track occurrences of digits.

```java
import java.util.HashMap;

public class Solution {
    public int longestAwesome(String s) {
        int mask = 0;
        HashMap<Integer, Integer> lenMap = new HashMap<>();
        lenMap.put(0, -1); // Initialize with mask 0 at index -1
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            mask ^= (1 << (s.charAt(i) - '0')); // Update the mask for the current character
            
            // Check if the current mask has been seen before
            if (lenMap.containsKey(mask)) {
                ans = Math.max(ans, i - lenMap.get(mask)); // Update answer
            }
            
            // Check masks that differ by one bit
            for (int j = 0; j < 10; j++) {
                int oddMask = mask ^ (1 << j);
                if (lenMap.containsKey(oddMask)) {
                    ans = Math.max(ans, i - lenMap.get(oddMask));
                }
            }
            
            // Store the first occurrence of this mask
            lenMap.putIfAbsent(mask, i);
        }

        return ans;
    }
}
```

[1371. Find the Longest Substring Containing Vowels in Even Counts](https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/)

Here, the goal is to find the longest substring where the counts of vowels are even. This problem is simpler since we only need to consider even occurrences.

```java
import java.util.HashMap;

public class Solution {
    public int findTheLongestSubstring(String s) {
        HashMap<Integer, Integer> lenMap = new HashMap<>();
        lenMap.put(0, -1); // Initialize with mask 0 at index -1
        int mask = 0;
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            // Check if the character is a vowel
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                mask ^= (1 << ("aeiou".indexOf(c))); // Update the mask for vowels
            }
            
            // Check if the current mask has been seen before
            if (lenMap.containsKey(mask)) {
                ans = Math.max(ans, i - lenMap.get(mask)); // Update answer
            } else {
                lenMap.put(mask, i); // Store the first occurrence of this mask
            }
        }

        return ans;
    }
}
```
---

## Section 4: 2D Prefix Sum Problems
 
In many problems, especially those involving matrices, we often need to perform operations on sub-matrices, such as finding the maximum, minimum, or sum within a specified range. The **2D prefix sum** technique is a powerful way to achieve this efficiently.

### 1. Computing the Prefix Sum

The prefix sum allows us to preprocess the matrix so that we can quickly query the sum of any sub-matrix in constant time after an initial setup phase.

**Prefix Sum Calculation**:
Given a 2D array `arr`, the prefix sum matrix `psum` is defined as follows:

\[
\text{psum}[i][j] = \text{arr}[i-1][j-1] + \text{psum}[i-1][j] + \text{psum}[i][j-1] - \text{psum}[i-1][j-1]
\]

- **Explanation**:
  - `psum[i-1][j]`: This adds all elements in the column above.
  - `psum[i][j-1]`: This adds all elements in the row to the left.
  - `- psum[i-1][j-1]`: This subtracts the overlapping area that has been added twice (the top-left corner).
  - Finally, we add the current element from the original array.
There are two operatiosn : compute prefix sum at next cell (r,c) and also get operation(like sum) between (r1 ,c1) & (r2, c2)  

```
Lets see with an example
Compute psum
psum[i][j] = psum[i-1] [j] + psum[i] [j-1]
if you notice that component [i-1][j-1] is added twice, so we will reduce one part.
psum[i][j] = psum[i-1] [j] + psum[i] [j-1] - psum [i-1][j-1]
and finall add current element.
image

psum[i+1][j+1] = psum[i+1] [j] + psum[i] [j+1] - psum[i][j] + arr[i][j]
**Size of psum matrix row and column will always be 1 extra. Same we will do while doing a get, so if someone ask
for sum(0,0) , we will actually return sum(1,1) **
```
![matrix](https://assets.leetcode.com/users/images/a79d8b57-1ae3-4735-b862-95898d574521_1715589271.9132378.png)

**Matrix Dimensions**:
- The size of the `psum` matrix will always be one row and one column larger than the original matrix. This allows easy access for computing sums, as it provides a zero boundary for easier calculations.

### 2. Querying the Prefix Sum
![image](https://assets.leetcode.com/users/images/fe64b6fa-b62d-479b-943b-66233b641ec1_1715590165.9704843.png)

To get the sum of a sub-matrix defined by the corners \((r1, c1)\) and \((r2, c2)\), we can use the prefix sum matrix as follows:

\[
\text{sum}(r1, c1, r2, c2) = \text{psum}[r2][c2] - \text{psum}[r1-1][c2] - \text{psum}[r2][c1-1] + \text{psum}[r1-1][c1-1]
\]

- **Explanation**:
  - `psum[r2][c2]`: Gives the total sum from the top-left corner (0,0) to (r2,c2).
  - `- psum[r1-1][c2]`: Subtracts the area above the top-left corner of the desired sub-matrix.
  - `- psum[r2][c1-1]`: Subtracts the area to the left of the bottom-right corner of the desired sub-matrix.
  - `+ psum[r1-1][c1-1]`: Adds back the area that was subtracted twice (the top-left corner area).

```
Get psum : suppose we have to get psum between sub-matrix (r2 ,c2) & (r3, c3) Yellow painted region.

image

We have orange region computed with us from earlier section and how to get yellow region out. If we deleted
psum(r1 - 1, c2) and psum (r2 , c1 -1)

Again notice that we are deleting psum[r1 - 1, c1-1) , twice , so adjust that by adding
Notice that above we have stored psum(0,0) actually as psum(1,1), so whatever operation we do, increment r1,c1,r2,c2 by 1 before doing that.
psum[r2] [c2] - psum[r1-1] [c2] - psum[r2][c1-1] + psum[r1-1][c1-1]
```

### Example Implementation

Here’s how you can implement the prefix sum and querying mechanism in Java:

```java
public class NumMatrix {
    private int[][] psum;

    public NumMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            psum = new int[1][1];
            return;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        psum = new int[rows + 1][cols + 1];

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                psum[i][j] = matrix[i - 1][j - 1]
                        + psum[i - 1][j]
                        + psum[i][j - 1]
                        - psum[i - 1][j - 1];
            }
        }
    }

    public int sumRegion(int r1, int c1, int r2, int c2) {
        return psum[r2 + 1][c2 + 1]
                - psum[r1][c2 + 1]
                - psum[r2 + 1][c1]
                + psum[r1][c1];
    }
}
```

### Explanation of the Code

1. **Initialization**:
   - The constructor takes a 2D matrix as input and computes the prefix sum matrix.
   - We create a `psum` matrix that is one row and one column larger than the input matrix.

2. **Prefix Sum Calculation**:
   - We iterate through each element of the input matrix, applying the formula for the prefix sum.

3. **Querying the Sum**:
   - The `sumRegion` method calculates the sum of the specified sub-matrix using the prefix sum matrix based on the formula discussed.  

[304. Range Sum Query 2D - Immutable](https://leetcode.com/problems/range-sum-query-2d-immutable/)

```java
class NumMatrix {
    private int[][] psum;

    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        psum = new int[m + 1][n + 1];

        // Compute the prefix sum
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                psum[i + 1][j + 1] = psum[i + 1][j] + psum[i][j + 1] - psum[i][j] + matrix[i][j];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        // Get the sum of the region using the prefix sums
        return psum[row2 + 1][col2 + 1] 
             - psum[row2 + 1][col1] 
             - psum[row1][col2 + 1] 
             + psum[row1][col1];
    }
}
```
[1314. Matrix Block Sum](https://leetcode.com/problems/matrix-block-sum/)

```java

Same as earlier 2D prefix sum, so here at each cell , we have to form a valid rectangle of size 2k ( k on left, right , up down).
So first compute psum of input matrix.
Then for each grid cell, compute bottom right and top-left co-ordinate of a valid rectangle, and then compute psum in standard 2D way.
class Solution {
public:
    public int[][] matrixBlockSum(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;
        int[][] psum = new int[m + 1][n + 1];
        
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                psum[i + 1][j + 1] = psum[i + 1][j] + psum[i][j + 1] - psum[i][j] + mat[i][j];
        
        int[][] result = new int[m][n];
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j) {
                int r1 = Math.max(i - k, 0);
                int c1 = Math.max(j - k, 0);
                int r2 = Math.min(i + k + 1, m);
                int c2 = Math.min(j + k + 1, n);
                result[i][j] = psum[r2][c2] - psum[r2][c1] - psum[r1][c2] + psum[r1][c1];
            }
        return result;
    }
}
```

[2132. Stamping the Grid](https://leetcode.com/problems/stamping-the-grid/)

```java

In this question we are given some empty cells (marked as 0 ), and we are given quadilateral stamp size. Return true if each and every empty cell is covered by some stamp.
Intuition 1: Once you come to a cell, check the polygon of stamp size, if the 2d psum is 0, which means that entire stamping grid is empty and can be stamped.
Now if you stamp it we have to leave some marker that this grid area is stamped, How can we do it quickly.
To understand that we have to learn a trick called Differential Array.
1D Differential Array:
Lets understand with a porblem statement, suppose we have input array [0, 0, 0, 0, 0, 0] and we want to update values in range 1, 4. Then we have 0, 1, 1, 1, 1, 0] here there are multiple update operation and later we have to print tha updated array, how can we do this efficiently for each update ? We create another array called difference array and update index 1 and 5 [0, 1, 0, 0, 0, -1, 0]
Note the size of differential array, 1 more than original array, Why ?
Suppose you have to mark first and last element , you have to mark last+1 and hence 1 extra size of this differential array.

Now if you do psum on this diffential array you get [0, 1, 1, 1, 1, 0, 0]

2D-Diiffential Array :
Not very intutive to get in one go, let me show with an example.

Suppose we have 3 * 3 matrix and we have to update the sub-grid (0,0) to (1,1) , we update following index

[0,0] =  1
[0, 2] = -1 ( size of width which is 2)
[2, 0] = -1 ( size of height is 2) 
[2, 2] =  1

Updating [0,0] & [0,2] is same as 1-D Difference Array but why we do reverse for [2,0]=-1 and [2,2]=1 ?
Reason is when we update psum pf row=2 we want all those psum to nullify the affect of above row (row=1). I would suggest you do this small example on paper and it will get things clear.

 [ 0, 0, 0 ],          [1, 0, -1],            
 [ 0, 0, 0 ],    --->  [0, 0, 0 ],
 [ 0, 0, 0 ]           [-1, 0, 1]

Now lets compute psum of the above grid, remember that psum is 1-index and hence we always allocate an extra size for both row and column and update using following way

    for(int i = 0; i < m ; ++i){
     for(int j =0; j < n ; ++j){
       psum[i+1][j+1] = psum[i][j+1] + psum[i+1][j] -psum[i][j] +grid[i][j];

Do manually using on a paper and notice we find the exact grid which is stamped i.e.

[0, 0, 0, 0],
[0, 1, 1, 0],
[0, 1, 1, 0],
[0, 0, 0, 0]

Note that psum grid from range (1,1) to (2, 2) actually correpond to stamped area from (0, 0) to (1,1).
So for this problem

    First find the stamp size grid at each cell, and if that grid sum is 0, that mean this grid is empty and can be stamped, place the marker as shown above in another matrix.
    Compute psum of that new matrix.
    For each empty grid cell, compute psum and if it is 0 that means, no one stamped it. return false.
    In the end return true, that means each empty grid is stamped by someone.

Note in below code, I am using stamp vector both for stamping as well as doing prefix sum on it. Hence the size is +2 extra, +1 for Difference Array and +1 for Prefix Sum.
Secondly while iterating the original grid, and once we find a grid of size of stamp, for stamping purpose I am doing +1 to all the co-ordinate as we are operating in psum array.

class Solution {
public:
    public boolean possibleToStamp(int[][] grid, int h, int w) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] psum1 = new int[m + 1][n + 1];
        
        // Compute prefix sum for grid
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                psum1[i + 1][j + 1] = psum1[i + 1][j] + psum1[i][j + 1] - psum1[i][j] + grid[i][j];
            }
        }
        
        int[][] stamp = new int[m + 2][n + 2];
        for (int i = 0; i + h <= m; ++i) {
            for (int j = 0; j + w <= n; ++j) {
                if (psum1[i + h][j + w] - psum1[i][j + w] - psum1[i + h][j] + psum1[i][j] == 0) {
                    stamp[i + 1][j + 1]++;
                    stamp[i + 1][j + w + 1]--;
                    stamp[i + h + 1][j + 1]--;
                    stamp[i + h + 1][j + w + 1]++;
                }
            }
        }
        
        // Compute the final stamp array
        for (int i = 1; i <= m; ++i) {
            for (int j = 1; j <= n; ++j) {
                stamp[i][j] += stamp[i][j - 1] + stamp[i - 1][j] - stamp[i - 1][j - 1];
            }
        }
        
        // Check if all empty cells can be stamped
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] == 0 && stamp[i + 1][j + 1] == 0) return false;
            }
        }
        return true;
    }
}
```
[221. Maximal Square](https://leetcode.com/problems/maximal-square/)
```java

 Strandard psum 2D template , at each grid which is 1 , we assume it as bottom right corner and try to make mimumim of left, up and corner , if all 3 are 1 , that we can form a sqaure here.

class Solution {
public:
    public int maximalSquare(char[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] psum = new int[m + 1][n + 1];
        int ans = 0;

        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (matrix[i][j] == '1') {
                    psum[i + 1][j + 1] = 1 + Math.min(psum[i][j + 1], Math.min(psum[i + 1][j], psum[i][j]));
                    ans = Math.max(ans, psum[i + 1][j + 1]);
                }
            }
        }
        return ans * ans;
    }
}
```

[2201.Count Artifacts That Can Be Extracted](https://leetcode.com/problems/count-artifacts-that-can-be-extracted/)

```java

Mostly same as Range Sum Query above.
Key idea is calculate the rectangular size of artificat by left-top and right-bottom co-ordinates.
and then see if the psum of this rectangular block has same excavation size or not.
If yes that means all block has been excavated and this artifcats can be collected.

class Solution {
public:
    public int digArtifacts(int n, int[][] artifacts, int[][] dig) {
        int[][] grid = new int[n][n];
        for (int[] d : dig) {
            grid[d[0]][d[1]] = 1;
        }

        int[][] psum = new int[n + 1][n + 1];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                psum[i + 1][j + 1] = psum[i + 1][j] + psum[i][j + 1] - psum[i][j] + grid[i][j];
            }
        }

        int ans = 0;
        for (int[] a : artifacts) {
            int artifactSize = (a[3] - a[1] + 1) * (a[2] - a[0] + 1);
            int r1 = a[0] + 1;
            int c1 = a[1] + 1;
            int r2 = a[2] + 1;
            int c2 = a[3] + 1; // psum is 1-index
            int excavationSize = psum[r2][c2] - psum[r1 - 1][c2] - psum[r2][c1 - 1] + psum[r1 - 1][c1 - 1];
            if (artifactSize == excavationSize) {
                ans++;
            }
        }
        return ans;
    }
}
```

[3148.Maximum Difference Score in a Grid](https://leetcode.com/problems/maximum-difference-score-in-a-grid/)  

```
In this problem, we can ask from anywhere but move either right or down.
Score would be calculated as c2-c1 where c2 is new cell and c1 is old cell value.
Suppose we make multiple moves overall score would be.
(c5 -c4) + (c4 - c3) +(c3 - c2) + (c2 -c1)
Notice that each term would cancel each other except first and last, so if we are at agiven grid cell, we need to find minimum c1 so that score can be maximized, so we need to find the minimum number in grid, for example if we are at 14 , we need to find blue or red grid value. Minium in this grid can be calculated using prefix sum(minimum) technique, see code
```
[2017. Grid Game](https://leetcode.com/problems/grid-game/description/)
