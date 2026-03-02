package code.heaps;

import java.util.Arrays;
import java.util.PriorityQueue;

public class KClosestPointsToOrigin {
    public static void main(String[] args) {

    }
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->Integer.compare(dist(b),dist(a)));
        for(int[] point: points){
            pq.add(point);
            if (pq.size()>k){
                pq.poll();
            }
        }
        return pq.stream().toArray(int[][]::new);
    }
    private int dist(int[] A){
        return A[0]*A[0] + A[1]*A[1];
    }
}