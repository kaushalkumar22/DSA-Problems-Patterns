package dsa.stacks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class CustomStack {
    public static void main(String[] args) {
        String s ="U 3 U 3 U 5 O O U 7 O X";
        String str = "a3A";
        char[] cr = str.toCharArray();

        Arrays.sort(cr);
        System.out.println(new String(cr));
        Stack<String> st = new Stack<>();
        String[] strs = s.split(" ");
        List<Integer> res =new ArrayList<>();
        for(int i = 0 ;i<strs.length ;i++){
            if(strs[i].equals("U")){
                st.push(strs[i+1]);
            }else if(strs[i].equals("O")){
                res.add(Integer.valueOf(st.pop()));
            } else if(strs[i].equals("X")){
                break;
            }

        }

        System.out.println(Arrays.toString(res.stream().toArray()));
    }
}
