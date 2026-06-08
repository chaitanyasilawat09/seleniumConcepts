package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver =
            new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void setDriver(){
        driver.set(new ChromeDriver());
    }

    public static void main(String[] args) {
        getDriver().get("https://google.com");
    }
}
