
import org.openqa.selenium.WebElement;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Java_8_Code {

    public static void main1(String[] args) {
//        String str = "ChaitanyaSilawat";
        int [] ar = {1,3,2,4,5,6,5,7,8,6,4,7,8,9,061,45,76,45,67};
        String [] strArr = {"abc", "bcab","ascdab","dsabab","eabbrgh","we3Urabtht"};
        Map<String, String> map = new HashMap<>();
        map.put("abc","bb");
        map.put("bcd","cc");
        map.put("cde","dd");
        map.put("def","ee");
        ArrayList<Integer> list = (ArrayList<Integer>) Arrays.stream(ar).boxed().collect(Collectors.toList());
        ArrayList<String> strList = (ArrayList<String>) Arrays.stream(strArr).collect(Collectors.toList());

        System.out.println(list.stream().filter(a -> a%2==0).collect(Collectors.toList()));;

        System.out.println(list.stream().filter(a -> t2.numberIsEvenOrOdd(a)).collect(Collectors.toList()));

        System.out.println(list.stream().anyMatch(t2::numberIsEvenOrOdd));
//        System.out.println(list.stream().allMatch(t2::numberIsEvenOrOdd));
//        System.out.println(list.stream().noneMatch(t2::numberIsEvenOrOdd));

        System.out.println(strList.stream().filter(a -> a.substring(0,2).equals("ab")).collect(Collectors.toList()));
//        System.out.println(strList.stream().collect(Collectors.toMap(strList:)));


//        User user = getUser();
//        if (user != null) {
//            Address address = user.getAddress();
//            if (address != null) {
//                String street = address.getStreet();
//                if (street != null) {
//                    return street;
//                }
//            }
//        }
//        return "not specified";


//        This can be simplified with Optional:
//        Optional<User> user = Optional.ofNullable(getUser());
//        String result = user
//                .map(User::getAddress)
//                .map(Address::getStreet)
//                .orElse("not specified");

    }

    public static void main11(String[] args){
        String [] arr = {"automatic","aautozone","showauto","moboleauto","myautoriksha","yourautoShoq","automation"};
        int [] ar = {1,3,2,4,5,6,5,7,8,6,4,7,8,9,061,45,76,45,67};

        List<String> arrayList = (ArrayList)Arrays.stream(arr).collect(Collectors.toList());
//        List<WebElement> elementList = (ArrayList)Arrays.stream(arr).collect(Collectors.toList());
//
//
//        elementList.stream().collect(Collectors.toMap(WebElement::getText,WebElement::isDisplayed));
//
//
//        elementList.stream().collect(Collectors.toMap(WebElement::getText, Function.identity()));

//        System.out.println( arrayList.stream().
//                filter(a -> a.contains("auto"))
////                .map(a -> a+"_updated")
//                .reduce("",(a,b)-> a+"5"+b+"\n")
//                .toUpperCase()
//        );



        int [] arInt = {1,3,2,4,5,6,5,7,8,6,4,7,8,9,061,45,76,45,67};

        System.out.println(Arrays.stream(arInt).boxed().collect(Collectors.toList()).stream()
                .reduce((a,b) -> a<b? a:b).stream().collect(Collectors.toList()));



    }

    public static void main(String[] args){

        String [] arr = {"automatic","aautozone","showauto","moboleauto","myautoriksha","yourautoShoq","aut1omation"};
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
                int k    =    Arrays.stream(arr).filter(a -> a.contains(subStirng)).collect(Collectors.toList()).size();
                if (k==arlength && rep.length()<subStirng.length())
                    rep=subStirng;
            }
        }
        System.out.println(rep);
    }
}
