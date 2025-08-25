package javaPrograms;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.annotations.Test;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class InterviewCode_WRT_Strings {

    @Test
    public void reverseString() {
        String Name = "ChaitanyaSilawat";
        String revString = "";
        char[] ch = Name.toCharArray();
        for (int i = ch.length - 1; i >= 0; i--) {
            revString = revString + ch[i];
        }
        System.out.println(revString);
    }

    @Test
    public void reverse_eachWord_at_Same_PLace() {
        String name = "My Name is Chaitanya";
        String[] StringArr = name.split(" ");
        String revString = "";
        for (String eachWord : StringArr) {
            char[] chArr = eachWord.toCharArray();
            String revWord = "";
            for (int i = chArr.length - 1; i >= 0; i--) {
                revWord = revWord + chArr[i];
            }
            revString = revString + revWord + " ";
        }
        System.out.println(revString);
    }

    @Test
    public static void find_Dup_Char_In_String() {
        String name = "duplicateCharacterinString";
        Map<Character, Integer> storeMap = new HashMap<>();
        char[] chArr = name.toCharArray();

        for (char ch : chArr) {
            if (storeMap.containsKey(ch)) {
                int i = storeMap.get(ch) + 1;
                storeMap.put(ch, i);
            } else storeMap.put(ch, 1);
        }
        System.out.println(storeMap);
        Map<Character, Integer> maps = storeMap.entrySet().stream().filter(entry -> entry.getValue() > 1).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.out.println(maps);
    }

    @Test
    public void count_Of_Word_In_String() {
        String name = "My Name is chaitanya   silawat";
        String[] chArr = name.split(" ");
        ;
        System.out.println(Arrays.stream(chArr).filter(a -> a.length() > 0).collect(Collectors.toList()).size());
    }

    @Test
    public static void permute() {
        String str = "ABC";
        String prefix = "";
        permute(str, prefix);

    }

    private static void permute(String str, String prefix) {
        if (str.length() == 0) {
            System.out.println(prefix);
        } else {
            for (int i = 0; i < str.length(); i++) {
                String rem = str.substring(0, i) + str.substring(i + 1);
                permute(rem, prefix + str.charAt(i));
            }
        }
    }

    @Test
    public static void palindram() {
        String name = "daamnmmaad";
        char[] chArr = name.toCharArray();
        int i = 0;
        int count = 0;
        for (i = 0; i <= chArr.length / 2; i++) {
            if ((chArr[i] == chArr[chArr.length - 1 - i])) {
                count++;
            }
        }
        if (i == count)
            System.out.println("plndrm");
        else
            System.out.println("Not plndrm");
    }

    @Test
    public void verify_Two_String_Anagrams() {
        //Example: "listen" and "silent" are anagrams.
        String name1 = "listen";
        String name2 = "silent";
        char[] ch1 = name1.replace("//s", "").toCharArray();
        char[] ch2 = name2.replace("//s", "").toCharArray();
        Arrays.sort(ch1);
        Arrays.sort(ch2);
//        System.out.println( Arrays.equals(ch1,ch2));
        for (int i = 0; i <= ch1.length; i++) {
            if (ch1[i] == ch2[i]) {
                System.out.println("not");
                break;
            }
        }
    }

    @Test
    public void count_vowel_In_String() {
        String name = "chaitanya";
        char[] chArr = name.toLowerCase().toCharArray();
        // vowel count
        System.out.println(name.toLowerCase().chars()
                .filter(c -> "aeiou".indexOf(c) != -1).count());

        //print only vowel from given string
        System.out.println(name.chars().mapToObj(c -> (char) c)
                .filter(c -> "aeiou".indexOf(c) != -1)
                .collect(Collectors.toList()));
    }

    @Test
    public void unique_Charactor_In_Stirg() {
        String name = "mynameisChaitanya";
        String output = "";
        for (char c : name.toCharArray()) {
            if (!output.contains(Character.toString(c))) {
                output = output + c;
            }
        }
        System.out.println(output);
    }

    @Test
    public void swap_string_without_3rd_String() {
        String s1 = "chaitanya";
        String s2 = "aayu";
        s1 = s1 + s2;
        s2 = s1.substring(0, s1.length() - s2.length());
        s1 = s1.substring(s2.length());
        System.out.println(s1);
        System.out.println(s2);
    }

    @Test
    public void a2b3c4() {
        String name = "a2b3c4";
        String output = "";
        char[] chArr = name.toCharArray();
        for (int i = 0; i < chArr.length; i += 2) {
            char newChar = chArr[i];
            int repeat = Character.getNumericValue(chArr[i + 1]);
            for (int j = 0; j < repeat; j++) {
                output = output + newChar;
            }
        }
        System.out.println(output);
    }

    @Test
    public void lower_And_upper_case(){
        String name = "aBCAbcEDdeF";
        String lower="";
        String upper="";

        for (char c : name.toCharArray()){
          if (Character.isLowerCase(c)){
              lower = lower+c;
          }
          else if (Character.isUpperCase(c)) {
              upper = upper+c;

          }
        }
        System.out.println(lower);
        System.out.println(upper);
    }

    @Test
    public void shift_0_to_End() {
        String name = "10203034012410041410";
        String zero = "";
        String nonZero = "";
        char [] chArr = name.toCharArray();
        for (char c : chArr){
            if (c=='0'){
                zero =zero+c;
            }
            else nonZero= nonZero+c;
        }

        System.out.println(nonZero+zero);
    }

    @Test
    public void common_btwn_two_Array(){
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(4, 5, 6, 7, 8);
        List<Integer> list3 = new ArrayList<>(list2);

        System.out.println( list1.stream().filter(a-> list2.contains(a))
                .collect(Collectors.toList()));
        list3.retainAll(list1);
        System.out.println(list3);

    }

    //    Import   <groupId>org.apache.pdfbox</groupId>
    public void read_PDF_FIle() throws Exception {
        String[] lines;
        PDDocument document = PDDocument.load(new File("3-6 Years - 18-Mar.pdf"));
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        lines = text.split(System.getProperty("line.separator"));
        //lines = text.split("\n");
        for(String s : lines){
            if(s.contains("Email: "))
                System.out.println(s.replace("Email: ",""));
        }
//        System.out.println(lines[0].toString());
        document.close();


    }
}
