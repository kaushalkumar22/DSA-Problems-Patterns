package code.aa_practice;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringMerger {
    public static void main(String[] args) {
        List<String> parts = List.of("gh", "abc", "cde", "efgh");
        System.out.println(mergeLongest(parts, "a")); // Output: abcdefgh
    }
    public static String mergeLongest(List<String> parts, String startWith) {
        // Find the starting string
        String current = parts.stream()
                .filter(s -> s.startsWith(startWith))
                .findFirst()
                .orElse("");

        if (current.isEmpty()) return "";

        List<String> remaining = new ArrayList<>(parts);
        remaining.remove(current);

        boolean matchFound = true;
        while (matchFound) {
            matchFound = false;
            for (int i = 0; i < remaining.size(); i++) {
                String next = remaining.get(i);
                int overlap = getOverlap(current, next);

                if (overlap > 0) {
                    // Merge and remove from pool
                    current += next.substring(overlap);
                    remaining.remove(i);
                    matchFound = true;
                    break; // Restart search with the new merged string
                }
            }
        }
        return current;
    }

    private static int getOverlap(String s1, String s2) {
        int maxOverlap = Math.min(s1.length(), s2.length());
        for (int len = maxOverlap; len > 0; len--) {
            if (s1.endsWith(s2.substring(0, len))) {
                return len;
            }
        }
        return 0;
    }
}

 class OptimizedStringMerge {

    public static String mergeStrings(List<String> parts, String startWith) {
        int n = parts.size();

        // Step 1: Precompute overlaps
        int[][] overlap = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    overlap[i][j] = getOverlap(parts.get(i), parts.get(j));
                }
            }
        }

        int[][] dp = new int[1 << n][n];
        int[][] parent = new int[1 << n][n];

        for (int[] row : dp) Arrays.fill(row, -1);

        // Step 2: Initialize with valid starting strings
        for (int i = 0; i < n; i++) {
            if (parts.get(i).startsWith(startWith)) {
                dp[1 << i][i] = 0;
            }
        }

        // Step 3: DP transitions
        for (int mask = 0; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                if (dp[mask][i] == -1) continue;

                for (int j = 0; j < n; j++) {
                    if ((mask & (1 << j)) != 0) continue;

                    int nextMask = mask | (1 << j);
                    int val = dp[mask][i] + overlap[i][j];

                    if (val > dp[nextMask][j]) {
                        dp[nextMask][j] = val;
                        parent[nextMask][j] = i;
                    }
                }
            }
        }

        // Step 4: Find best result
        int bestMask = 0, last = -1, maxOverlap = -1;

        for (int mask = 0; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                if (dp[mask][i] > maxOverlap) {
                    maxOverlap = dp[mask][i];
                    bestMask = mask;
                    last = i;
                }
            }
        }

        // Step 5: Reconstruct path
        List<Integer> path = new ArrayList<>();
        int mask = bestMask;

        while (mask > 0) {
            path.add(last);
            int temp = parent[mask][last];
            mask ^= (1 << last);
            last = temp;
        }

        Collections.reverse(path);

        // Step 6: Build final string
        String result = parts.get(path.get(0));

        for (int k = 1; k < path.size(); k++) {
            int i = path.get(k - 1);
            int j = path.get(k);
            int ov = overlap[i][j];
            result += parts.get(j).substring(ov);
        }

        return result;
    }

    private static int getOverlap(String a, String b) {
        int max = Math.min(a.length(), b.length());

        for (int len = max; len > 0; len--) {
            if (a.endsWith(b.substring(0, len))) {
                return len;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        List<String> parts = List.of("gh", "abc", "cde", "efgh");
        System.out.println(mergeStrings(parts, "a")); // abcdefg
    }
}