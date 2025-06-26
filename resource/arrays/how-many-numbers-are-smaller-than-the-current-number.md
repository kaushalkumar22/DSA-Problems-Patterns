
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
