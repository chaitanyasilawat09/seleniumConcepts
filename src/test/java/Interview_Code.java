import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interview_Code {


    public static void main(String[] args) {
//        reverseString_At_Same_position();
//        chatCountInStringJava_9();
//        ReverseNumber();
        removeSpecialChar();

    }

    public static void reverseString_At_Same_position(){
        String originalStr = "Chaitanya Silawat Ratlam";
        String words[] = originalStr.split("\\s");
        String reversedString = "";
        for (int i = 0; i < words.length; i++)
        {
            String word = words[i];
            String reverseWord = "";
            for (int j = word.length() - 1; j >= 0; j--) {
                reverseWord = reverseWord + word.charAt(j);
            }
            reversedString = reversedString + reverseWord + " ";
        }

        // Displaying the string after reverse
        System.out.print("Reversed string : " + reversedString);
    }


    public void charCountInString(){
        String s =   "DeliverySolutions";
        char[] ch = s.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (int i =0;i<ch.length;i++){

            if (map.containsKey(ch[i])){
                int value =  map.get(ch[i]).intValue();
                value= value + 1;

                map.put(ch[i],value);
            }
            else {
                map.put(ch[i], 1);
            }
        }
        System.out.println(map);

    }


    public static void chatCountInStringJava_9(){
        String someString =   "DeliverySolutions";
        char[] c = someString.toCharArray();

        Map<Character, Integer> map = new HashMap<>();
        for (int i =0;i<c.length;i++) {
            Integer count = 0;
            Pattern pattern = Pattern.compile(String.valueOf(c[i]));
            Matcher matcher = pattern.matcher(someString);
            while (matcher.find()) {
                count++;

            }
            map.put(c[i],count);
        }
        System.out.println(map);

    }

    @Test
    public void getIntValueFromString(){
        String s="java3selenium5sql6!@#%";
        char[] ch = s.toCharArray();
        int a =0;
        String s1 = "";
        String s2 = "";
        for (int i = 0; i<ch.length; i++){
            if (Character.isDigit(ch[i]))
            {
                a = a + Character.getNumericValue(ch[i]);
            }

            if (Character.isLetter(ch[i]))
            {
                s1 = s1  + ch[i];
            }

            if (!(Character.isLetter(ch[i])) && !(Character.isDigit(ch[i])))
            {
                s2 = s2  + ch[i];
            }
        }
        System.out.println(a);
        System.out.println(s1);
        System.out.println(s2);
    }


    public static void printLargest(){
        int num[] ={ 900,90,6,7,5000,4,60000,20,3};
        int largest = num[0];
        int secondLargest = num[1];
        for (int i=1; i<num.length; i++)
        {
            if(largest < num[i])
            {
                secondLargest = largest;
                largest = num[i];
            }
            else if(secondLargest < num[i]){
                secondLargest = num[i];
            }
        }
        System.out.println("Largest : " +largest);
        System.out.println("Second Largest : "+secondLargest);
    }


    public static void ReverseNumber(){
        int no=123423;

        int reverse =0;
        while (no != 0){

            int reminder = no % 10;
            reverse = reverse * 10 +reminder;
            no = no/10;
        }
        System.out.println(reverse);
    }


    public  static void removeSpecialChar(){
        String str="Cha&%i@%tany#@a";
        String s=str.replaceAll("[^a-zA-Z]", "");
        System.out.println(s);

    }

    @Test
    public  static void printArray_In_Cyclic_Form(){

//        TODO  //For example, if we perform a circular left rotation on an array [1, 2, 3, 4, 5]
//         by a factor of ‘2’ it would be [3, 4, 5, 1, 2]

//        output = [[3, 4, 5, 1, 2]]
        int [] ia= {1, 2, 3, 4, 5};
        System.out.println(ia.length % 2);  //gives reminder
        System.out.println(ia.length / 2);  // gives divisible

        List<Integer> list = new ArrayList<>();
        for (int i =ia.length/2; i<ia.length; i++){
            list.add(ia[i]);
        }
        for (int i =0; i<ia.length/2; i++){
            list.add(ia[i]);
        }
        System.out.println("for /2 != 0");
        System.out.println(list);
    }


    public  static void printArray_In_Cyclic_Form_1(){

        int [] ia= {1, 2, 3, 4, 5};
//        output = [2, 3, 4, 5, 1]
//        output = [3, 4, 5, 1, 2]
        int fac = 2;
        for (int j = 1; j<=fac; j++) {
            List<Integer> list = new ArrayList<>();
            for (int i = j; i < ia.length; i++) {
                list.add(ia[i]);
            }
            for (int i = 0; i < j; i++) {
                list.add(ia[i]);
            }
            System.out.println(list);
        }
    }


    public  void charCountWithInteger() {

        char [] ch =  {'a', 'p', 'p', 'l', 'e'};
        Map<Character, Integer> map = new HashMap<>();
        Map<Character, Integer> charMap = new HashMap<>();
        int count = 1;
        for(char i = 'a'; i<='z'; i++){

            map.put(i, count);
            count++;
        }
        for (int j = 0 ; j<ch.length; j ++){
            int value =  map.get(ch[j]).intValue();
            if (!(charMap.containsKey(ch[j]))){
                charMap.put(ch[j],value);
                System.out.println(charMap);}
        }
    }

}
