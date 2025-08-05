import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Test12312 {
    public static void main(String[] args) {
        String [] arr = {"automatic","aautozone","showauto","moboleauto","myautoriksha","yourautoShoq","automation"};
        int mainArrayLen = arr.length;
        String firstString = arr[0];
        int firstStringLen = firstString.length();
        Arrays.sort(arr, Comparator.comparing(String :: length));
        String repStirng = "";
        for(int i =0;i<firstStringLen ;i++){
            for (int j = i+1;j<=firstStringLen;j++){
                String subString = firstString.substring(i,j);

                int k = Arrays.stream(arr).filter(a -> a.contains(subString)).collect(Collectors.toList()).size();
            if (k==arr.length && repStirng.length()<subString.length())
                repStirng = subString;
            }


        }
        System.out.println(repStirng);

    }
}




