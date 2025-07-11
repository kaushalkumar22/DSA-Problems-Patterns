
# Sliding Window 

--- 

1. [**3. Longest Substring Without Repeating Characters**](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
2. [**159. Longest Substring with At Most Two Distinct Characters**](https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/)
3. [**340. Longest Substring with At Most K Distinct Characters**](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/)
4. [**424. Longest Repeating Character Replacement**](https://leetcode.com/problems/longest-repeating-character-replacement/)
5. [**1004. Max Consecutive Ones III**](https://leetcode.com/problems/max-consecutive-ones-iii/)
6. [**1838. Frequency of the Most Frequent Element**](https://leetcode.com/problems/frequency-of-the-most-frequent-element/)
7. [**992. Subarrays with K Different Integers**](https://leetcode.com/problems/subarrays-with-k-different-integers/)
8. [**1423. Maximum Points You Can Obtain from Cards**](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/)
9. [**904. Fruit Into Baskets**](https://leetcode.com/problems/fruit-into-baskets/)
10. [**862. Shortest Subarray with Sum at Least K**](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/)
11. [**209. Minimum Size Subarray Sum**](https://leetcode.com/problems/minimum-size-subarray-sum/)

---

## Template 1: Sliding Window (Shrinkable)

```java
int left = 0, right = 0, ans = 0;
for (right = 0; right < N; ++right) {
    // CODE: use A[right] to update state which might make the window invalid
    for (; invalid(); ++left) { // when invalid, keep shrinking the left edge until it's valid again
        // CODE: update state using A[left]
    }
    ans = max(ans, right - left + 1); // the window [left, right] is the maximum window we've found thus far
}
return ans;
```
---

Essentially, we want to keep the window valid at the end of each outer for loop.

### Example: [1838. Frequency of the Most Frequent Element](https://leetcode.com/problems/frequency-of-the-most-frequent-element/)

- **What should we use as the state?**  
It should be the sum of numbers in the window.
- **How to determine invalid?**  
The window is invalid if `(right - left + 1) * A[right] - sum > k`.  
  
For every new element A[right] to the sliding window,Add it to the sum by sum += A[right].  
Check if it'a valid window  `(right - left + 1) * A[right] - sum > k`  
  
If not, removing A[left] from the window by sum -= A[left] and left += 1.  
Then update the res by res = max(res, right - left + 1).  

```java
// Time: O(NlogN)
// Space: O(1)
class Solution {
    public int maxFrequency(int[] A, int k) {
        Arrays.sort(A);
        long left = 0, N = A.length, ans = 1, sum = 0;
        for (int right = 0; right < N; ++right) {
            sum += A[right];
            while ((right - left + 1) * A[right] - sum > k) sum -= A[left++];
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
```

### FAQ:

- **Why is the time complexity O(NlogN)?**  
  The sorting takes O(NlogN). The two-pointer part only takes O(N) because both the pointers `left` and `right` traverse the array only once.
  
- **Why is `(right - left + 1) * A[right] - sum <= k` valid?**  
  `(right - left + 1)` is the length of the window `[left, right]`. We want to increase all the numbers in the window to equal `A[right]`. The number of operations needed is `(right - left + 1) * A[right] - sum`, which should be `<= k`.

## Template 2: Sliding Window (Non-shrinkable)

```java
int left = 0, right = 0;
for (; right < N; ++right) {
    // CODE: use A[right] to update state which might make the window invalid
    if (invalid()) { // Increment the left edge ONLY when the window is invalid
        // CODE: update state using A[left]
        ++left;
    }
    // after `++right` in the for loop, this window `[left, right)` of length `right - left` might be valid.
}
return right - left;
```

### Example Solution:

```java
// OJ: https://leetcode.com/problems/frequency-of-the-most-frequent-element/
// Author: github.com/lzl124631x
// Time: O(NlogN)
// Space: O(1)
class Solution {
    public int maxFrequency(int[] A, int k) {
        Arrays.sort(A);
        long left = 0, right = 0, N = A.length, sum = 0;
        for (; right < N; ++right) {
            sum += A[right];
            if ((right - left + 1) * A[right] - sum > k) sum -= A[left++];
        }
        return right - left;
    }
}
```

## Apply these Templates to Other Problems  

[1493. Longest Subarray of 1's After Deleting One Element (Medium)](https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/)
#### Sliding Window (Shrinkable)

- **What's the state?** `cnt` as the number of `0`s in the window.
- **What's invalid?** `cnt > 1` is invalid.

```java
// Time: O(N)
// Space: O(1)
class Solution {
    public int longestSubarray(int[] A) {
        int left = 0, right = 0, N = A.length, cnt = 0, ans = 0;
        for (; right < N; ++right) {
            cnt += A[right] == 0 ? 1 : 0;
            while (cnt > 1) cnt -= A[left++] == 0 ? 1 : 0;
            ans = Math.max(ans, right - left);
        }
        return ans;
    }
}
```

#### Sliding Window (Non-shrinkable)

```java
// Time: O(N)
// Space: O(1)
class Solution {
    public int longestSubarray(int[] A) {
        int left = 0, right = 0, N = A.length, cnt = 0;
        for (; right < N; ++right) {
            cnt += A[right] == 0 ? 1 : 0;
            if (cnt > 1) cnt -= A[left++] == 0 ? 1 : 0;
        }
        return right - left - 1;
    }
}
```

[3. Longest Substring Without Repeating Characters (Medium)](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

#### Sliding Window (Shrinkable)

- **State:** `cnt[ch]` is the number of occurrences of character `ch` in the window.
- **Invalid:** `cnt[s[right]] > 1` is invalid.

```java
// Time: O(N)
// Space: O(1)
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0, N = s.length(), ans = 0;
        int[] cnt = new int[128];
        for (; right < N; ++right) {
            cnt[s.charAt(right)]++;
            while (cnt[s.charAt(right)] > 1) cnt[s.charAt(left++)]--;
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }
}
```

#### Sliding Window (Non-shrinkable)

```java
// OJ: https://leetcode.com/problems/longest-substring-without-repeating-characters/
// Time: O(N)
// Space: O(1)
class Solution {
    public int lengthOfLongestSubstring(String s) {
        int left = 0, right = 0, N = s.length();
        int[] cnt = new int[128];
        int dup = 0;
        for (; right < N; ++right) {
            dup += ++cnt[s.charAt(right)] == 2 ? 1 : 0;
            if (dup > 0) dup -= --cnt[s.charAt(left++)] == 1 ? 1 : 0;
        }
        return right - left;
    }
}
```

### 713. Subarray Product Less Than K (Medium)

#### Sliding Window (Shrinkable)

- **State:** `prod` is the product of the numbers in the window.
- **Invalid:** `prod >= k` is invalid.

```java
// OJ: https://leetcode.com/problems/subarray-product-less-than-k/
// Time: O(N)
// Space: O(1)
class Solution {
    public int numSubarrayProductLessThanK(int[] A, int k) {
        if (k == 0) return 0;
        long left = 0, right = 0, prod = 1, ans = 0;
        for (; right < A.length; ++right) {
            prod *= A[right];
            while (left <= right && prod >= k) prod /= A[(int) left++];
            ans += right - left + 1;
        }
        return (int) ans;
    }
}
```


1. [3. Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)
2. [159. Longest Substring with At Most Two Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/)
3. [340. Longest Substring with At Most K Distinct Characters](https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/)
4. [424. Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/)
5. [1004. Max Consecutive Ones III](https://leetcode.com/problems/max-consecutive-ones-iii/)
6. [1838. Frequency of the Most Frequent Element](https://leetcode.com/problems/frequency-of-the-most-frequent-element/)
7. [992. Subarrays with K Different Integers](https://leetcode.com/problems/subarrays-with-k-different-integers/)
8. [1423. Maximum Points You Can Obtain from Cards](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/)
9. [904. Fruit Into Baskets](https://leetcode.com/problems/fruit-into-baskets/)
10. [862. Shortest Subarray with Sum at Least K](https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/)
11. [209. Minimum Size Subarray Sum](https://leetcode.com/problems/minimum-size-subarray-sum/)
