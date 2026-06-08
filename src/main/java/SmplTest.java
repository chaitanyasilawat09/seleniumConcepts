import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SmplTest {
  ;
    public static void main(String[] args) throws IOException {
        int[] i = {1, 2, 3, 4, 5};
//      2  1,2,3
//        4/5  4,5
//        3
        int key = 1;
        List<Integer> list = Arrays.stream(i).boxed().collect(Collectors.toList());

        int avg = list.get(i.length / 2);
        System.out.println(avg);
        if (avg > key && list.contains(key)) {
            System.out.println(list.subList(0, avg));
        } else if (avg < key && list.contains(key)) {
            System.out.println(list.subList(avg, list.size()));
        } else {
            System.out.println("-1");
        }

    }
}
















