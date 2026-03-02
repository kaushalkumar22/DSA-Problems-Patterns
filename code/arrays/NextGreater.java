package code.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
//The next greater element of some element x in an array is the first greater element that is to the right of x in the same array.

//You are given two distinct 0-indexed integer arrays nums1 and nums2, where nums1 is a subset of nums2.

//Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
//Output: [-1,3,-1]
public class NextGreater {
    // Example run
    public static void main(String[] args) {
        NextGreater solution = new NextGreater();
        int[] nums1 = {4, 1, 2};
        int[] nums2 = {1, 3, 4, 2,5};

        int[] result = solution.nextGreaterElement(nums1, nums2);
        System.out.println(Arrays.toString(result)); // [-1, 3, -1]
    }

        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            // Map to store next greater element for each num in nums2
            Map<Integer, Integer> nextGreater = new HashMap<>();
            Stack<Integer> stack = new Stack<>();

            for (int num : nums2) {
                // While current number is greater than the stack's top,
                // it's the next greater for stack's top
                while (!stack.isEmpty() && stack.peek() < num) {
                    nextGreater.put(stack.pop(), num);
                }
                stack.push(num);
            }

            // Remaining numbers in stack have no next greater element
            while (!stack.isEmpty()) {
                nextGreater.put(stack.pop(), -1);
            }

            // Build result for nums1
            int[] result = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                result[i] = nextGreater.get(nums1[i]);
            }

            return result;
        }

    }
