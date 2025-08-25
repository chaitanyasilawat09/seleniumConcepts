package Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URL;

public class CrossBrowserTest {

    WebDriver driver;

    public static final String USERNAME = "your_browserstack_username";
    public static final String ACCESS_KEY = "your_browserstack_access_key";
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY +
            "@hub-cloud.browserstack.com/wd/hub";

    @Parameters({"browser", "browser_version", "os", "os_version"})
    @BeforeMethod
    public void setup(String browser, String browser_version, String os, String os_version) throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("browser", browser);
        caps.setCapability("browser_version", browser_version);
        caps.setCapability("os", os);
        caps.setCapability("os_version", os_version);
        caps.setCapability("name", "Cross Browser Test - Example");

        driver = new RemoteWebDriver(new URL(URL), caps);
    }

    @Test
    public void openGoogle() {
        driver.get("https://www.google.com");
        System.out.println("Title is: " + driver.getTitle());
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
