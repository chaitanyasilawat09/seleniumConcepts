package Test_Concept;

public class myClass{


    public static void main(String[] args) {
       try{
           int d = 0/1;
           System.out.println(d);
       }
       finally {
           System.out.println("finally");
       }
    }
}
