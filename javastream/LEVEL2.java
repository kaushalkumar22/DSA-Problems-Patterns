package javastream;

import java.util.List;
import java.util.stream.Collectors;

public class LEVEL2 {
    public static void main(String[] args) {
        List<Integer> list = List.of(1, 2, 3, 4,2);
        List<String> strs = List.of("abc", "def","ad","","asdfghj");
        //11. Distinct elements
        list.stream().distinct().toList();
        list.stream().distinct().collect(Collectors.toList());
        //12. Sum using
        int sum = list.stream().mapToInt(Integer::intValue).sum();
        //13. Find max
        int max1 = list.stream().mapToInt(Integer::intValue).max().getAsInt();
        int val = list.stream().max(Integer::compareTo).get();
        //14. Find min
        int min =  list.stream().mapToInt(Integer::intValue).min().getAsInt();
        int min1 = list.stream().min(Integer::compareTo).get();
        //15. Check if any match “xyz”
        strs.stream().filter(s->s.contains("xyx")).toList();
        // 16. Count frequency of each word
        strs.stream().collect(Collectors.groupingBy(s->s,Collectors.counting()));
        // 17. Filter only unique using frequency
        // 18. Reverse sort by length
        // 19. Convert Map<K,V> → List of Strings “K=V”
        // 20. Multiply each element by 5
    }
}
