package extra;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChromeOptionsExample {

//  TODO  The ChromeOptions class is a Selenium WebDriver class used to customize and control
//        how the Chrome browser launches and behaves during automation.
//     👉 ChromeOptions = settings/configurations for Chrome browser before it starts.

    public static void main(String[] args) throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");
        options.addArguments("--headless");
        options.addArguments("--disable-notifications");
        options.addArguments("--incognito");
        options.addArguments("--disable-infobars");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", "C:\\Downloads");

        options.setExperimentalOption("prefs", prefs);
        WebDriver driver = new ChromeDriver(options);



//        TODO DesiredCapabilities is a Selenium class used to define browser properties and environment settings
//        for WebDriver before starting the browser session.
//        In Selenium 4, DesiredCapabilities is mostly deprecated.
//
//👉 Now Selenium recommends using:ChromeOptions, FirefoxOptions,EdgeOptions
//     because these classes already implement capabilities internally.

        ChromeOptions options1 = new ChromeOptions();
        options.addArguments("--headless");
        options.setAcceptInsecureCerts(true);

        WebDriver driver1 =
                new RemoteWebDriver(new URL("gridURL"), options);
    }



}
