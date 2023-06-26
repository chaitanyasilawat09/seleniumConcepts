import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class t1 {

    public static void main(String[] args) {
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
