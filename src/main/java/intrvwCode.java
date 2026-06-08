import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class intrvwCode {

    // TODO code to repetitive Endurance of String
    public static void main(String[] args) {
        String s = "Hello one Hello Two Hello Three";
        String [] strArr = s.split("\\s");
        Map<String, Integer> map = new HashMap<>();
        for(String str : strArr){
//            if(map.containsKey(str)){
//              int value = map.get(str).intValue();
//              value++;
//              map.put(str,value);
//            }
//            else {
//                map.put(str,1);
//            }
            map.put(str, map.getOrDefault(str, 0)+1);
        }
        System.out.println(map);
        Iterator itr = map.entrySet().iterator();

        while (itr.hasNext()){

            Map.Entry entry = (Map.Entry) itr.next();
            int i = (int) entry.getValue();
            if(i>=3){
                System.out.println(entry.getKey() +"...."+entry.getValue());
            }
        }

    }
}
