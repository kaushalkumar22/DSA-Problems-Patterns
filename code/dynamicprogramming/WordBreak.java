package code.dynamicprogramming;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordBreak {

    public static void main(String[] args) {
       // String s = "leetcode"; wordDict = []

        wordBreak("leetcode", Arrays.asList("leet","code"));
    }
    public static boolean wordBreak(String s, List<String> wordDict) {
        Set<String> dic = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n+1];
        dp[0] = true;
       for(int i = 0 ;i<n;i++){
           for (int j = i+1 ;j<=n;j++){
               String str = s.substring(i,j);
               if(dic.contains(str) && dp[i]){
                   dp[j] = true;
               }
           }
       }
       System.out.println(dp[n]);
       return dp[n];
    }
}
