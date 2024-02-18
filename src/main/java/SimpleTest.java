
public class SimpleTest  {
    public static void print_String_and_maintain_space(String[] args) {
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
}