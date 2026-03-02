package code.intervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertInterval {
    public static void main(String[] args) {
        int[][] intervals = { {1,2}, {3,5}, {6,7}, {8,10}, {12,16} };
        int[] newInterval = {4,8};
        insert(intervals, newInterval);
    }
    public static int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> res = new ArrayList<>();
        Arrays.sort(intervals,(a,b)->Integer.compare(a[0],b[0]));
        for(int[] interval : intervals){
            if(interval[1]<newInterval[0]){
                res.add(interval);
            }else if (newInterval[1]<interval[0]){
                res.add(newInterval);
                newInterval = interval;
            }else{
                newInterval[0] = Math.min(newInterval[0],interval[0]);
                newInterval[1] = Math.max(newInterval[1],interval[1]);
            }
        }
        res.add(newInterval);
        for(int[] r : res){
            System.out.println(r[0]+ " val " +r[1]);
        }
        return res.toArray(new int[res.size()][2]);
    }
}
/*
Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

You may assume that the intervals were initially sorted according to their start times.

Example 1:

Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
Example 2:

Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method signature.
 */