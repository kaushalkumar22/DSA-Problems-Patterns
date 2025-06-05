# [1472. Design Browser History (Medium)](https://leetcode.com/problems/design-browser-history/)

<p>You have a <strong>browser</strong> of one tab where you start on the <code>homepage</code> and you can visit another <code>url</code>, get back in the history number of <code>steps</code> or move forward in the history number of <code>steps</code>.</p>

<p>Implement the <code>BrowserHistory</code> class:</p>

<ul>
	<li><code>BrowserHistory(string homepage)</code> Initializes the object with the <code>homepage</code>&nbsp;of the browser.</li>
	<li><code>void visit(string url)</code>&nbsp;visits <code>url</code> from the current page. It clears up all the forward history.</li>
	<li><code>string back(int steps)</code>&nbsp;Move <code>steps</code> back in history. If you can only return <code>x</code> steps in the history and <code>steps &gt; x</code>, you will&nbsp;return only <code>x</code> steps. Return the current <code>url</code>&nbsp;after moving back in history <strong>at most</strong> <code>steps</code>.</li>
	<li><code>string forward(int steps)</code>&nbsp;Move <code>steps</code> forward in history. If you can only forward <code>x</code> steps in the history and <code>steps &gt; x</code>, you will&nbsp;forward only&nbsp;<code>x</code> steps. Return the current <code>url</code>&nbsp;after forwarding in history <strong>at most</strong> <code>steps</code>.</li>
</ul>

<p>&nbsp;</p>
<p><strong>Example:</strong></p>

<pre><b>Input:</b>
["BrowserHistory","visit","visit","visit","back","back","forward","visit","forward","back","back"]
[["leetcode.com"],["google.com"],["facebook.com"],["youtube.com"],[1],[1],[1],["linkedin.com"],[2],[2],[7]]
<b>Output:</b>
[null,null,null,null,"facebook.com","google.com","facebook.com",null,"linkedin.com","google.com","leetcode.com"]

<b>Explanation:</b>
BrowserHistory browserHistory = new BrowserHistory("leetcode.com");
browserHistory.visit("google.com");       // You are in "leetcode.com". Visit "google.com"
browserHistory.visit("facebook.com");     // You are in "google.com". Visit "facebook.com"
browserHistory.visit("youtube.com");      // You are in "facebook.com". Visit "youtube.com"
browserHistory.back(1);                   // You are in "youtube.com", move back to "facebook.com" return "facebook.com"
browserHistory.back(1);                   // You are in "facebook.com", move back to "google.com" return "google.com"
browserHistory.forward(1);                // You are in "google.com", move forward to "facebook.com" return "facebook.com"
browserHistory.visit("linkedin.com");     // You are in "facebook.com". Visit "linkedin.com"
browserHistory.forward(2);                // You are in "linkedin.com", you cannot move forward any steps.
browserHistory.back(2);                   // You are in "linkedin.com", move back two steps to "facebook.com" then to "google.com". return "google.com"
browserHistory.back(7);                   // You are in "google.com", you can move back only one step to "leetcode.com". return "leetcode.com"
</pre>

<p>&nbsp;</p>
<p><strong>Constraints:</strong></p>

<ul>
	<li><code>1 &lt;= homepage.length &lt;= 20</code></li>
	<li><code>1 &lt;= url.length &lt;= 20</code></li>
	<li><code>1 &lt;= steps &lt;= 100</code></li>
	<li><code>homepage</code> and <code>url</code> consist of&nbsp; '.' or lower case English letters.</li>
	<li>At most <code>5000</code>&nbsp;calls will be made to <code>visit</code>, <code>back</code>, and <code>forward</code>.</li>
</ul>

**Related Topics**:  
[Design](https://leetcode.com/tag/design/)

## Solution 1. Simulation

```java
class BrowserHistory {
    private List<String> history;
    private int currentIndex;

    public BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        currentIndex = 0;
    }

    public void visit(String url) {
        // Remove forward history
        while (history.size() > currentIndex + 1) {
            history.remove(history.size() - 1);
        }
        history.add(url);
        currentIndex++;
    }

    public String back(int steps) {
        currentIndex = Math.max(0, currentIndex - steps);
        return history.get(currentIndex);
    }

    public String forward(int steps) {
        currentIndex = Math.min(history.size() - 1, currentIndex + steps);
        return history.get(currentIndex);
    }
}

```

## Solution 2.  

Instead of removing entries (which is expensive in an ArrayList), we can:  

Keep the full history list.  

Track a currentIndex (current page) and a lastIndex (last valid page in history).  

When we visit(), we overwrite or append new values and move currentIndex and lastIndex forward.  

Ignore anything after lastIndex — it becomes inaccessible just like deleted forward history.  

```java
class BrowserHistory {
    private List<String> history;
    private int currentIndex;
    private int lastIndex;

    public BrowserHistory(String homepage) {
        history = new ArrayList<>();
        history.add(homepage);
        currentIndex = 0;
        lastIndex = 0;
    }

    public void visit(String url) {
        currentIndex++;
        if (currentIndex < history.size()) {
            history.set(currentIndex, url); // overwrite forward history
        } else {
            history.add(url); // append new entry
        }
        lastIndex = currentIndex; // discard forward history logically
    }

    public String back(int steps) {
        currentIndex = Math.max(0, currentIndex - steps);
        return history.get(currentIndex);
    }

    public String forward(int steps) {
        currentIndex = Math.min(lastIndex, currentIndex + steps);
        return history.get(currentIndex);
    }
}

```
