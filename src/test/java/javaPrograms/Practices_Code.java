package javaPrograms;

import java.util.ArrayList;
import java.util.Arrays;

public class Practices_Code {

    public static void main(String[] args) {

        int[] intArr = {1, 3, 2, 5, 0, 85, 9, 32, 89};
        int large = intArr[0];
        int small = intArr[1];

        for (int i : intArr){

            if (i>large){
                small= i;
                large=i;
            } else if (i>small) {
                small=i;
            }

        }
        System.out.println(large);
        System.out.println(small);

    }
}
