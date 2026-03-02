package code.aa_practice;

public class Zigzag {
    public static void main(String[] args) {
        System.out.println(convert("PAYPALISHIRING", 3));  // Output: PAHNAPLSIIGYIR
    }

    public static String convert(String s, int n) {
        if (n == 1 || s.length() <= n) return s;

        StringBuilder[] sbs = new StringBuilder[n];
        for (int i = 0; i < n; i++) {
            sbs[i] = new StringBuilder();
        }

        int row = 0;
        boolean goingDown = false;

        for (int i = 0; i < s.length(); i++) {
            sbs[row].append(s.charAt(i));

            // Change direction at top or bottom
            if (row == 0 || row == n - 1) {
                goingDown = !goingDown;
            }

            row += goingDown ? 1 : -1;
        }

        StringBuilder res = new StringBuilder();
        for (int j = 0; j < n; j++) {
            res.append(sbs[j]);
        }

        return res.toString();
    }
}
