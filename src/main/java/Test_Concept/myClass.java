package Test_Concept;

public class myClass{


    public static void main(String[] args) {
      int no =1234567;
      int reverse = 0;
      while (no!=0) {
          int reminder = no % 10;
          reverse = reverse * 10 + reminder;
          no = no / 10;
          System.out.println(no);
      }
        System.out.println(reverse);
    }
}
