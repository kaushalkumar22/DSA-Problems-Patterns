package javastream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) {

        //1. Filter even numbers
        List<Integer> nums = List.of(1, 2, 3, 4,2);
        List<Integer> num = nums.stream().filter(i->i%2==0).toList();
        System.out.println(num);
        //convert list to array
        System.out.println( nums.toArray()[0]);

        //2. Convert strings to uppercase
        List<String> strs = List.of("abc", "def","ad","","asdfghj");
       // strs =  strs.stream().map(s->s.toUpperCase()).toList();
        strs =  strs.stream().map(String::toUpperCase).toList();//better
        System.out.println(strs);
        List<Integer> lengths = strs.stream()
                        .map(String::length)
                        .collect(Collectors.toList());

        List<Integer> lengths1 =  strs.stream().map(String::length).toList();//better
        System.out.println(lengths1);
        //3. Get lengths of each string
        Map<String,Integer> strvlLen = strs.stream().collect(Collectors.toMap(s->s,String::length));
        System.out.println(strvlLen);

        // Case 3: Get Map<Integer, Long> (length → count)
        Map<Integer,Long> lenVSCount = strs.stream().collect(Collectors.groupingBy(String::length,Collectors.counting()));
        System.out.println(lenVSCount);

        //4. Square each number
        List<Integer> square = nums.stream().map(i->i*i).toList();
        System.out.println(square);
        //5. Remove empty strings
        List<String> empty  = strs.stream().filter(s->!s.isEmpty()).toList();
        List<String> nonEmpty = strs.stream()
                .filter(String::isEmpty)
                .toList();

        List<String> empty1  = strs.stream().filter(s->!s.isEmpty()).collect(Collectors.toList());

        System.out.println(empty);//
        System.out.println(empty1);//mutable

        //6. Sort numbers
        List<Integer> sort = nums.stream().sorted((a,b)->Integer.compare(b,a)).toList();
        System.out.println(sort);

        //7. Sort strings by length
        List<String> sortByLen = strs.stream().sorted(Comparator.comparingInt(String::length)).toList();
        System.out.println(sortByLen);
        List<String> sortByLenRev = strs.stream().sorted(Comparator.comparingInt(String::length).reversed()).toList();

        System.out.println(sortByLenRev);
        //8. Count strings starting with “a”
        long countstr = strs.stream().filter(a->a.startsWith("A")).count();
        System.out.println(countstr);
        //9. Convert list → set
        Set<String> set = strs.stream().collect(Collectors.toSet());
        System.out.println(set);
        //10. Join strings with comma
    }
}
