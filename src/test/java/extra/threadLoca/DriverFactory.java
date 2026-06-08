package extra.threadLoca;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }
    public static void unload() {
        driver.remove();
    }

    @BeforeMethod
    public void setup() {
        WebDriver driver = new ChromeDriver();
        DriverFactory.setDriver(driver);
    }

    @Test
    public void test1() {
        DriverFactory.getDriver().get("https://example.com");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.getDriver().quit();
        DriverFactory.unload();
    }
}
