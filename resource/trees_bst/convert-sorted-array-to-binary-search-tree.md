# [108. Convert Sorted Array to Binary Search Tree (Easy)](https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/)

<p>Given an integer array <code>nums</code> where the elements are sorted in <strong>ascending order</strong>, convert <em>it to a <strong>height-balanced</strong> binary search tree</em>.</p>

<p>A <strong>height-balanced</strong> binary tree is a binary tree in which the depth of the two subtrees of every node never differs by more than one.</p>

<p>&nbsp;</p>
<p><strong>Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/02/18/btree1.jpg" style="width: 302px; height: 222px;">
<pre><strong>Input:</strong> nums = [-10,-3,0,5,9]
<strong>Output:</strong> [0,-3,9,-10,null,5]
<strong>Explanation:</strong> [0,-10,5,null,-3,null,9] is also accepted:
<img alt="" src="https://assets.leetcode.com/uploads/2021/02/18/btree2.jpg" style="width: 302px; height: 222px;">
</pre>

<p><strong>Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2021/02/18/btree.jpg" style="width: 342px; height: 142px;">
<pre><strong>Input:</strong> nums = [1,3]
<strong>Output:</strong> [3,1]
<strong>Explanation:</strong> [1,3] and [3,1] are both a height-balanced BSTs.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= nums.length &lt;= 10<sup>4</sup></code></li>
	<li><code>-10<sup>4</sup> &lt;= nums[i] &lt;= 10<sup>4</sup></code></li>
	<li><code>nums</code> is sorted in a <strong>strictly increasing</strong> order.</li>
</ul>


**Companies**:  
[Amazon](https://leetcode.com/company/amazon), [Facebook](https://leetcode.com/company/facebook), [Google](https://leetcode.com/company/google), [Apple](https://leetcode.com/company/apple)

## Solution

```java
class Solution {

    // Helper method to recursively build the BST
    private TreeNode dfs(int[] A, int begin, int end) {
        // Base case: if the range is invalid (begin >= end), return null (no node to add)
        if (begin >= end) return null;

        // Find the middle element to be the root of the current subtree
        int mid = (begin + end) / 2;

        // Create a new node with the middle element as the root
        TreeNode root = new TreeNode(A[mid]);

        // Recursively build the left subtree using the left half of the array
        root.left = dfs(A, begin, mid);

        // Recursively build the right subtree using the right half of the array
        root.right = dfs(A, mid + 1, end);

        // Return the root of the subtree
        return root;
    }

    // Main method to convert sorted array to BST
    public TreeNode sortedArrayToBST(int[] A) {
        // Call the helper method with the entire range of the array
        return dfs(A, 0, A.length);
    }
}



- **Time Complexity: O(N)**, where `N` is the number of elements in the array. Each element is processed once.
- **Space Complexity: O(logN)**, due to the recursive calls on the stack, where `logN` is the height of the balanced tree.

```
