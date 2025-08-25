package javaPrograms;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Print_missing_number_in_array {
    public static void main(String[] args) {
        List<Integer> l1 = Arrays.asList(1,3,6,8,19);
        Arrays.sort(l1.toArray());
        int maxNo = l1.get(l1.size()-1);

              List<Integer> missingList = IntStream.range(1,maxNo)
                      .filter(e-> !(l1.contains(e)))
                      .boxed()
                      .collect(Collectors.toList());
    }
}
