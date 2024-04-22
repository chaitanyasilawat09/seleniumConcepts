public class Test12312 {


    public static void main(String[] args) {
        String str = "i love Python";
//        o/p= "n ohty Pevoli"

        String [] stArr = str.split("\\s");
        String rev = "";
        for(int i=stArr.length-1; i>=0;i--){
            int count =0;
            char[] ch = stArr[i].toCharArray();
            for(int j = ch.length-1;j>0;j--){
                if(stArr[count].length()== ch.length- ch.length+1)
                    rev = rev+" ";
              rev = rev+ ch[j];
            }

        }
        System.out.println(rev);


    }

    }
