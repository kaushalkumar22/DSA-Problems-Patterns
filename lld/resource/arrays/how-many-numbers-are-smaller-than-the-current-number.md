
# [How Many Numbers Are Smaller Than the Current Number](https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/)

<p>Given the array <code>nums</code>, for each <code>nums[i]</code> find out how many numbers in the array are smaller than it. That is, for each <code>nums[i]</code> you have to count the number of valid <code>j</code>'s such that <code>j != i</code> and <code>nums[j] &lt; nums[i]</code>.</p>

<p>Return the answer in an array.</p>

<h3>Example 1:</h3>
<pre>
Input: nums = [8,1,2,2,3]
Output: [4,0,1,1,3]
Explanation:
For nums[0] = 8 → four smaller numbers: 1, 2, 2, 3
For nums[1] = 1 → no smaller numbers
For nums[2] = 2 → one smaller number: 1
For nums[3] = 2 → one smaller number: 1
For nums[4] = 3 → three smaller numbers: 1, 2, 2
</pre>

<h3>Example 2:</h3>
<pre>
Input: nums = [6,5,4,8]
Output: [2,1,0,3]
</pre>

<h3>Example 3:</h3>
<pre>
Input: nums = [7,7,7,7]
Output: [0,0,0,0]
</pre>

<h3>Constraints:</h3>
<ul>
  <li>2 &le; nums.length &le; 500</li>
  <li>0 &le; nums[i] &le; 100</li>
</ul>

## Solution: 

***The brute-force approach would be:***

* For every element, loop over the entire array and count how many are smaller.
* Time Complexity: **O(n²)** — this works but is inefficient for larger arrays.

---

***Optimized Approach (Counting Sort + Prefix Sum):***

Since all values in `nums[i]` are between 0 and 100 ,we can avoid nested loops by precomputing frequencies and prefix sums.

* We don’t need to compare each pair.
* We can just **count** how many numbers are smaller than any given value by precomputing frequencies.  

Create array freq[101] to count frequency.  
freq[i] = how many times number i appears in nums array

Create a prefix-sum array prefix[101] , this array will contains count of each elements 
which is less than current value using freq array.  

prefix[i] = how many numbers are strictly less than i in freq.  
We do this by accumulating freq[i-1] + freq[i-2] + ... + freq[0].

For each `nums[i]`, we simply lookup `prefix[nums[i]]` — which gives the count of smaller elements.

---

### Time and Space Complexity:

* **Time:** O(n + K), where K = 101 (constant), so effectively **O(n)**
* **Space:** O(K) → constant extra space (since we use size-101 arrays)


```java

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
```

### Dry Run
```
Input: [8,1,2,2,3]
Frequencies:
freq[1]=1, freq[2]=2, freq[3]=1, freq[8]=1

Prefix sum (smaller counts): 
prefix[2] = 1 (just 1)  
prefix[3] = 3 (1,2,2)  
prefix[8] = 4 (1,2,2,3)  
Result: [4,0,1,1,3]  
```