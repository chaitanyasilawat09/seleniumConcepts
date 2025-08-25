package javaPrograms;

public class InterviewCode_WRT_Integers {

    public static void main(String[] args) {

//        no_Even_Odd(10);
//        System.out.println(no_Prime_or_not(9));
//        fibonacciSeries(5);
//        swap_No_Without_Third_No();
//        System.out.println(factorial(50));
//        reverce_No(1414);
//        Armstrong_no_Check(370);
//        no_Is_Palindrome();
        sum_of_Digit_Of_Given_No();

    }

    public static void no_Even_Odd(int no) {
        if (no % 2 == 0) {
            System.out.println("no. is even");
        } else
            System.out.println("no is odd");
    }

    public static boolean no_Prime_or_not(int no) {
        boolean primeNo = false;
        for (int i = 2; i <= no / 2; i++) {
            if (no % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void fibonacciSeries(int no) {
        int first = 0;
        int second = 1;
        int finalNo;
        for (int i = 0; i <= no; i++) {
            System.out.println(first);
            finalNo = first + second;
            first = second;
            second = finalNo;
        }
    }

    public static void swap_No_Without_Third_No() {

        int a = 10;
        int b = 20;
        a = a + b; //30
        b = a - b;// 10
        a = a - b;// 20
        System.out.println(a + " " + b);
    }

    public static long factorial(long n) {
        if (n == 0 || n == 1)
            return 1;
        return n * factorial(n - 1);
    }

    public static int reverce_No(int no) {

        int reverse = 0;
        while (no != 0) {
            int reminder = no % 10;
            reverse = reverse * 10 + reminder;
            no = no / 10;
        }
        System.out.println("reverse no is :-" + reverse);
        return reverse;
    }

    public static void Armstrong_no_Check(int no) {
        int actualNo = no;
        int sum = 0;
        int length = String.valueOf(no).length();
        while (no > 0) {
            int digit = no % 10;
            sum += (int) Math.pow(digit, length);
            no = no / 10;
        }
        if (sum == actualNo) {
            System.out.println("No is Armstrong");
        } else {
            System.out.println("No is not Armstrong");
        }

    }


    public static void no_Is_Palindrome() {
        int no = 123431;
        int actualNo = no;
        int reverse = 0;
        while (no != 0) {
            int reminder = no % 10;
            reverse = reverse * 10 + reminder;
            no = no / 10;
        }
        if (actualNo == reverse)
            System.out.println("no is Palindrom");
        else
            System.out.println("No is not palindrom");

    }

    public static void sum_of_Digit_Of_Given_No() {

        int no = 12343;
        int a = 0, b = 0;
        while (no != 0) {
            a = no % 10;
            b = b + a;
            no = no / 10;
        }
        System.out.println(b);
    }
}


