package javaPrograms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    }
}
