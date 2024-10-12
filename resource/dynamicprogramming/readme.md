# Dynamic Programming (DP)

## 1. Fibonacci Sequence

[image:](https://substackcdn.com/image/fetch/w_1456,c_limit,f_webp,q_auto:good,fl_progressive:steep/https%3A%2F%2Fsubstack-post-media.s3.amazonaws.com%2Fpublic%2Fimages%2F2e6c90e0-e1ab-4295-92ae-e84dfa311088_1768x1652.png)

The Fibonacci sequence pattern is useful when the solution to a problem depends on the solutions of smaller instances of the same problem.

There is a clear recursive relationship, often resembling the classic Fibonacci sequence F(n) = F(n-1) + F(n-2).

1. **[70. Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)**
2. **[509. Fibonacci Number](https://leetcode.com/problems/fibonacci-number/)**
3. **[746. Min Cost Climbing Stairs](https://leetcode.com/problems/min-cost-climbing-stairs/)**
  
## 2. Kadane's Algorithm

Kadane's Algorithm is primarily used for solving the Maximum Subarray Problem and its variations where the problem asks to optimize a contiguous subarray within a one-dimensional numeric array

1. **[53. Maximum Subarray](https://leetcode.com/problems/maximum-subarray/)**
2. **[918. Maximum Sum Circular Subarray](https://leetcode.com/problems/maximum-sum-circular-subarray/)**
3. **[152. Maximum Product Subarray](https://leetcode.com/problems/maximum-product-subarray/)**

---

## 3. 0/1 Knapsack

The 0/1 Knapsack pattern is useful when:

    You have a set of items, each with a weight and a value.

    You need to select a subset of these items.

    There's a constraint on the total weight (or some other resource) you can use.

    You want to maximize (or minimize) the total value of the selected items.

    Each item can be chosen only once (hence the "0/1" - you either take it or you don't).

1. **[Count Subsets with a Difference of K](https://leetcode.com/problems/target-sum/)**
2. **[Count Subsets Sum](https://leetcode.com/problems/partition-equal-subset-sum/)**
3. **[0/1 Knapsack Problem](https://leetcode.com/problems/0-1-knapsack-problem/)**
4. **[Minimum Subset Sum Difference](https://leetcode.com/problems/partition-equal-subset-sum/)**
5. **[Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)**
6. **[Subset Sum](https://leetcode.com/problems/subset-sum/)**
7. **[Target Sum](https://leetcode.com/problems/target-sum/)**
8. **[1049. Last Stone Weight II](https://leetcode.com/problems/last-stone-weight-ii/)**

---

## 4. Unbounded Knapsack

The Unbounded Knapsack pattern is useful when:

    You have a set of items, each with a weight and a value.

    You need to select items to maximize total value.

    There's a constraint on the total weight (or some other resource) you can use.

    You can select each item multiple times (unlike 0/1 Knapsack where each item can be chosen only once).

    The supply of each item is considered infinite.

1. **[322. Coin Change](https://leetcode.com/problems/coin-change/)**
2. **[518. Coin Change II](https://leetcode.com/problems/coin-change-ii/)**
3. **[377. Combination Sum IV](https://leetcode.com/problems/combination-sum-iv/)**
4. **[322. Knapsack Problem (Unbounded)](https://leetcode.com/problems/coin-change/)**
5. **[279. Perfect Squares](https://leetcode.com/problems/perfect-squares/)**

---

## 5. Longest Common Subsequence (LCS)

The Longest Common Subsequence pattern is useful when you are given two sequences and need to find a subsequence that appears in the same order in both the given sequences.
1. **[1143. Longest Common Subsequence](https://leetcode.com/problems/longest-common-subsequence/)**
2. **[583. Delete Operation for Two Strings](https://leetcode.com/problems/delete-operation-for-two-strings/)**
3. **[1092. Shortest Common Supersequence](https://leetcode.com/problems/shortest-common-supersequence/)**
4. **[115. Distinct Subsequences](https://leetcode.com/problems/distinct-subsequences/)**
5. **[72. Edit Distance](https://leetcode.com/problems/edit-distance/)**
6. **[5. Longest Palindromic Substring](https://leetcode.com/problems/longest-palindromic-substring/)**
7. **[516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/)**
8. **[1143. Longest Common Substring](https://leetcode.com/problems/longest-common-substring/)**
9. **[1143. Longest Repeating Subsequence](https://leetcode.com/problems/longest-repeating-subsequence/)**
10. **[1456. Maximum Length of Repeated Subarray](https://leetcode.com/problems/maximum-length-of-repeated-subarray/)**
11. **[1213. Minimum Deletions in a String to Make It a Palindrome](https://leetcode.com/problems/minimum-deletions-in-a-string-to-make-it-a-palindrome/)**
12. **[1312. Minimum Insertion Steps to Make a String Palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/)**
13. **[127. Minimum Number of Deletion in a String to Make It a Palindrome](https://leetcode.com/problems/minimum-number-of-deletions-in-a-string-to-make-it-a-palindrome/)**
14. **[1092. Minimum Number of Insertion and Deletion to Convert String A to B](https://leetcode.com/problems/minimum-number-of-insertion-and-deletion-to-convert-string-a-to-b/)**
15. **[Print Longest Common Subsequence](https://leetcode.com/problems/print-longest-common-subsequence/)**
16. **[1092. Shortest Common Supersequence](https://leetcode.com/problems/shortest-common-supersequence/)**
17. **[97. Interleaving Strings](https://leetcode.com/problems/interleaving-string/)**
18. **[1035. Uncrossed Lines](https://leetcode.com/problems/uncrossed-lines/)**

---

## 6. Longest Increasing Subsequence (LIS)

The Longest Increasing Subsequence pattern is used to solve problems that involve finding the longest subsequence of elements in a sequence where the elements are in increasing order.

1. **[300. Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)**
2. **[673. Number of Longest Increasing Subsequence](https://leetcode.com/problems/number-of-longest-increasing-subsequence/)**
3. **[354. Russian Doll Envelopes](https://leetcode.com/problems/russian-doll-envelopes/)**
4. **[Building Bridges](https://leetcode.com/problems/building-bridges/)**
5. **[368. Largest Divisible Subset](https://leetcode.com/problems/largest-divisible-subset/)**
6. **[674. Longest Bitonic Subsequence](https://leetcode.com/problems/longest-bitonic-subsequence/)**
7. **[300. Longest Increasing Subsequence](https://leetcode.com/problems/longest-increasing-subsequence/)**
8. **[300. Maximum Sum Increasing Subsequence](https://leetcode.com/problems/maximum-sum-increasing-subsequence/)**
9. **[1048. Minimum Number of Deletions to Make a Sorted Sequence](https://leetcode.com/problems/minimum-number-of-deletions-to-make-a-sorted-sequence/)**
10. **[1611. Minimum Number of Removals to Make Mountain Array](https://leetcode.com/problems/minimum-number-of-removals-to-make-mountain-array/)**
11. **[673. Number of Longest Increasing Subsequence](https://leetcode.com/problems/number-of-longest-increasing-subsequence/)**

---

## 7. Palindromic Subsequence

The Palindromic Subsequence pattern is used when solving problems that involve finding a subsequence within a sequence (usually a string) that reads the same forwards and backwards.
LeetCode Problems:

1. **[516. Longest Palindromic Subsequence](https://leetcode.com/problems/longest-palindromic-subsequence/)**
2. **[647. Palindromic Substrings](https://leetcode.com/problems/palindromic-substrings/)**
3. **[1312. Minimum Insertion Steps to Make a String Palindrome](https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/)**

---

## 8. Edit Distance

The Edit Distance pattern is used to solve problems that involve transforming one sequence (usually a string) into another sequence using a minimum number of operations.

The allowed operations typically include insertion, deletion, and substitution.
LeetCode Problems:

- **[72. Edit Distance](https://leetcode.com/problems/edit-distance/)**
- **[583. Delete Operation for Two Strings](https://leetcode.com/problems/delete-operation-for-two-strings/)**
- **[712. Minimum ASCII Delete Sum for Two Strings](https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/)**

---
## 9. Subset Sum

The Subset Sum pattern is used to solve problems where you need to determine whether a subset of elements from a given set can sum up to a specific target value.

- **[416. Partition Equal Subset Sum](https://leetcode.com/problems/partition-equal-subset-sum/)**
- **[494. Target Sum](https://leetcode.com/problems/target-sum/)**
- **[698. Partition to K Equal Sum Subsets](https://leetcode.com/problems/partition-to-k-equal-sum-subsets/)**

---

## 10. String Partition

The String Partition pattern is used to solve problems that involve partitioning a string into smaller substrings that satisfy certain conditions.

It’s useful when:

    You're working with problems involving strings or sequences.

    The problem requires splitting the string into substrings or subsequences.

    You need to optimize some property over these partitions (e.g., minimize cost, maximize value).

    The solution to the overall problem can be built from solutions to subproblems on smaller substrings.

    There's a need to consider different ways of partitioning the string.

- **[139. Word Break](https://leetcode.com/problems/word-break/)**
- **[132. Palindrome Partitioning II](https://leetcode.com/problems/palindrome-partitioning-ii/)**
- **[472. Concatenated Words](https://leetcode.com/problems/concatenated-words/)**

---

## 11. Catalan Numbers

The Catalan Number pattern is used to solve combinatorial problems that can be decomposed into smaller, similar subproblems.

Some of the use-cases of this pattern include:

    Counting the number of valid parentheses expressions of a given length

    Counting the number of distinct binary search trees that can be formed with n nodes.

    Counting the number of ways to triangulate a polygon with n+2 sides.

- **[96. Unique Binary Search Trees](https://leetcode.com/problems/unique-binary-search-trees/)**
- **[22. Generate Parentheses](https://leetcode.com/problems/generate-parentheses/)**

---

## 12. Matrix Chain Multiplication

This pattern is used to solve problems that involve determining the optimal order of operations to minimize the cost of performing a series of operations.

It is based on the popular optimization problem: Matrix Chain Multiplication.

It’s useful when:

    You're dealing with a sequence of elements that can be combined pairwise.

    The cost of combining elements depends on the order of combination.

    You need to find the optimal way to combine the elements.

    The problem involves minimizing (or maximizing) the cost of operations of combining the elements.

- **[1039. Minimum Score Triangulation of Polygon](https://leetcode.com/problems/minimum-score-triangulation-of-polygon/)**
- **[312. Burst Balloons](https://leetcode.com/problems/burst-balloons/)**
- **[1000. Minimum Cost to Merge Stones](https://leetcode.com/problems/minimum-cost-to-merge-stones/)**

---

## 13. Count Distinct Ways

This pattern is useful when:

    You need to count the number of different ways to achieve a certain goal or reach a particular state.

    The problem involves making a series of choices or steps to reach a target.

    There are multiple valid paths or combinations to reach the solution.

    The problem can be broken down into smaller subproblems with overlapping solutions.

    You're dealing with combinatorial problems that ask "in how many ways" can something be done.

- **[91. Decode Ways](https://leetcode.com/problems/decode-ways/)**
- **[2266. Count Number of Texts](https://leetcode.com/problems/count-number-of-texts/)**

---

## 14. DP on Grids

The DP on Grids pattern is used to solve problems that involve navigating or optimizing paths within a grid (2D array).

For these problems, you need to consider multiple directions of movement (e.g., right, down, diagonal) and solution at each cell depends on the solutions of neighboring cells.

- **[62. Unique Paths](https://leetcode.com/problems/unique-paths/)**
- **[64. Minimum Path Sum](https://leetcode.com/problems/minimum-path-sum/)**
- **[329. Longest Increasing Path in a Matrix](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/)**

---

## 15. DP on Trees

The DP on Trees pattern is useful when:

    You're working with tree-structured data represented by nodes and edges.

    The problem can be broken down into solutions of subproblems that are themselves tree problems.

    The problem requires making decisions at each node that affect its children or parent.

    You need to compute values for nodes based on their children or ancestors.

- **[337. House Robber III](https://leetcode.com/problems/house-robber-iii/)**
- **[124. Binary Tree Maximum Path Sum](https://leetcode.com/problems/binary-tree-maximum-path-sum/)**
- **[968. Binary Tree Cameras](https://leetcode.com/problems/binary-tree-cameras/)**

---

## 16. DP on Graphs

The DP on Graphs pattern is useful when:

    You're dealing with problems involving graph structures.

    The problem requires finding optimal paths, longest paths, cycles, or other optimized properties on graphs.

    You need to compute values for nodes or edges based on their neighbors or connected components.

    The problem involves traversing a graph while maintaining some state.

- **[787. Cheapest Flights Within K Stops](https://leetcode.com/problems/cheapest-flights-within-k-stops/)**
- **[1334. Find the City With the Smallest Number of Neighbors at a Threshold Distance](https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/)**

## 17. Digit DP

The Digit DP Pattern is useful when:

    You're dealing with problems involving counting or summing over a range of numbers.

    The problem requires considering the digits of numbers individually.

    You need to find patterns or properties related to the digits of numbers within a range.

    The range of numbers is large (often up to 10^18 or more), making brute force approaches infeasible.

    The problem involves constraints on the digits.

- **[357. Count Numbers with Unique Digits](https://leetcode.com/problems/count-numbers-with-unique-digits/)**
- **[233. Number of Digit One](https://leetcode.com/problems/number-of-digit-one/)**
- **[902. Numbers At Most N Given Digit Set](https://leetcode.com/problems/numbers-at-most-n-given-digit-set/)**

---

## 18. Bitmasking DP

The Bitmasking DP pattern is used to solve problems that involve a large number of states or combinations, where each state can be efficiently represented using bits in an integer.

It’s particularly useful when:

    You're dealing with problems involving subsets or combinations of elements.

    The total number of elements is relatively small (typically <= 20-30).

    You need to efficiently represent and manipulate sets of elements.

    The problem involves making decisions for each element (include/exclude) or tracking visited/unvisited states.

    You want to optimize space usage in DP solutions.

- **[1986. Minimum Number of Work Sessions to Finish the Tasks](https://leetcode.com/problems/minimum-number-of-work-sessions-to-finish-the-tasks/)**
- **[2305. Fair Distribution of Cookies](https://leetcode.com/problems/fair-distribution-of-cookies/)**
- **[847. Shortest Path Visiting All Nodes](https://leetcode.com/problems/shortest-path-visiting-all-nodes/)**

## 19. Probability DP

This pattern is useful when:

    You're dealing with problems involving probability calculations.

    The probability of a state depends on the probabilities of previous states.

    You need to calculate the expected value of an outcome.

    The problem involves random processes or games of chance.

- **[688. Knight Probability in Chessboard](https://leetcode.com/problems/knight-probability-in-chessboard/)**
- **[808. Soup Servings](https://leetcode.com/problems/soup-servings/)**
- **[837. New 21 Game](https://leetcode.com/problems/new-21-game/)**:

---

## 20. State Machine DP

The State Machine DP Pattern is useful when when:

    The problem can be modeled as a series of states and transitions between these states.

    There are clear rules for moving from one state to another.

    The optimal solution depends on making the best sequence of state transitions.

    The problem involves making decisions that affect future states.

    There's a finite number of possible states, and each state can be clearly defined.

Here are the LeetCode problems you mentioned:

- **[309. Best Time to Buy and Sell Stock with Cooldown](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/)**
- **[123. Best Time to Buy and Sell Stock III](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-iii/)**

---
