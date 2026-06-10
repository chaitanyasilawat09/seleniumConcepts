package javaPrograms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Conversion {
    public static void main(String[] args) {

        int[] array2 = {4, 5, 6, 7, 8};
        // TODO Convert int[] to ArrayList<Integer>
        ArrayList<Integer> list = Arrays.stream(array2)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));


        //TODO
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);
        List<Integer> list3 = new ArrayList<>(Arrays.asList(2, 0, 1, 3, 4));


        //
        int test = 12345;
        int[] testArray = Integer.toString(test).chars().map(c -> c-'0').toArray();


        //Max value from list
        List<Integer> listq = new ArrayList<>(Arrays.asList(1,4,7,8,9,14,2));
        Optional<Integer> in = listq.stream().max(Integer::compareTo);
        int i = list.stream().max(Integer::compare).get();


        //Max value from Array
        int [] st = {1,3,4,3,2,6,15};
        Arrays.sort(st);
        System.out.println(st[st.length-1]);

    }
}
