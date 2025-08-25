package javaPrograms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Read_text_file {

    public static void main(String[] args) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get("automationframework.pdf"));
        String [] arr = allLines.get(0).split(System.getProperty("lines.separator"));
    }
}
