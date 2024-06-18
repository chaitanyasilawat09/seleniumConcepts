package Test_Concept;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class myClass{


    public static void main(String[] args) {
        int test = 12345;
        int[] testArray = Integer.toString(test).chars().map(c -> c-'0').toArray();
        System.out.println(Arrays.stream(testArray).toArray());
        System.out.println(Arrays.stream(testArray).toArray());
    }


//
//    {
//      int no =1234567;
//      int reverse = 0;
//      while (no!=0) {
//          int reminder = no % 10;
//          reverse = reverse * 10 + reminder;
//          no = no / 10;
//          System.out.println(no);
//      }
//        System.out.println(reverse);
//    }
}
