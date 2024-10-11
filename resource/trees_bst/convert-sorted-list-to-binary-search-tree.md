# [109. Convert Sorted List to Binary Search Tree (Medium)](https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/)

<p>Given the <code>head</code> of a singly linked list where elements are <strong>sorted in ascending order</strong>, convert it to a height balanced BST.</p>

<p>For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two subtrees of <em>every</em> node never differ by more than 1.</p>

<p>&nbsp;</p>
<p><strong>Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/08/17/linked.jpg" style="width: 600px; height: 466px;">
<pre><strong>Input:</strong> head = [-10,-3,0,5,9]
<strong>Output:</strong> [0,-3,9,-10,null,5]
<strong>Explanation:</strong> One possible answer is [0,-3,9,-10,null,5], which represents the shown height balanced BST.
</pre>

<p><strong>Example 2:</strong></p>

<pre><strong>Input:</strong> head = []
<strong>Output:</strong> []
</pre>

<p><strong>Example 3:</strong></p>

<pre><strong>Input:</strong> head = [0]
<strong>Output:</strong> [0]
</pre>

<p><strong>Example 4:</strong></p>

<pre><strong>Input:</strong> head = [1,3]
<strong>Output:</strong> [3,1]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in <code>head</code> is in the range <code>[0, 2 * 10<sup>4</sup>]</code>.</li>
	<li><code>-10^5 &lt;= Node.val &lt;= 10^5</code></li>
</ul>


**Companies**:  
[Facebook](https://leetcode.com/company/facebook), [Microsoft](https://leetcode.com/company/microsoft), [Oracle](https://leetcode.com/company/oracle)

---

### **Solution 1: Convert Sorted List to Binary Search Tree using `getLength`**

This solution involves getting the length of the list first, then performing a recursive depth-first search (DFS) to create the tree.

```java
// Time: O(NlogN)
// Space: O(logN)
class Solution {

    // Helper method to get the length of the linked list
    private int getLength(ListNode head) {
        int ans = 0;
        while (head != null) {
            ans++;  // Increment the length counter
            head = head.next;  // Move to the next node
        }
        return ans;  // Return the total length of the list
    }

    // Recursive DFS method to construct the binary search tree
    private TreeNode dfs(ListNode head, int len) {
        // Base case: if the length is zero, return null (no node to add)
        if (len == 0) return null;

        // Base case: if the length is 1, create a node with the current head value
        if (len == 1) return new TreeNode(head.val);

        // Move pointer to the middle element
        ListNode midNode = head;
        for (int i = 0; i < len / 2; i++) midNode = midNode.next;

        // Create the root node with the middle value
        TreeNode root = new TreeNode(midNode.val);

        // Recursively build the left subtree with the first half of the list
        root.left = dfs(head, len / 2);

        // Recursively build the right subtree with the second half of the list
        root.right = dfs(midNode.next, (len - 1) / 2);

        return root;  // Return the root of this subtree
    }

    // Main method to convert sorted linked list to BST
    public TreeNode sortedListToBST(ListNode head) {
        int len = getLength(head);  // Get the length of the list
        return dfs(head, len);  // Recursively build the tree
    }
}
```

---

### **Solution 2: Fast-Slow Pointer Method**

This solution uses two pointers (`fast` and `slow`) to find the middle node in the list and recursively build the tree.

```java
// Time: O(NlogN)
// Space: O(logN)
class Solution {

    // Recursive DFS method to construct the binary search tree
    private TreeNode dfs(ListNode head, ListNode end) {
        // Base case: if head reaches the end, return null (no node to add)
        if (head == end) return null;

        // Base case: if there is only one node between head and end, create a node
        if (head.next == end) return new TreeNode(head.val);

        // Use fast and slow pointers to find the middle of the list
        ListNode slow = head, fast = head;
        while (fast != end && fast.next != end) {
            slow = slow.next;  // Move slow pointer one step
            fast = fast.next.next;  // Move fast pointer two steps
        }

        // Create the root node with the middle value
        TreeNode root = new TreeNode(slow.val);

        // Recursively build the left subtree with the first half of the list
        root.left = dfs(head, slow);

        // Recursively build the right subtree with the second half of the list
        root.right = dfs(slow.next, end);

        return root;  // Return the root of this subtree
    }

    // Main method to convert sorted linked list to BST
    public TreeNode sortedListToBST(ListNode head) {
        return dfs(head, null);  // Start DFS from head to null
    }
}
```

---

### **Solution 3: In-Order Simulation with a Global Pointer**

This solution performs an in-order simulation and uses a class-level pointer to build the tree efficiently.

```java
// Time: O(N)
// Space: O(logN)
class Solution {

    // Class-level pointer to traverse the linked list
    private ListNode head;

    // Helper method to get the length of the linked list
    private int getLength(ListNode head) {
        int ans = 0;
        while (head != null) {
            ans++;  // Increment the length counter
            head = head.next;  // Move to the next node
        }
        return ans;  // Return the total length of the list
    }

    // Recursive DFS method to simulate in-order traversal and construct the BST
    private TreeNode dfs(int begin, int end) {
        // Base case: if begin equals end, return null (no node to add)
        if (begin == end) return null;

        // Find the middle index
        int mid = (begin + end) / 2;

        // Recursively build the left subtree first (in-order traversal)
        TreeNode left = dfs(begin, mid);

        // Create the root node with the current head value
        TreeNode root = new TreeNode(head.val);

        // Move the head pointer to the next node in the list
        head = head.next;

        // Attach the left subtree to the root
        root.left = left;

        // Recursively build the right subtree
        root.right = dfs(mid + 1, end);

        return root;  // Return the root of this subtree
    }

    // Main method to convert sorted linked list to BST
    public TreeNode sortedListToBST(ListNode head) {
        this.head = head;  // Set the class-level head pointer
        int len = getLength(head);  // Get the length of the list
        return dfs(0, len);  // Start DFS from 0 to length
    }
}
```

---

