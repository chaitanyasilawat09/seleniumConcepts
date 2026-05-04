package Test_Concept;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TestCode {
    static{
        System.out.println("hello static");
        System.out.println("hello static");
        System.out.println("hello static");
        System.out.println("hello static");
        System.out.println("hello static");
    }

    public static void main(String[] args) {
        int [] arInt = {1,3,2,4,5,6,5,7,80,0,6,4,7,8,9,061,45,76,45,67};

        System.out.println(Arrays.stream(arInt).boxed().collect(Collectors.toList())
                .stream().reduce((a,b) -> a<b? a:b)
                .stream().collect(Collectors.toList()));
    }
}
