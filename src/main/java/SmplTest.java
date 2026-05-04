import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmplTest {
  ;
    public static void main(String[] args) throws IOException {
        int[] arr = {0, 1, 1,1,1,1,1,1, 0, 0, 0, 0, 0};

        int maxNo=arr[0];
        int maxCount=1;
        int currentCount=1;

        for (int i=1;i<arr.length; i++)
        {
            if(arr[i]==arr[i-1]){
                currentCount++;
            }
            else {
                currentCount=1;
            }

            if (maxCount<currentCount){
                maxCount=currentCount;
                maxNo= arr[i];
            }
        }
        System.out.println(maxNo);
        System.out.println(maxCount);
    }
}
















