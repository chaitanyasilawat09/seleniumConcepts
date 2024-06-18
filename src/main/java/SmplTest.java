import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SmplTest  {

    public static void main(String[] args) {

            int [] ar = {};
//        Convert[] to list
        Arrays.stream(ar).boxed().collect(Collectors.toList());
//
////        convert Int to Int[]
        Integer.toString(123).chars().map(c-> c-'0').toArray();
        String s = "";
        List<Integer> l = IntStream.range(0,s.length())
                            .filter(i -> s.charAt(i)==' ')
                                .boxed().collect(Collectors.toList());
    }
}