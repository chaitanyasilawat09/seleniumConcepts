package javaPrograms;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Common_And_Distinct_Value_In_2_Different_List {

    public static void main(String[] args) {

        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);

//        System.out.println(
//                list1.stream()
//                        .filter(list2::contains)
//                        .distinct().collect(Collectors.toList())
//        );
        System.out.println(
        Stream.concat(list1.stream(),list2.stream())
                .filter(e-> !(list1.contains(e)&&list2.contains(e)))
                .collect(Collectors.toList())
         );
        System.out.println("..."+
                Stream.concat(list1.stream(),list2.stream())
                        .filter(e-> (list1.contains(e)&&list2.contains(e)))
                        .distinct()
                        .collect(Collectors.toList())
        );
    }

}

