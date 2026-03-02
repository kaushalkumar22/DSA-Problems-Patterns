package code.heaps;

import java.util.PriorityQueue;

public class FindMedianFromDataStream {
    public static void main(String[] args) {
    }

}
class MedianFinder {

    PriorityQueue<Integer> left;   // max-heap
    PriorityQueue<Integer> right;  // min-heap

    public MedianFinder() {
        left = new PriorityQueue<>((a, b) -> b - a);
        right = new PriorityQueue<>();
    }

    public void addNum(int num) {
        // 1. add to left
        left.add(num);

        // 2. move max of left to right
        right.add(left.poll());

        // 3. balance (left can have 1 extra)
        if (left.size() < right.size()) {
            left.add(right.poll());
        }
    }

    public double findMedian() {
        if (left.size() > right.size()) {
            return left.peek(); // odd count
        }
        return (left.peek() + right.peek()) / 2.0; // even count
    }
}
