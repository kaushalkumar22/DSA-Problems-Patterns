package code.backtracking;

import java.util.*;

public class LetterCasePermutation {
    public static void main(String[] args) {
        String s = "a1b2";
        System.out.println(letterCasePermutation(s));
    }

    public static List<String> letterCasePermutation(String s) {
        List<String> res = new ArrayList<>();
        dfs(s.toCharArray(), 0, res);
        return res;
    }

    private static void dfs(char[] arr, int index, List<String> res) {
        if (index == arr.length) {
            res.add(new String(arr));
            return;
        }

        if (Character.isLetter(arr[index])) {
            // Lowercase path
            arr[index] = Character.toLowerCase(arr[index]);
            dfs(arr, index + 1, res);

            // Uppercase path
            arr[index] = Character.toUpperCase(arr[index]);
            dfs(arr, index + 1, res);
        } else {
            // Digit, continue without branching
            dfs(arr, index + 1, res);
        }
    }
}
