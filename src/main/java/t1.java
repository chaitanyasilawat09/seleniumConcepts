import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class t1 {

    public static void main(String[] args) {
       checkCommanSubStringGivenArraycheckCommanSubStringGivenArray();
    }

    public static void checkCommanSubStringGivenArraycheckCommanSubStringGivenArray(){

        String [] arr = {"automatic","aautozone","showauto","moboleauto","myautoriksha","yourautoShoq","automation"};
        List<String> arrayList = (ArrayList)Arrays.stream(arr).collect(Collectors.toList());
        int arlength = arr.length;
        Arrays.sort(arr, Comparator.comparing(String::length));
        System.out.println(Arrays.toString(arr));
        String rep = "";
        String s1 = arr[0];
        int s1L = s1.length();
        for (int i = 0; i<s1L;i++){
            for (int j =i+1;j<=s1L;j++){

                String subStirng  = s1.substring(i,j);
                int k ;
                for (k=1; k<arlength;k++){
                    if(!(arr[k].contains(subStirng))){
                        break;

                    }
                }
                if (k==arlength && rep.length()<subStirng.length())
                    rep=subStirng;
            }
        }
        System.out.println(rep);
    }

    public static void find_longest_subString_and_Polendrom_SubString_from_Given_String(String[] args) {
       String s = "w12aaddaamadamaadaads232";
//       String s = "abcab";

       int strLength = s.length();
        boolean plndrCheck = false;
       String rep = "";
       for(int i =0; i<strLength; i++){

           for (int j =i+1; j<=strLength; j++){
               String subString = s.substring(i,j);
//               System.out.println(subString);
               // TODO to find Longest repetitive string

               Pattern p = Pattern.compile(subString);
               Matcher m = p.matcher(s);
               int count =0;
               while (m.find()){
                   count++;
               }
                if (count>1 &&rep.length()<subString.length()){
                    rep = subString;
                }
                // TODO to find Longest Palandram stirng from given string
//               if(subString.length()>1){
                   plndrCheck = plndrCheck(subString);
                   if(plndrCheck && rep.length()<subString.length())
                       rep= subString;

                   System.out.println(rep);

               }
           }



        System.out.println("rep...........");
        System.out.println(rep);
    }
    public static boolean plndrCheck(String str){

        char [] ch = str.toCharArray();
        int count = 0;
        int i=0;
        Boolean bool = false;
        for( i=0;i<ch.length/2;i++){
            if(ch[i]==ch[ch.length-1-i]){
                count++;
            }
        }
        if(i==count){
            bool =  true;
        }
        return bool;
    }
}