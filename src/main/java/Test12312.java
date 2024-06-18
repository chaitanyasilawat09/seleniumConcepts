import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test12312 {

    public static void main(String[] args) throws IOException {

        {
//            List<String> content = Files.readAllLines(Paths.get("TestLog.txt"));
//            String []s = content.get(0).split(System.getProperty("line.separator"));
//            System.out.println(s[0]);
//            System.out.println(content.get(0).split("\\s")[0]);

            File file = new File("myFile.text");
//            file.d
            file.createNewFile();
            System.out.println(file.exists());
            file.deleteOnExit();
            System.out.println(file.exists());
        }
    }
}


