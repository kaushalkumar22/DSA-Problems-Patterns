package code.dynamicprogramming;

public class DecodeWays {
    //Input: s = "226"

    //Output: 3
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n];
        dp[0] =s.charAt(0)=='0' ?0:1;
        for(int i =1;i<n;i++){
            char c1 = s.charAt(i);
            char c2 = s.charAt(i-1);
            if((c2=='1' || c2 =='2') && (c1>='1' && c1<='6')){
                dp[i] = dp[i-1]+1;
            }else{
                dp[i] = dp[i-1];
            }
        }
     return dp[n-1];
    }
}
