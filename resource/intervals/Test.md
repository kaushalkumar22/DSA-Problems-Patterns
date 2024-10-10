
# Line Sweep Algorithms

**Line Sweep** (or **Sweep Line**) is an algorithmic technique that involves sweeping an imaginary line across a plane (either the x-axis or y-axis) 
to solve various problems. Events (entries or exits) trigger updates to our data, allowing us to compute results efficiently.

### List of Problems
[Explore Problems on LeetCode](https://leetcode.com/problem-list/mzw3cyy6/)

This post is divided into three parts:
1. **1D Easy/Medium Problems**
2. **1D Hard Problems**
3. **2D Geometric Problems**
---

### 1D Easy/Medium Problems

**[1854. Maximum Population Year](https://leetcode.com/problems/maximum-population-year/) [Easy]**
   - **Problem:** Given the birth and death years of individuals, treat this data as a number line. Increment the population when a person is born and decrement when they die. 
   - **Approach:** Sweep from left to right, keeping track of the current population. Update the global maximum whenever the current population exceeds it.
   - **Time Complexity:** O(n log n)

   **Example Code:**
   ```java
  // 1854. Maximum Population Year [Easy]
    public int maximumPopulation(int[][] logs) {
        Map<Integer, Integer> line = new TreeMap<>();
        for (int[] log : logs) {
            line.put(log[0], line.getOrDefault(log[0], 0) + 1);
            line.put(log[1], line.getOrDefault(log[1], 0) - 1);
        }
        
        int maxPopulation = 0;
        int year = 0;
        int population = 0;

        for (Map.Entry<Integer, Integer> entry : line.entrySet()) {
            population += entry.getValue();
            if (population > maxPopulation) {
                maxPopulation = population;
                year = entry.getKey();
            }
        }
        return year;
    }
   ```
**[2848. Points That Intersect With Cars](https://leetcode.com/problems/points-that-intersect-with-cars/)**

```java
 int numberOfPoints(vector<vector<int>>& nums) {
    int line [102] ={};
    for(auto& p : nums){
        line[p[0]]++;
        line[p[1]+1]--;
    }
    int ans =0;
    int count =0;
    for(int i =0; i <102; ++i){
        count += line[i];
        if(count > 0){
            ++ans;
        }
    }
    return ans;
}
```

**[253. Meeting Rooms II](https://leetcode.com/problems/meeting-rooms-ii/) [Medium]**
   - **Problem:** Track the maximum number of rooms required for meetings. Increment when a meeting starts (+1) and decrement when it ends (-1).
   - **Time Complexity:** O(n log n)

   ```java
  // 253. Meeting Rooms II [Medium]
    public int minMeetingRooms(Interval[] intervals) {
        Map<Integer, Integer> line = new TreeMap<>();
        for (Interval i : intervals) {
            line.put(i.start, line.getOrDefault(i.start, 0) + 1);
            line.put(i.end, line.getOrDefault(i.end, 0) - 1);
        }
        
        int maxRooms = 0;
        int count = 0;

        for (Map.Entry<Integer, Integer> entry : line.entrySet()) {
            count += entry.getValue();
            maxRooms = Math.max(maxRooms, count);
        }
        return maxRooms;
    }

   ```

**[731. My Calendar II](https://leetcode.com/problems/my-calendar-ii/) [Medium]**
   - **Problem:** Check for triple bookings. If any time has a count >= 3, return false.
   - **Time Complexity:** O(n² * log n)

   ```java
  // 731. My Calendar II [Medium]
    private Map<Integer, Integer> m = new HashMap<>();
    public boolean book(int start, int end) {
        m.put(start, m.getOrDefault(start, 0) + 1);
        m.put(end, m.getOrDefault(end, 0) - 1);
        int count = 0;
        
        for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
            count += entry.getValue();
            if (count >= 3) {
                // revert changes
                m.put(start, m.get(start) - 1);
                m.put(end, m.get(end) + 1);
                return false;
            }
        }
        return true;
    }
   ```

**[2237. Count Positions on Street With Required Brightness](https://leetcode.com/problems/count-positions-on-street-with-required-brightness/) [Medium]**
   - **Problem:** Fill counts with +1 and -1 for brightness levels. Scan to check if the threshold is met.
   - **Time Complexity:** O(n)

   ```java
 // 2237. Count Positions on Street With Required Brightness [Medium]
    public boolean isCovered(int[][] ranges, int left, int right) {
        int[] line = new int[52];
        for (int[] r : ranges) {
            line[r[0]] += 1;
            line[r[1] + 1] -= 1;
        }

        int overlaps = 0;
        for (int i = 1; i <= 51; i++) {
            overlaps += line[i];
            if (i >= left && i <= right && overlaps <= 0) {
                return false;
            }
        }
        return true;
    }
   ```

**[1893. Check if All the Integers in a Range Are Covered](https://leetcode.com/problems/check-if-all-the-integers-in-a-range-are-covered/) [Easy]**
   - **Problem:** Mark start and end (end+1) as +1/-1, then check overlaps.
   - **Time Complexity:** O(n)
```java
 // 1893. Check if All the Integers in a Range Are Covered [Easy]
    public boolean isCovered(int[][] ranges, int left, int right) {
        int[] line = new int[52];
        for (int[] r : ranges) {
            line[r[0]] += 1;
            line[r[1] + 1] -= 1;
        }

        int overlaps = 0;
        for (int i = 1; i <= 50; i++) {
            overlaps += line[i];
            if (i >= left && i <= right && overlaps <= 0) {
                return false;
            }
        }
        return true;
    }
```
7. **[370. Range Addition](https://leetcode.com/problems/range-addition/) [Medium]**
   - **Problem:** Accumulate updates in the range using +1 at the start and -1 at end + 1.
   - **Time Complexity:** O(n)
```java
 // 370. Range Addition [Medium]
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] result = new int[length];
        for (int[] update : updates) {
            result[update[0]] += update[2];
            if (update[1] + 1 < length) {
                result[update[1] + 1] -= update[2];
            }
        }

        for (int i = 1; i < length; i++) {
            result[i] += result[i - 1];
        }
        return result;
    }
```

**[1094. Car Pooling](https://leetcode.com/problems/car-pooling/)**
   - **Problem:** Mark pickups (+) and drop-offs (-) on the x-axis. If at any point the sum exceeds capacity, return false.
```java
 // 1094. Car Pooling
    public boolean carPooling(int[][] trips, int capacity) {
        int[] line = new int[1001];
        for (int[] trip : trips) {
            line[trip[1]] += trip[0];
            line[trip[2]] -= trip[0];
        }

        int currentPassengers = 0;
        for (int i = 0; i < line.length; i++) {
            currentPassengers += line[i];
            if (currentPassengers > capacity) {
                return false;
            }
        }
        return true;
    }
```

### Type 2 Problems Explanation

In Type 2 problems, you often don't have the luxury of plotting points on a graph to find the solution. Instead, you have to rely on a previous technique, typically involving sorting and a greedy approach. Let’s consider two examples:

**[452. Minimum Number of Arrows to Burst Balloons](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/) [Medium]**

**Problem Statement**: Given a list of intervals representing balloons, each defined by a start and end point, you need to determine the minimum number of arrows required to burst all balloons. 

![image](https://assets.leetcode.com/users/images/73811d36-8aea-4576-b945-797c86573561_1689758547.3407717.png)

```java

 In this kind of problem, we dont use marking on axis, instead axis is already given, we sort it.
Now if we strike first ballon , we can strike at the end-most point i.e. 2
We save this as prev = 2
Any ballon starting before we dont need extra arraow since this arrow is enough to burst the ballon.
The moment start point > prev , we know we need a new ballon again we save the end-most point in the prev and repeat the process.
Many problem can be solved using this technique.
import java.util.Arrays;

class Solution {
    public int findMinArrowShots(int[][] points) {
        if (points.length == 0) return 0;

        // Sort the intervals by their end time
        Arrays.sort(points, (a, b) -> Integer.compare(a[1], b[1]));
        
        int prevEnd = points[0][1]; // end of the first balloon
        int arrows = 1; // at least one arrow needed
        
        for (int i = 1; i < points.length; i++) {
            // If the start of the current balloon is greater than prevEnd, we need a new arrow
            if (points[i][0] > prevEnd) {
                arrows++;
                prevEnd = points[i][1]; // Update prevEnd to the end of the current balloon
            }
        }
        
        return arrows;
    }
}
```

[435. Non-overlapping Intervals](https://leetcode.com/problems/non-overlapping-intervals/)

Why Sorting with endTimes Works
In many of the problem above we do sorting with endTime , this is an important concept to understand,
if you sort with endTime and then check other intervals you can easily find non-overlapping intervals like this, here prev is previous interval.

if(intervals[i][0] < intervals[prev][1])

Reason is if a new interval start is before previous end time that means a sure overlap.
While on the other hand if we sort by startTime, we dont know when this interval gonna end, there will be overlaps.
Here are some additional points to consider:

Sorting by end time is a greedy algorithm. This means that it makes the best possible choice at each step, without considering the future. As a result, it is usually more efficient than sorting by start time.
Sorting by start time is a dynamic programming algorithm. This means that it makes a choice at each step, based on the choices that it has made in the past. As a result, it is usually more robust to errors in the input data.
For practice try to solve this problem , which can be solved in both DP as well as Greey Way and see the difference

```java
import java.util.Arrays;

class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals.length == 0) return 0;

        // Sort intervals by their end times
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));
        
        int prevEnd = intervals[0][1]; // end of the first interval
        int removals = 0; // count of intervals to remove
        
        for (int i = 1; i < intervals.length; i++) {
            // If the start of the current interval is less than the end of the previous one, it overlaps
            if (intervals[i][0] < prevEnd) {
                removals++; // We need to remove this interval
            } else {
                prevEnd = intervals[i][1]; // Update prevEnd to the end of the current interval
            }
        }
        
        return removals;
    }
}
```



[646. Maximum Length of Pair Chain](https://leetcode.com/problems/maximum-length-of-pair-chain/)

**Problem:** Given a set of pairs, find the length of the longest chain that can be formed by these pairs.

**Solution Explanation:**
- **Sorting:** We sort the pairs by their end values.
- **Greedy Approach:** We maintain a count of chains and track the end of the last added pair:
  - For each pair, if its first value is greater than the end of the last pair in the chain, we can include it in the chain.
  - We then update the end to the second value of the current pair.
- The final count represents the maximum length of the chain.

```java
public int findLongestChain(int[][] pairs) {
    Arrays.sort(pairs, (a, b) -> a[1] - b[1]); // Sort by end values
    int count = 0; // To count the length of the chain
    int end = Integer.MIN_VALUE; // Track the end of the last added pair
    for (int[] pair : pairs) {
        if (pair[0] > end) { // Can include this pair
            count++;
            end = pair[1]; // Update end time
        }
    }
    return count; // Return length of the longest chain
}
```

---

[252. Meeting Rooms](https://leetcode.com/problems/meeting-rooms/)

**Problem:** Determine if a person can attend all meetings given their time intervals.

**Solution Explanation:**
- **Sorting:** Sort the intervals by their start times.
- **Checking for Overlap:** Iterate through the sorted intervals, checking if the start time of the current interval is less than the end time of the previous interval.
- If an overlap is detected, return false. If no overlaps are found, return true.

```java
public boolean canAttendMeetings(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // Sort by start times
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < intervals[i - 1][1]) { // Overlap detected
            return false;
        }
    }
    return true; // No overlaps found
}
```

---

[57. Insert Interval](https://leetcode.com/problems/insert-interval/)

**Problem:** Given a set of non-overlapping intervals, insert a new interval into the intervals and merge if necessary.

**Solution Explanation:**
- **Iterate Through Intervals:** For each interval:
  - If it ends before the new interval starts, add it to the result.
  - If it starts after the new interval ends, add the new interval first and then the current interval.
  - If they overlap, update the new interval to encompass both intervals.
- Finally, add the last new interval to the result.

```java
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> ans = new ArrayList<>();
    for (int[] interval : intervals) {
        if (interval[1] < newInterval[0]) { // Before new interval
            ans.add(interval);
        } else if (newInterval[1] < interval[0]) { // After new interval
            ans.add(newInterval);
            newInterval = interval; // Update new interval to current
        } else { // Overlapping intervals
            newInterval[0] = Math.min(newInterval[0], interval[0]);
            newInterval[1] = Math.max(newInterval[1], interval[1]);
        }
    }
    ans.add(newInterval); // Add the last new interval
    return ans.toArray(new int[ans.size()][]);
}
```

---

[1272. Remove Interval](https://leetcode.com/problems/remove-interval/)

**Problem:** Given a set of intervals and a target interval to remove, return the remaining intervals.

**Solution Explanation:**
- **Iterate Through Intervals:** For each interval:
  - If it ends before the target interval starts or starts after it ends, add it to the result.
  - If it overlaps, split it into two parts: before and after the target interval if applicable.
- Return the updated list of intervals.

```java
public int[][] removeInterval(int[][] intervals, int[] toBeRemoved) {
    List<int[]> ans = new ArrayList<>();
    for (int[] interval : intervals) {
        if (toBeRemoved[1] <= interval[0]) { // Before target
            ans.add(interval);
        } else if (toBeRemoved[0] >= interval[1]) { // After target
            ans.add(interval);
        } else { // Overlapping intervals
            if (interval[0] < toBeRemoved[0]) {
                ans.add(new int[]{interval[0], toBeRemoved[0]}); // Before target
            }
            if (interval[1] > toBeRemoved[1]) {
                ans.add(new int[]{toBeRemoved[1], interval[1]}); // After target
            }
        }
    }
    return ans.toArray(new int[ans.size()][]);
}
```

---

[1229. Meeting Scheduler](https://leetcode.com/problems/meeting-scheduler/)

**Problem:** Given two sets of meeting time slots, find the earliest time slot that can accommodate a meeting of a specified duration.

**Solution Explanation:**
- **Sorting:** Sort both sets of intervals by their start times.
- **Two-Pointer Technique:** Use two pointers to iterate through the slots:
  - Compare the start of the current slot of both sets to find overlaps.
  - If there is an overlap of sufficient duration, return that time slot.
- Move the pointer of the set that ends first.

```java
public int[] minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
    Arrays.sort(slots1, (a, b) -> a[0] - b[0]); // Sort by start times
    Arrays.sort(slots2, (a, b) -> a[0] - b[0]); // Sort by start times
    int i = 0, j = 0; // Pointers for both slots
    while (i < slots1.length && j < slots2.length) {
        int start = Math.max(slots1[i][0], slots2[j][0]);
        int end = Math.min(slots1[i][1], slots2[j][1]);
        if (end - start >= duration) { // Found a suitable time slot
            return new int[]{start, start + duration};
        }
        if (slots1[i][1] < slots2[j][1]) {
            i++; // Move pointer in slots1
        } else {
            j++; // Move pointer in slots2
        }
    }
    return new int[]{}; // No suitable time found
}
```

---

[1288. Remove Covered Intervals](https://leetcode.com/problems/remove-covered-intervals/)

**Problem:** Given a set of intervals, remove all intervals that are covered by another interval.

**Solution Explanation:**
- **Sorting:** Sort the intervals based on their start time; if the start times are equal, sort by end time in ascending order.
- **Counting:** Track the count of remaining intervals and the end of the last interval added:
  - For each interval, check if it is covered by the last added interval.
  - If it is not covered, update the end and increment the count.
- Return the count of non-covered intervals.

```java
public int removeCoveredIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]); // Sort intervals
    int count = 0; // Count remaining intervals
    int end = 0; // Track end of last added interval
    for (int[] interval : intervals) {
        if (interval[1] > end) { // Not covered
            count++;
            end = interval[1]; // Update end
        }
    }
    return count;

 // Return count of non-covered intervals
}
```

---

[1353. Maximum Number of Events That Can Be Attended](https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/)

**Problem:** Given a list of events with start and end times, find the maximum number of events a person can attend.

**Solution Explanation:**
- **Sorting:** Sort the events by start times.
- **Priority Queue:** Use a min-heap (priority queue) to track end times:
  - Iterate through each day and add events that start on that day to the queue.
  - Attend one event per day if possible, and remove any past events from the queue.
- Return the count of attended events.

```java
public int maxEvents(int[][] events) {
    Arrays.sort(events, (a, b) -> a[0] - b[0]); // Sort events by start times
    PriorityQueue<Integer> pq = new PriorityQueue<>(); // Min-heap for end times
    int count = 0; // Count of attended events
    int i = 0, n = events.length; // Index for events
    for (int day = 1; day <= 100000; day++) {
        while (i < n && events[i][0] == day) { // Add events starting today
            pq.offer(events[i][1]);
            i++;
        }
        if (!pq.isEmpty()) { // Attend an event
            pq.poll(); // Remove the event from the queue
            count++;
        }
        while (!pq.isEmpty() && pq.peek() < day) { // Remove past events
            pq.poll();
        }
    }
    return count; // Return count of attended events
}
```

---

[56. Merge Intervals](https://leetcode.com/problems/merge-intervals/)

**Problem:** Given a collection of intervals, merge all overlapping intervals.

**Solution Explanation:**
- **Sorting:** Sort the intervals by start times.
- **Merging:** Iterate through the sorted intervals:
  - If the current interval overlaps with the last merged interval, update the end of the last interval.
  - If it does not overlap, add it to the result.
- Finally, return the merged intervals.

```java
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // Sort by start times
    List<int[]> merged = new ArrayList<>(); // To store merged intervals
    for (int[] interval : intervals) {
        if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
            merged.add(interval); // No overlap, add the interval
        } else {
            merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]); // Merge intervals
        }
    }
    return merged.toArray(new int[merged.size()][]); // Return merged intervals
}
```
Sure! Here are the LeetCode problems you've mentioned, along with the Java solutions that include detailed comments explaining the logic:

[1589. Maximum Sum Obtained of Any Permutation (Medium)](https://leetcode.com/problems/maximum-sum-obtained-of-any-permutation/)

```java
class Solution {
    public int maxSumObtained(int[] nums, int[][] segments) {
        // Sort segments based on their starting index
        Arrays.sort(segments, Comparator.comparingInt(a -> a[0]));
        
        // Max heap to keep track of the maximum values in segments
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int currentIndex = 0;
        int totalSum = 0;

        // To get the maximum value in the segments
        for (int[] segment : segments) {
            // Add all values from nums to the maxHeap that are in the range [currentIndex, segment[0])
            while (currentIndex < segment[0]) {
                maxHeap.offer(nums[currentIndex]);
                currentIndex++;
            }
            // Add the maximum number from maxHeap to totalSum
            totalSum += maxHeap.poll();
            // Now add the segment's ending index to the heap
            for (int j = segment[0]; j <= segment[1]; j++) {
                maxHeap.offer(nums[j]);
            }
            // Update currentIndex to the end of the current segment
            currentIndex = segment[1] + 1;
        }

        // Add any remaining numbers to the sum
        while (currentIndex < nums.length) {
            maxHeap.offer(nums[currentIndex]);
            currentIndex++;
        }
        
        // Final accumulation of maximum numbers
        while (!maxHeap.isEmpty()) {
            totalSum += maxHeap.poll();
        }

        return totalSum;
    }
}
```

[1943. Describe the Painting (Medium)](https://leetcode.com/problems/describe-the-painting/)

```java
class Solution {
    public int[] describePainting(int[][] paint) {
        int[] result = new int[2]; // result array to hold the area and total paint used
        Map<Integer, Integer> lineMap = new TreeMap<>();

        // Create events for the start and end of each painting
        for (int[] p : paint) {
            lineMap.put(p[0], lineMap.getOrDefault(p[0], 0) + 1); // start of painting
            lineMap.put(p[1], lineMap.getOrDefault(p[1], 0) - 1); // end of painting
        }

        int activePaints = 0;
        int lastPosition = -1;
        int area = 0;

        // Sweep line through the sorted events
        for (Map.Entry<Integer, Integer> entry : lineMap.entrySet()) {
            int position = entry.getKey();
            if (activePaints > 0 && lastPosition != -1) {
                area += position - lastPosition; // accumulate area
            }
            activePaints += entry.getValue(); // update the count of active paintings
            lastPosition = position; // update last position
        }

        result[0] = area; // total area
        result[1] = paint.length; // total number of paintings
        return result;
    }
}
```

[1674. Minimum Moves to Make Array Complementary (Medium)](https://leetcode.com/problems/minimum-moves-to-make-array-complementary/)

```java
class Solution {
    public int minMoves(int[] nums) {
        Arrays.sort(nums); // Sort the array to facilitate calculations
        int n = nums.length;
        int moves = 0;
        int minMoves = 0;

        // Calculate the minimum moves needed
        for (int i = 0; i < n / 2; i++) {
            moves += nums[n - 1 - i] - nums[i]; // accumulate the difference
        }

        return moves; // return the total moves needed
    }
}
```

[2158. Amount of New Area Painted Each Day (Hard)](https://leetcode.com/problems/amount-of-new-area-painted-each-day/)

```java
class Solution {
    public int[] amountPainted(int[][] paint) {
        // Sort events by starting and ending positions
        List<int[]> events = new ArrayList<>();
        for (int[] p : paint) {
            events.add(new int[]{p[0], 1}); // start of paint
            events.add(new int[]{p[1], -1}); // end of paint
        }
        Collections.sort(events, Comparator.comparingInt(a -> a[0]));

        int area = 0;
        int[] res = new int[paint.length];
        int curr = 0; // Current position in the painting
        TreeMap<Integer, Integer> activeIntervals = new TreeMap<>(); // Map to track active intervals
        
        // Process events
        for (int i = 0; i < events.size(); i++) {
            int[] e = events.get(i);
            int pos = e[0];
            int type = e[1];
            if (curr < pos) {
                // Calculate the area covered by active intervals
                int coveredArea = 0;
                for (Map.Entry<Integer, Integer> entry : activeIntervals.entrySet()) {
                    if (entry.getValue() > 0) {
                        coveredArea += entry.getValue(); // accumulate area
                    }
                }
                area += pos - curr - coveredArea; // update total area
                curr = pos; // Move current position to event position
            }
            // Update the active intervals
            if (type == 1) {
                activeIntervals.put(pos, activeIntervals.getOrDefault(pos, 0) + 1); // Add interval
            } else {
                activeIntervals.put(pos, activeIntervals.getOrDefault(pos, 0) - 1); // Remove interval
            }
            res[i / 2] = area; // Store area for each day
        }

        return res; // Return the result
    }
}
```

[2251. Number of Flowers in Full Bloom (Hard)](https://leetcode.com/problems/number-of-flowers-in-full-bloom/)
```java
class Solution {
    public int[] fullBloomFlowers(int[][] flowers, int[] people) {
        List<int[]> events = new ArrayList<>();
        for (int[] flower : flowers) {
            events.add(new int[]{flower[0], 1}); // Start of bloom
            events.add(new int[]{flower[1], -1}); // End of bloom
        }

        Collections.sort(events, Comparator.comparingInt(a -> a[0])); // Sort by position
        int[] result = new int[people.length];
        Arrays.sort(people); // Sort queries
        
        int flowerCount = 0;
        int j = 0;

        // Sweep line through events
        for (int person : people) {
            while (j < events.size() && events.get(j)[0] <= person) {
                flowerCount += events.get(j)[1]; // Update bloom count
                j++;
            }
            result[person] = flowerCount; // Store current count
        }

        return result; // Return result
    }
}
```

[732. My Calendar III (Hard)](https://leetcode.com/problems/my-calendar-iii/)
```java
class MyCalendarThree {
    private Map<Integer, Integer> timeline;

    public MyCalendarThree() {
        timeline = new HashMap<>(); // To track the timeline of events
    }

    public int book(int start, int end) {
        timeline.put(start, timeline.getOrDefault(start, 0) + 1); // Increment count for start
        timeline.put(end, timeline.getOrDefault(end, 0) - 1); // Decrement count for end

        int ongoingEvents = 0;
        int maxEvents = 0;

        // Calculate the maximum number of overlapping events
        for (int count : timeline.values()) {
            ongoingEvents += count;
            maxEvents = Math.max(maxEvents, ongoingEvents); // Update maximum
        }

        return maxEvents; // Return the maximum overlapping events
    }
}
```

[759.Employee Free Time](https://leetcode.com/problems/employee-free-time/)
     
```java
Same conepts, mark start and end time on line, when will everyone is free i.e. when count is 0.
So whenever count is 0 , it mark the begining of an interval, also set a flag , so that after we non-zero from 0, we use that as closing interval.

	map<int, int> line;
        for(auto& s : schedule){
            for(auto& i :  s){
                ++line[i.start];
                --line[i.end];
            }
        }
        
        int count = 0;
        bool found = false;
        vector<Interval> ans;
        for(auto&x : line){
            count += x.second;
            if(found){
                ans.back().end = x.first;
                found = false;
            }
            if(count == 0){
                // mark begining of new interval
                ans.push_back(Interval(x.first, -1));
                found = true;
            }
        }
        ans.pop_back();
        return ans;
```

[391. Perfect Rectangle (Hard)](https://leetcode.com/problems/perfect-rectangle/)



On Similar lines to above problem, two point to note.

    Here we have to calculate exact cover which means, two rectangle cant intersect, lets understand this with an image.

![image](https://user-images.githubusercontent.com/20656683/174115549-d6ade836-d27c-407c-8731-9d10481df2ab.png)
![image](https://user-images.githubusercontent.com/20656683/174115717-12eacdd8-726a-405b-bd91-d84b5c4434cc.png)
![image](https://user-images.githubusercontent.com/20656683/174115644-e4b581d0-dacd-479f-b9c8-5a13f060950e.png)

Point to be noted that new rectangle higher y-cordinate should be lower than equal to existing active rectangles ( new rectangle is beneath) or new rectangle lower y-cordinate should be greater than equal to to existing active rectangles ( new rectangle is above).

    Sum of Height of the active rectangle should always be exactly (ymax-ymin) else there would be hole and it wont be exact cover.

![image](https://user-images.githubusercontent.com/20656683/174116557-64e7fd04-40e1-48ad-b92b-79f8a8849332.png)

Here ymax is 3 and ymin is 0 , so everytime y height sum should be exactly 3 only then we would have exact cover.
But in above example, sum of both y height is 2 and 2!=3 and hence return false.
Thanks @wddd for his solution https://leetcode.com/problems/perfect-rectangle/discuss/87188/on-log-n-sweep-line-solution

```java
class Solution {
    public boolean isRectangleCover(int[][] rectangles) {
        Set<String> corners = new HashSet<>();
        int area = 0;
        int x1 = Integer.MAX_VALUE, y1 = Integer.MAX_VALUE, x2 = Integer.MIN_VALUE, y2 = Integer.MIN_VALUE;

        for (int[] rect : rectangles) {
            // Calculate area and update boundaries
            area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
            x1 = Math.min(x1, rect[0]);
            y1 = Math.min(y1, rect[1]);
            x2 = Math.max(x2, rect[2]);
            y2 = Math.max(y2, rect[3]);

            // Add corners to the set
            String[] cornersToAdd = {rect[0] + "," + rect[1], rect[2] + "," + rect[3],
                                      rect[0] + "," + rect[3], rect[2] + "," + rect[1]};
            for (String corner : cornersToAdd) {
                if (!corners.add(corner)) {
                    corners.remove(corner); // Remove if already present (not unique)
                }
            }
        }

        // Check if we have exactly 4

[218. The Skyline Problem](https://leetcode.com/problems/the-skyline-problem/)

```java
    Why do we use priority-queue(min-heap): Reason is we have to take decision whether to add skyline contour or not at the given x-cordinate.
    So we try to pull out all the events for a given x , insert/delete as per event type and then decide whether to make contour or not.

    Why do we use multiset : Because multiple box of same height exist, if one box is removed that doesnt mean other box can be removed, hence multiset.

Logic: Before inserting height into multiset we note the maximum height available (thats why i used negative number in multiset).
After processing of all events for a given x co-ordinate, check if the height changed ? if yes we have a contout here, otherwise skip.

class Solution {
public:
    vector<vector<int>> getSkyline(vector<vector<int>>& buildings) {
        vector<vector<int>> ans;
        multiset<int> height;
        
        priority_queue<vector<int>, vector<vector<int>>, greater<vector<int>>> line; // min-heap
        for(auto& b : buildings){
            line.push({b[0], 0, b[2]});
            line.push({b[1], 1, b[2]});
        }
        while(!line.empty()){
            int before = height.empty() ? 0 : -*(begin(height));
            int x;
            do{
                x = line.top()[0];
                int event = line.top()[1];
                int yheight = line.top()[2];
                line.pop();    
                if(event)
                    height.erase(height.find(-yheight));
                else
                    height.insert(-yheight);
            }while(!line.empty() and line.top()[0] == x);
            
            int after = height.empty() ? 0 : -*(begin(height));
            
            if(after != before)
                ans.push_back({x, after});
        }
        return ans;
    }
};	

```

If you noticed in all above 3 problem, template remain same.

    Store the events in either priority queue or vector in sorted manner of x-axis.
    priority queue approach has an advantage of not worrying whether to keep entry event first or exit event first becauase your are popping out
    all events for same x-cordinate in one go and then deciding what to be done but if you use vector to store the interval and use custom comparator,
    you have to be careful about whether to add exit event first or entry event first because we pull event one by one.
    See Skyline problem for priroity queue approach and Rectangle Area II for vector appraoch.
    Use multiset of process of y-axis. This multiset can store either line or rectangle, depending on what problem is asking for.

    Closest pair of points.
    Lines Intersection.

