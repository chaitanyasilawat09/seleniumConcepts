package javaPrograms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Read_text_file {

    public static void main(String[] args) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get("src/Base/InterviewQuestions.txt"));
//        String [] arr = allLines.get(0).split(System.getProperty("lines.separator"));
        System.out.println(allLines);
        System.out.println(allLines.get(0));

//        or

        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
