package code.intervals;

import java.util.Arrays;

/**
 * Meeting Rooms
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * determine if a person could attend all meetings.
 * Example 1:
 * Input: [[0,30],[5,10],[15,20]]
 * Output: false
 * Example 2:
 * Input: [[7,10],[2,4]]
 * Output: true
 */
public class MeetingRooms {
    public static void main(String[] args) {
        //int[][] intervals = {{0,30},{5,10},{15,20}};
        int[][] intervals = {{ 7,10},{2,4}};
        System.out.println(canAttendMeetings(intervals));
    }

    public static boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals,(a,b)->Integer.compare(a[0],b[0]));
        int[] overlap = intervals[0];
        for(int i = 1;i<intervals.length;i++){
            int[] curr = intervals[i];
            if(overlap[1]>=curr[0]){
                return false;
            }
            overlap = curr;
        }
        return true;
    }
}

class Solution {
    public boolean canAttendMeetings(int[][] intervals) {
        // Step 1: Sort meetings by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // Step 2: Compare each meeting with the previous one
        for (int i = 1; i < intervals.length; i++) {
            // Overlap condition: current start < previous end
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false;
            }
        }
        return true; // No overlaps found
    }
}
