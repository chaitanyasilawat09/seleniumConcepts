package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver){
        driver.set(webDriver);
    }

    public static void quitDriver(){
        driver.remove();
    }

    public static WebDriver setUpDriverAndGet(WebDriver driver){
        setDriver(driver);
        return getDriver();

    }
    public static void main(String[] args) {
        WebDriver driver1 = setUpDriverAndGet(new ChromeDriver());
        driver1.get("https://google.com");
        quitDriver();
    }
}
