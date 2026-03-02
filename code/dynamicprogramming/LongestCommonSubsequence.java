package code.dynamicprogramming;

public class LongestCommonSubsequence {
    public static void main(String[] args) {
        String t1= "abc", t2 = "afhbsca";
        longestCommonSubsequence( t1,  t2);
    }
    //Input: t1 = "abcde", t2 = "ace"
   // Output: 3
    //Input: text1 = "abc", text2 = "afhbsca"
    public static int longestCommonSubsequence(String t1, String t2) {
       int m = t1.length();
       int n = t2.length();
       int[][] dp = new int[m+1][n+1];
       //base either is empty then longest is zero
        for(int i = 1 ;i<=m;i++){
            for(int j = 1 ;j<=n;j++){
                if(t1.charAt(i-1)==t2.charAt(j-1)){
                    dp[i][j] = 1+dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }
        System.out.println(dp[m][n]);
        return dp[m][n];
    }
}
