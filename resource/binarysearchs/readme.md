# Binary Search Problems

### 1. Difficult to Figure Out If Binary Search Can Be Applied
These problems typically involve finding a minimum value that satisfies certain conditions in an array of length \( n \). The runtime for these problems is usually \( O(n \log m) \).

- [Minimum Number of Days to Make M Bouquets](https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/)
- [Sum of Mutated Array Closest to Target](https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/)
- [Find the Smallest Divisor Given a Threshold](https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/)
- [Koko Eating Bananas](https://leetcode.com/problems/koko-eating-bananas/)
- [Capacity to Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)

**Example Implementation for Minimum Number of Days to Make M Bouquets:**

```java
class Solution {
    private boolean isValid(int[] bloomDay, int m, int k, int mid) {
        int count = 0, size = 0;
        for (int day : bloomDay) {
            size = (day <= mid) ? size + 1 : 0;
            if (size == k) {
                size = 0;
                count++;
            }
            if (count == m) return true;
        }
        return false;
    }

    public int minDays(int[] bloomDay, int m, int k) {
        if (bloomDay.length == 0 || m == 0 || k == 0) return 0;
        if (m * k > bloomDay.length) return -1;

        int l = Integer.MAX_VALUE, r = Integer.MIN_VALUE;
        for (int day : bloomDay) {
            l = Math.min(l, day);
            r = Math.max(r, day);
        }

        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (isValid(bloomDay, m, k, mid))
                r = mid - 1;
            else
                l = mid + 1;
        }
        return l;
    }
}
```

### 2. Tricky Binary Search
These problems often involve multiple conditions to determine whether to select the left or right portion of the array.

- [Find Peak Element](https://leetcode.com/problems/find-peak-element/)
- [Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)
- [Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)
- [Missing Element in Sorted Array](https://leetcode.com/problems/missing-element-in-sorted-array/)

**Example Implementation for Find Peak Element:**

```java
class Solution {
    private int findPeakElementUtil(int[] nums, int l, int r) {
        if (l > r) return -1;
        int m = l + (r - l) / 2;

        if ((m > 0 && nums[m] > nums[m - 1]) && (m < nums.length - 1 && nums[m] > nums[m + 1]))
            return m;

        if (m == 0 && nums.length > 1 && nums[m] > nums[m + 1])
            return m;

        if (m == nums.length - 1 && nums[m] > nums[m - 1])
            return m;

        int left = findPeakElementUtil(nums, l, m - 1);
        int right = findPeakElementUtil(nums, m + 1, r);

        return (left != -1) ? left : right;
    }

    public int findPeakElement(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;
        return findPeakElementUtil(nums, 0, n - 1);
    }
}

```

### 3. Simple Binary Search
These problems typically have straightforward conditions where binary search can be applied directly.

- [Find Smallest Letter Greater Than Target](https://leetcode.com/problems/find-smallest-letter-greater-than-target/)
- [Missing Element in Sorted Array](https://leetcode.com/problems/missing-element-in-sorted-array/)
- [Peak Index in a Mountain Array](https://leetcode.com/problems/peak-index-in-a-mountain-array/)
- [H-Index II](https://leetcode.com/problems/h-index-ii/)
- [Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)
- [First Bad Version](https://leetcode.com/problems/first-bad-version/)

**Example Implementation for Find Smallest Letter Greater Than Target:**

```java
class Solution {
    public char nextGreatestLetter(char[] letters, char target) {
        int n = letters.length;
        int l = 0, r = n - 1;

        if (target >= letters[n - 1] || target < letters[0])
            return letters[0];

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (m > 0 && (target >= letters[m - 1] && target < letters[m]))
                return letters[m];
            else if (target >= letters[m])
                l = m + 1;
            else    
                r = m - 1;
        }
        return letters[l];
    }
}

```

### 4. Using Upper Bound for Binary Search
These problems utilize C++ STL functions for efficient searching.

- [Time-Based Key-Value Store](https://leetcode.com/problems/time-based-key-value-store/)
- [Online Election](https://leetcode.com/problems/online-election/)
- [Random Pick with Weight](https://leetcode.com/problems/random-pick-with-weight/)
- [Find Right Interval](https://leetcode.com/problems/find-right-interval/)

**Example Implementation for Time-Based Key-Value Store:**

```java
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class TimeMap {
    private Map<String, TreeMap<Integer, String>> map;

    /** Initialize your data structure here. */
    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        map.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) return "";
        TreeMap<Integer, String> timeMap = map.get(key);
        Integer floorKey = timeMap.floorKey(timestamp);
        return (floorKey == null) ? "" : timeMap.get(floorKey);
    }
}

```

### 5. Binary Search Based on Condition on Two Arrays
These problems often involve two sorted arrays and require a binary search approach.

- [Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/)

**Example Implementation for Median of Two Sorted Arrays:**

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;

        if (n > m) return findMedianSortedArrays(nums2, nums1);

        int k = (n + m - 1) / 2;
        int l = 0;
        int r = Math.min(k, n);

        while (l < r) {
            int mid1 = l + (r - l) / 2;
            int mid2 = k - mid1;

            if (nums1[mid1] > nums2[mid2])
                r = mid1;
            else
                l = mid1 + 1;
        }

        int a = Math.max(l >= 1 ? nums1[l - 1] : Integer.MIN_VALUE, k >= l ? nums2[k - l] : Integer.MIN_VALUE);
        if ((n + m) % 2 != 0) return a;

        int b = Math.min(l < n ? nums1[l] : Integer.MAX_VALUE, k - l + 1 < m ? nums2[k - l + 1] : Integer.MAX_VALUE);
        return (a + b) / 2.0;
    }
}

```

---

### Binary Search Problems

1. **[Median of Two Sorted Arrays](https://leetcode.com/problems/median-of-two-sorted-arrays/)**
   - Find the median of two sorted arrays with a combined length of \( m + n \).

2. **[Kth Smallest Element in Two Sorted Arrays](https://leetcode.com/problems/kth-smallest-element-in-two-sorted-arrays/)**
   - Find the kth smallest element in the union of two sorted arrays.

3. **[Search in a Sorted Array of Unknown Size](https://leetcode.com/problems/search-in-a-sorted-array-of-unknown-size/)**
   - Search for a target in an array where the size is unknown.

4. **[Capacity to Ship Packages Within D Days](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/)**
   - Determine the minimum capacity needed to ship packages within a given number of days.

5. **[Find First and Last Position of Element in Sorted Array](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)**
   - Find the starting and ending position of a given target in a sorted array.

6. **[Find K Closest Elements](https://leetcode.com/problems/find-k-closest-elements/)**
   - Find the k closest elements to a given target in a sorted array.

7. **[Kth Smallest Element in a Sorted Matrix](https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/)**
   - Find the kth smallest element in a row-wise and column-wise sorted matrix.

8. **[Peak Element](https://leetcode.com/problems/find-peak-element/)**
   - Find a peak element in an array, where an element is considered a peak if it is greater than its neighbors.

9. **[Peak Index in a Mountain Array](https://leetcode.com/problems/peak-index-in-a-mountain-array/)**
   - Find the index of the peak in a mountain array.

10. **[Search a 2D Matrix](https://leetcode.com/problems/search-a-2d-matrix/)**
    - Search for a target value in a 2D matrix that has been sorted.

11. **[Search a 2D Matrix II](https://leetcode.com/problems/search-a-2d-matrix-ii/)**
    - Search for a target value in a 2D matrix where each row and column is sorted in ascending order.

12. **[Search in a Nearly Sorted Array](https://leetcode.com/problems/search-in-a-nearly-sorted-array/)**
    - Search for a target in a nearly sorted array where each element may be misplaced by one position.

13. **[Search Insert Position](https://leetcode.com/problems/search-insert-position/)**
    - Find the index at which a target should be inserted into a sorted array.

14. **[Single Element in a Sorted Array](https://leetcode.com/problems/single-element-in-a-sorted-array/)**
    - Find the single element in a sorted array where every other element appears twice.

15. **[Split Array Largest Sum](https://leetcode.com/problems/split-array-largest-sum/)**
    - Split an array into \( m \) non-empty continuous subarrays such that the largest sum among these subarrays is minimized.

16. **[First Bad Version](https://leetcode.com/problems/first-bad-version/)**
    - Find the first bad version in a list of versions, where some are good and others are bad.

17. **[Longest Duplicate Substring](https://leetcode.com/problems/longest-duplicate-substring/)**
    - Find the longest duplicate substring in a given string.

### Binary Search in Rotated Sorted Arrays

18. **[Search in Rotated Sorted Array](https://leetcode.com/problems/search-in-rotated-sorted-array/)**
    - Search for a target in a rotated sorted array.

19. **[Search in Rotated Sorted Array II](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/)**
    - Search for a target in a rotated sorted array that may contain duplicates.

20. **[Find Minimum in Rotated Sorted Array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)**
    - Find the minimum element in a rotated sorted array.

21. **[Find Minimum in Rotated Sorted Array II](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/)**
    - Find the minimum element in a rotated sorted array that may contain duplicates.

22. **[Find Number of Rotations in Rotated Sorted Array](https://leetcode.com/problems/find-rotation-count/)**
    - Determine the number of times a sorted array has been rotated.

23. **[Find Pivot in Rotated Sorted Array](https://leetcode.com/problems/find-pivot-in-rotated-sorted-array/)**
    - Find the pivot index where the sorted array was rotated.
