package restAssured.utils;

import java.io.IOException;
import java.util.Properties;

public enum ApplicationProperties {
    INSTANCE;

    private Properties properties;

    ApplicationProperties(){
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("src/main/java/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getBaseURL(){
        return properties.getProperty("baseURL");
    }
}
