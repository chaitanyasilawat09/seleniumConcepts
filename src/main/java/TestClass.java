import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestClass {

    public static void main(String[] args) {
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

}