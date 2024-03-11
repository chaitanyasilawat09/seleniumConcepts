import java.util.*;
import java.util.stream.Collectors;

public class Java_8_Code {

    public static void main(String[] args) {
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

//        System.out.println(list.stream().filter(a -> t2.numberIsEvenOrOdd(a)).collect(Collectors.toList()));

//        System.out.println(list.stream().anyMatch(t2::numberIsEvenOrOdd));
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
}
