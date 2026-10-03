package restAssured.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public enum ApplicationProperties {
    INSTANCE;

    private Properties properties;

    ApplicationProperties(){
        properties = new Properties();
        try {
            File file = new File("src/main/resources/application.properties");
            FileInputStream input = new FileInputStream(file);
            properties.load(input);

//            properties.load(
//                    Files.newInputStream(
//                            Paths.get("src/main/resources/application.properties")
//                    );


//            properties.load(getClass().getClassLoader().getResourceAsStream("src/main/java/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseURL(){
        return properties.getProperty("baseURL");
    }
}
