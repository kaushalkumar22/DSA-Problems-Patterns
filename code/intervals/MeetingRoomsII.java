package code.intervals;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class MeetingRoomsII {
    public static void main(String[] args) {
        int[][] intervals = {{0, 30}, {5, 10}, {15, 20}};
        //Input: intervals = [(0,40),(5,10),(15,20)]
        Arrays.sort(intervals,(a,b)->Integer.compare(a[0],b[0]));
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b)->Integer.compare(b[0],a[0]));
        pq.add(intervals[0]);
        int count =1;
        for(int i =1;i<intervals.length;i++){
            int[] interval = intervals[i];
            if(pq.peek()[1]>interval[0]){
                count++;
                pq.add(interval);
            }else{
                pq.add(interval);
            }

        }
        System.out.println(count);

        // Output: 2
        //int[][] intervals = {{ 7,10},{2,4}};
        // System.out.println(minMeetingRooms(intervals));
        //System.out.println(minMeetingRooms1(intervals));
    }
}

