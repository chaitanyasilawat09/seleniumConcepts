package javaPrograms;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InterviewCode_WRT_Array {

/*
* */

    @Test
    public void common_Element_In_2_Array() {

        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> list2 = Arrays.asList(3, 1, 6, 8, 9);

        System.out.println(
                Stream.concat(list1.stream(),list2.stream())
                        .filter(a-> ( list1.contains(a) && list2.contains(a) ) )
                        .distinct()
                        .collect(Collectors.toList())
        );

        System.out.println(
                Stream.concat(list1.stream(),list2.stream())
                        .filter(a-> (!( list1.contains(a) && list2.contains(a) )))
                        .collect(Collectors.toList())
        );
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
       // List<Integer> list = new ArrayList<>(Arrays.asList(2,2,3,5,3,6,5));

        for (int i :array){
            if (!newList.contains(i)){
                newList.add(i);
            }
            else {
                System.out.println("Duplicate : "+i);
            }
        }
//                       TODO OR
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
    public void find_SecondLarge_and_second_small_Value()
    {
        int[] ik = {0, 2, 1, 4};

        int large = Integer.MIN_VALUE;
        int large2 = Integer.MIN_VALUE;

        int small = Integer.MAX_VALUE;
        int small2 = Integer.MAX_VALUE;

        for (int k : ik) {

            // largest two
            if (k > large) {
                large2 = large;
                large = k;
            } else if (k > large2) {
                large2 = k;
            }

            // smallest two
            if (k < small) {
                small2 = small;
                small = k;
            } else if (k < small2) {
                small2 = k;
            }
        }

        System.out.println("Largest = " + large);
        System.out.println("Second Largest = " + large2);
        System.out.println("Smallest = " + small);
        System.out.println("Second Smallest = " + small2);
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

//       TODO OR
        for (int i: array){
            if(IntStream.range(0,array.length)
                    .filter(a-> array[a]==i)
                    .boxed()
                    .collect(Collectors.toList()).size()>1)
                System.out.println("duplicate No is :-"+ i);


        }
    }

    public static void find_Continious_Accurency_Of_Integer_In_Array() {
        int[] arr = {0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0};

        int maxCount = 1;
        int currentCount = 1;
        int maxElement = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                currentCount++;
            } else {
                currentCount = 1;
            }

            if (currentCount > maxCount) {
                maxCount = currentCount;
                maxElement = arr[i];
            }
        }
        System.out.println("Number " + maxElement + " occurs " + maxCount + " times continuously.");
    }


    //       //2,4,3,6,0
//        ArrayList<Integer> list = new ArrayList(Arrays.asList(2,4,3,6,0));
////        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0,0,1,0,0));
///
        @Test
        public void getMiddleValueFromLetAndRightSum(){
        ArrayList<Integer> list = new ArrayList(Arrays.asList(2,4,3,6,0));
//        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(2,4,3,6,3));

        int totalSum = 0;
        int leftSum = 0;
        for (int num : list) {
            totalSum += num;
        }
        for (int i = 0; i < list.size(); i++) {
            totalSum = totalSum - list.get(i);   // right sum
            if (leftSum == totalSum) {
                System.out.println("Equilibrium element: " + list.get(i));
                return;
            }
            leftSum = leftSum + list.get(i);
        }
    }

    public void sortWithout_Collection(){int[] arr = {5,2,8,1};

        for(int i=0;i<arr.length;i++){
            for(int j=i+1;j<arr.length;j++){
                if(arr[i] > arr[j]){
                    int temp = arr[i];
                    arr[i]=arr[j];
                    arr[j]=temp;
                }
            }
        }
                }

    public static void maximumSubarraySum_Kadane() {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        int maxSoFar = arr[0];
        int macValue = arr[0];

        for (int i = 1; i < arr.length; i++) {
            macValue = Math.max(arr[i], macValue + arr[i]);
            maxSoFar = Math.max(maxSoFar, macValue);
        }
        System.out.println(maxSoFar);
    }



        public static void maximumSubarraySum_Kadane1(String[] args) {
        int[] arr = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
//        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
        int max = arr[0];
        int maxValue = arr[0];

        ArrayList<Integer> list = new ArrayList<>(Arrays.stream(arr).boxed().collect(Collectors.toList()));

        for (int i = 0;i<list.size();i++){
            for (int j =i+1;j<= list.size();j++){
                int k =    list.subList(i,j)
                        .stream()
                        .mapToInt(a-> a).sum();
                if (k>max){
                    System.out.println( list.subList(i,j) +"---->"+ k);
                    max = k;
                }
            }
        }
    }

    @Test
    public void findSecondHighestSalary() {
        List<Integer> salaries = Arrays.asList(50000, 60000, 45000, 70000, 55000);

        Integer secondHighest = salaries.stream()
                .distinct()
                .sorted(Comparator.reverseOrder())
                .skip(1)
                .findFirst()
                .orElse(null);

        System.out.println("Salaries: " + salaries);
        System.out.println("Second highest: " + secondHighest);
        // Output: 60000
    }



}
