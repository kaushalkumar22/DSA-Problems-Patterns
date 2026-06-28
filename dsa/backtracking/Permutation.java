package dsa.backtracking;

import java.util.*;

public class Permutation {
    public static void main(String[] args) {
        String s = "aa3A";

        Map<Character, Integer> freq = new TreeMap<>(); // sorted order
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }

        List<String> res = new ArrayList<>();
        dfs(res, new StringBuilder(), freq, s.length());

        System.out.println(res);
    }

    private static void dfs(List<String> res, StringBuilder sb,
                            Map<Character, Integer> freq, int n) {

        if (sb.length() == n) {
            res.add(sb.toString());
            return;
        }

        for (char c : freq.keySet()) { // already sorted
            if (freq.get(c) == 0) continue;

            sb.append(c);
            freq.put(c, freq.get(c) - 1);

            dfs(res, sb, freq, n);

            // backtrack
            sb.deleteCharAt(sb.length() - 1);
            freq.put(c, freq.get(c) + 1);
        }
    }
}
