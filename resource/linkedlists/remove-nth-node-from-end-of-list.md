# [19. Remove Nth Node From End of List (Medium)](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

<p>Given the <code>head</code> of a linked list, remove the <code>n<sup>th</sup></code> node from the end of the list and return its head.</p>

<p><strong>Follow up:</strong>&nbsp;Could you do this in one pass?</p>

<p>&nbsp;</p>
<p><strong>Example 1:</strong></p>
<img alt="" src="https://assets.leetcode.com/uploads/2020/10/03/remove_ex1.jpg" style="width: 542px; height: 222px;">
<pre><strong>Input:</strong> head = [1,2,3,4,5], n = 2
<strong>Output:</strong> [1,2,3,5]
</pre>

<p><strong>Example 2:</strong></p>

<pre><strong>Input:</strong> head = [1], n = 1
<strong>Output:</strong> []
</pre>

<p><strong>Example 3:</strong></p>

<pre><strong>Input:</strong> head = [1,2], n = 1
<strong>Output:</strong> [1]
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li>The number of nodes in the list is <code>sz</code>.</li>
	<li><code>1 &lt;= sz &lt;= 30</code></li>
	<li><code>0 &lt;= Node.val &lt;= 100</code></li>
	<li><code>1 &lt;= n &lt;= sz</code></li>
</ul>

## Solution

This problem has 3 edge cases need to consider n =1 ,n=n and if size of list is 1.

```java
Complexity
    Time complexity:O(n)
    Space complexity:O(1)
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode p = head, q = head;

        // Move q n steps ahead
        for (int i = 0; i < n; i++) {
            q = q.next;
        }

        // If q is null, it means we need to remove the head
        if (q == null) {
            return head.next;
        }

        // Move p and q together until q reaches the end
        while (q.next != null) {
            p = p.next;
            q = q.next;
        }

        // p is now just before the node to be removed
        p.next = p.next.next;

        return head;
    }
}

```

Approach


```java
Complexity
    Time complexity:O(n)
    Space complexity:O(1)
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(0);
        ListNode dummyHead = dummy;
        dummy.next = head; 
        ListNode ptr1= dummy,ptr2 = dummy, prev;
        for(int i = 0 ;i<n && ptr1!=null;i++){
            ptr1 = ptr1.next;
        }
        prev = ptr2;
        while(ptr1!= null){
            ptr1 = ptr1.next;
            prev = ptr2;
            ptr2 = ptr2.next;
        }
        prev.next = ptr2.next;

        return dummyHead.next;
    }
}
```