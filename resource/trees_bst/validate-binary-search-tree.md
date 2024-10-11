# [98. Validate Binary Search Tree (Medium)](https://leetcode.com/problems/validate-binary-search-tree/)

<p>Given the <code>root</code> of a binary tree, <em>determine if it is a valid binary search tree (BST)</em>.</p>

<p>A <strong>valid BST</strong> is defined as follows:</p>

<ul>
	<li>The left subtree of a node contains only nodes with keys <strong>less than</strong> the node's key.</li>
	<li>The right subtree of a node contains only nodes with keys <strong>greater than</strong> the node's key.</li>
	<li>Both the left and right subtrees must also be binary search trees.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/12/01/tree1.jpg" style="width: 302px; height: 182px;">
<pre><strong>Input:</strong> root = [2,1,3]
<strong>Output:</strong> true
</pre>

<p><strong>Example 2:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/12/01/tree2.jpg" style="width: 422px; height: 292px;">
<pre><strong>Input:</strong> root = [5,1,4,null,null,3,6]
<strong>Output:</strong> false
<strong>Explanation:</strong> The root node's value is 5 but its right child's value is 4.
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the tree is in the range <code>[1, 10<sup>4</sup>]</code>.</li>
	<li><code>-2<sup>31</sup> &lt;= Node.val &lt;= 2<sup>31</sup> - 1</code></li>
</ul>


**Companies**:  
[Amazon](https://leetcode.com/company/amazon), [Facebook](https://leetcode.com/company/facebook), [Bloomberg](https://leetcode.com/company/bloomberg), [Microsoft](https://leetcode.com/company/microsoft), [Zillow](https://leetcode.com/company/zillow), [Apple](https://leetcode.com/company/apple), [Google](https://leetcode.com/company/google), [Uber](https://leetcode.com/company/uber), [ByteDance](https://leetcode.com/company/bytedance)

### Pre-order Traversal
```java
class Solution {
    // Public method to check if a binary tree is a valid BST
    public boolean isValidBST(TreeNode root) {
        // Start the recursive check with minimum and maximum possible values for a long
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    // Helper method to validate BST with numerical boundaries
    private boolean isValidBST(TreeNode root, long left, long right) {
        // Base case: If the current node is null, it means the subtree is valid
        if (root == null) return true;
        
        // If the current node's value is not within the allowable range, return false
        if (root.val <= left || root.val >= right) return false;
        
        // Recursively check the left subtree with the current node's value as the new right boundary
        // Recursively check the right subtree with the current node's value as the new left boundary
        return isValidBST(root.left, left, root.val) && isValidBST(root.right, root.val, right);
    }
}

Time Complexity: O(N): Each node is visited exactly once.
Space Complexity: O(H): The recursion stack uses memory proportional to the height of the tree, which is H

```

### 3. In-order Traversal

```java
class Solution {
    // Class-level variable to track the previous node in the in-order traversal
    TreeNode prev = null;

    // Public method to check if a binary tree is a valid BST
    public boolean isValidBST(TreeNode root) {
        // Base case: If the current node is null, the subtree is valid
        if (root == null) return true;

        // Recursively check the left subtree first (in-order traversal)
        if (!isValidBST(root.left)) return false;

        // If the current node's value is less than or equal to the previous node's value, it's not a valid BST
        if (prev != null && root.val <= prev.val) return false;

        // Update the previous node to the current node
        prev = root;

        // Recursively check the right subtree
        return isValidBST(root.right);
    }
}

Time Complexity: O(N): Each node is visited exactly once.
Space Complexity: O(H): The recursion stack uses memory proportional to the height of the tree, which is H

```
