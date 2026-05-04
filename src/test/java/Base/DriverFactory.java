package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void initDriver(){
        driver.set(new ChromeDriver());
    }

    public static void main(String[] args) {
        initDriver();
        getDriver().get("https://google.com");
    }
}
