import org.jsoup.select.Collector;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class t2 {


    public static void main(String[] args) {
//
//        reverseString();
//        charCountInString();
//        find_Special_Charactr_And_Number_In_Given_String();
//        find_Special_Charactr_And_Number_In_Given_String_Using_Patter();
//        find_largest_and_Smallest_number();
//        rverse_Number();
//        print_Circular_Array();
//        print_Largest_Smallest_value_from_Two_Dimentiol_Array();
//        print_Star();
//        print_Duplicate_Element_From_List_and_Print_it();
//        short_An_ArrayList();
        find_Prime_Number();
//        palindrom_No();
//        find_Continious_Accurency_Of_Integer_In_Array();
//        print_String_and_maintain_space();

    }



    public static void print_String_and_maintain_space() {
        String s  = "Today is     Sunday";
        //        = "yadoT si     yadnuS"
        String[] strArr =  s.split("\\s");
        String rev = "";

        for(String s1 : strArr){
            String word = "";
            if(s1.length()!=0) {
                char[] ch = s1.toCharArray();

                for (int i = ch.length - 1; i >= 0; i--) {
                    word = word + ch[i];

                }
                rev = rev + word + " ";

            }
            else {
                rev = rev+" ";
            }
        }

        System.out.println(rev);

    }


    public static void find_Continious_Accurency_Of_Integer_In_Array()
    {
        int [] arr = {0,1,1,1,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0};
        int first = arr[0];
        int rep= 1;
        int count = 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i =1;i< arr.length;i++){

            if(first==arr[i]){
                System.out.println(arr[i]);
                count++;
            }
            else {
                first = arr[i];
                count = 1;
            }
            if (rep<count){
                System.out.println("count-"+ count);
                rep=count;
                map.put(arr[i],count);
            }
        }
        System.out.println(map);
    }

    public static boolean numberIsEvenOrOdd(int no){
        if(no%2==0){
            System.out.println("No.is------ "+no);
            return true;}
        else return false;

    }

    public static void palindrom_No() {

//        TODO Generate Random Number
        Random random = new Random();
//        int max=10000900;
//        int min=1000000;
//        int i = random.nextInt((max - min) + 1) + min;
//        System.out.println(i);
        System.out.println(random.ints(1000,2000).filter(a -> a%2!=0).findAny().getAsInt());
        System.out.println(random.ints(100,200).findAny().getAsInt());


        int no = 123456789;
//        String no = "chaitanya";
        int[] digits = Integer.toString(no).chars().map(c -> c-'0').toArray();

       Integer.toString(no).chars().map(c -> c-'0').toArray();

        char[] ch = String.valueOf(no).toCharArray();

        for(int i = 0 ;i<ch.length/2; i++){

            System.out.println(ch[i]+"...."+ch[ch.length-1-i]);

        }

    }

    private static void find_Prime_Number() {

//        which divided by only 1 and it and by it self
        int n =12;
        if (n <= 1) {
            System.out.println("not prime");
        }
        int i;
//        for ( i = 2; i < Math.sqrt(n); i++) {
        for ( i = 2; i < n; i++) {
            if (n % i == 0) {
                System.out.println("not prime");
                break;
            }
        }

    }

    private static void short_An_ArrayList() {
        int [] ar = {1,3,2,4,5,6,5,7,8,6,4,7,8,9,061,45,76,45,67};

        ArrayList<Integer> list = (ArrayList<Integer>) Arrays.stream(ar).boxed().collect(Collectors.toList());
        for (int i=0;i<list.size(); i++){

            for (int j=0; j<list.size(); j++){

                if (list.get(i)<list.get(j)){
                    Integer temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j,temp);

                }
            }
        }
        System.out.println(list);
    }


    private static void print_Duplicate_Element_From_List_and_Print_it() {

        int [] ar = {1,3,2,4,5,6,5,7,8,6,4,7,8,9,061,45,76,45,67};
        ArrayList<Integer> list = (ArrayList<Integer>) Arrays.stream(ar).boxed().collect(Collectors.toList());
        List<Integer> newList = new ArrayList<>();
        for (int i : list){
            if (!newList.contains(i)){
                newList.add(i);
            }
        }
        System.out.println(newList);

    }

    private static void print_Star() {

        for(int i = 1; i <= 5; i++){

            for(int j =1; j <= i; j++) {
                System.out.println("*");
            }
            System.out.println();
        }


        for (int i = 1; i  <= 5; i++) {
            // Inner loop for number of stars in each row
            for (int j = 1; j  <= i; j++) {
                System.out.print("*");
            }
            // Move to the next line after printing stars in each row
            System.out.println();
        }
    }

    private static void print_Largest_Smallest_value_from_Two_Dimentiol_Array() {
        int [][] arr = {{44,6,8},{3,2,65,78,79}};

        int min = arr[0][0];
        int max = arr[0][1];
        System.out.println(min +"   "+max);

        for (int [] first : arr){

            for (int i :first){
                if (max<i){
                    min = max;
                    max = i;
                }
                else {
                    if(min>i){
                        min = i;
                    }
                }
            }
        }
        System.out.println(min +"   "+max);



    }

    private static void print_Circular_Array() {
//        TODO  //For example, if we perform a circular left rotation on an array [1, 2, 3, 4, 5]
//         by a factor of ‘2’ it would be [3, 4, 5, 1, 2]

        int [] ia= {1, 2, 3, 4, 5};
        int fac = 3;
        List<Integer> list = new ArrayList<>();
        for(int i =fac;i<ia.length;i++){
            list.add(ia[i]);
        }
        for (int j =0;j<fac;j++){
            list.add(ia[j]);
        }
        System.out.println(list);







    }

    private static void rverse_Number() {

        int number = 998877661;
        int reverse =0;
        while (number!=0){
            int reminder = number%10;
            reverse = reverse *10 + reminder;
            number = number/10;

        }
        System.out.println(reverse);

    }

    private static void find_largest_and_Smallest_number() {

        int num[] ={ 900,90,6,7,5000,4,60000,20,3};
        int largest = num[0];
        int smallest = num[1];
        for(int i : num){

            if(largest<i){
                smallest = largest;
                largest = i;
            }
            if (smallest>i)
                smallest=i;
        }
        System.out.println(largest);
        System.out.println(smallest);

    }

    private static void find_Special_Charactr_And_Number_In_Given_String_Using_Patter() {
// Main "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"
//        24 feb "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"
        String st = "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";
        char[] ch = st.toCharArray();
        Map<Character, Integer> map = new HashMap();

        for (char c : ch){
            Pattern pattern = Pattern.compile(String.valueOf(c));
            Matcher matcher = pattern.matcher(st);
            int count = 0;
            while(matcher.find()){
                count++;
            }
            map.put(c,count);
        }
        System.out.println(map);

        Iterator itr = map.entrySet().iterator();

        while(itr.hasNext()){

            Map.Entry entry = (Map.Entry)itr.next();

            System.out.println(entry.getKey()+"..."+entry.getValue());
            if (entry.getKey().equals("a")){
                itr.remove();
            }
        }
        System.out.println(map);
    }

    private static void find_Special_Charactr_And_Number_In_Given_String() {

        String st = "chait12^%sdf%s6@$NYJ0";
        char [] ch = st.toCharArray();
        String str = "";
        int integers = 0;
        String special = "";

        for(char c : ch){

            if(Character.isDigit(c)){
                integers= integers+Integer.parseInt(String.valueOf(c));
            }
            if (Character.isLetter(c)){
                str = str+c;
            }
            if(!Character.isLetter(c) && !Character.isDigit(c)) {
                special = special + c;
            }

        }
        System.out.println(str);
        System.out.println(integers);
        System.out.println(special);

    }

    private static void charCountInString() {

        String st = "ChaitanyaSilawat";
        char[] ch = st.toCharArray();
        Map<Character, Integer> map = new HashMap<>();
        for (char c :ch){

            if(map.containsKey(c)){
                int value = map.get(c).intValue();
                value++;
                map.put(c, value);
            }
            else {
                map.put(c,1);
            }
        }
        System.out.println(map);

        Iterator itr = map.entrySet().iterator();
        while(itr.hasNext()){

            Map.Entry<Character, Integer> entry = (Map.Entry)itr.next();
            System.out.println(entry.getKey() +"..."+entry.getValue());

            if(entry.getValue()%2==0){
                itr.remove();
            }
        }
        System.out.println(map);
    }

    private static void reverseString() {

        String str = "Chaitanya Silawat is My Name";
        String [] arrSt = str.split("\\s");
        String rev = "";
        for (String st : arrSt){

            char[] ch = st.toCharArray();

            String word = "";
            for (int i =ch.length-1; i>=0; i--){
                word = word+ch[i];
            }
            rev = rev+ word+" ";
        }
        System.out.println(rev);

    }



    public void findCommaonString(){
        // Input: strs = ["flower","flow","flight"]
        // Output: "fl"
        String [] st = {"flower","flow","flight"};
//        String [] st = {"abc","ab","a"};
        String s = "";
        for(int i =0; i<st.length; i++) { //flower

            String  s1 = st[i];
            char [] ch = s1.toCharArray();

            for(int j =1; j<st.length; j++) {

//                if(st[j].contains(String.valueOf(ch[i]))) {
                if(st[j].contains(String.valueOf(ch[i]))) {
                    s = s + ch[i];
                    continue;
                }

            }
            System.out.println("s inside "+ s );
        }
        System.out.println("s outside "+ s );




//
//            char [] ch = st[0].toCharArray();
//            for(int j =0; j<ch.length; j++){
//
//            Pattern p = Pattern.compile(String.valueOf(ch[j]));
//            Matcher m = p.matcher(st[i]);
//            int count = 0;
//            while (m.find()){
//                count ++;
//            }
//            if(count>=1)
//                map.put(ch[i], count);
//
//            }
//            System.out.println(map);
//        }






    }


}
