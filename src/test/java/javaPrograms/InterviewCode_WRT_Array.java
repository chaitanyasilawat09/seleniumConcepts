package javaPrograms;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterviewCode_WRT_Array {

    @Test
    public void common_Element_In_2_Array() {

        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> list2 = Arrays.asList(3, 1, 6, 8, 9);

        //TODO Common element
        list2.stream()
                .filter(a -> list1.contains(a))
                .collect(Collectors.toList())
                .forEach(System.out::println);

//        TODO Unique element in two list
        list1
                .addAll(list2
                        .stream()
                        .filter(a -> !list1.contains(a))
                        .collect(Collectors.toList()));
        System.out.println(list1);
    }

    @Test
    public void sort_an_Array() {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 0, 1, 3, 4));
        for (int i = 0; i < list.size(); i++) {

            for (int j = 0; j < list.size(); j++) {
                if (list.get(i) < list.get(j)) {
                    Integer tem = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, tem);
                }
            }
        }
        System.out.println(list);
    }

    @Test
    public void remove_duplicate_from_array() {
        int[] array = {5, 2, 9, 1, 6, 2, 5};
        List<Integer> newList = new ArrayList<>();
        List<Integer> list = new ArrayList<>(Arrays.stream(array).boxed().collect(Collectors.toList()));
        list.stream()
                .filter(a ->
                {
                    if (!newList.contains(a))
                        newList.add(a);
                    return false;
                })
                .collect(Collectors.toList());
        System.out.println(newList);
    }

    @Test
    public void find_missing_no() {
        int[] array = {1, 2, 4, 5, 6, 9}; // Missing number is 3
        Arrays.sort(array);
        int maxNo = array[array.length - 1];
        List<Integer> list = new ArrayList<>(Arrays.stream(array).boxed().collect(Collectors.toList()));
        for (int i = 1; i < maxNo; i++) {
            if (!list.contains(i)) {
                System.out.println(i);
            }
        }
        System.out.println(list);
    }

    @Test
    public void find_Large_and_Small_From_Array() {

        int[] intArr = {1, 3, 2, 5, 0, 85, 9, 32, 89};
        int small = intArr[0];
        int large = intArr[1];

        for (int i : intArr) {

            if (i > large) {
                large = i;
            }
            if (i < small) {
                small = i;
            }
        }
        System.out.println(small);
        System.out.println(large);
    }

    @Test
    public void find_larget_and_second_largest_From_Array() {

        int[] intArr = {1, 3, 2, 5, 0, 85, 9, 32, 89};
        int large = intArr[0];
        int secondLarge = intArr[1];
        for (int i : intArr) {
            if (i > large) {
                secondLarge = large;
                large = i;
            } else {
                if (i > secondLarge) {
                    secondLarge = i;
                }
            }
        }
        System.out.println(large);
        System.out.println(secondLarge);
    }

    @Test
    public void search_Element_in_Array() {

        int[] intArr = {1, 3, 2, 5, 0, 85, 9, 32, 89};
        int target = 32;
        int index = IntStream.range(0, intArr.length)
                .filter(i -> intArr[i] == target)
                .findFirst().orElse(-1);
        System.out.println(index);
    }

    @Test
    public void sum_of_Integers_from_Array() {
        String[] strArr = {"1", "3", "a", "4", "#", "12"};
        int sum = 0;
        for (String str : strArr) {
            try {
                int i = Integer.parseInt(str);
                sum += i;
            } catch (NumberFormatException e) {

            }
        }
        System.out.println(sum);
    }

    @Test
    public void get_non_repetitive_no(){

        int [] array = {1,1,2,9};
        Map<Integer, Integer> sotredMap = new HashMap<>();
        for (int i : array){
            if (sotredMap.containsKey(i)){
               sotredMap.put(i,sotredMap.get(i).intValue()+1);
            }
            else {
                sotredMap.put(i,1);
            }
        }
        System.out.println(sotredMap);
       for (Map.Entry<Integer,Integer> entryMap : sotredMap.entrySet()){
           if (entryMap.getValue()==1)
           {
               System.out.println(entryMap.getKey());
           }
       }

    }

}
