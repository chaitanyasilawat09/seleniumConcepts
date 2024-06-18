package extra;

import org.openqa.selenium.By;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import java.util.HashMap;

public class Headlessbrowser {


//    TODO What is Chrome Options Class?
//    The Chromeoptions Class is a concept in Selenium WebDriver for manipulating various properties
//    of the Chrome driver.
//    The Chrome options class is generally used in conjunction with Desired Capabilities for customizing Chrome driver sessions.
//    It helps you perform various operations
//    like opening Chrome in maximized mode, disable existing extensions, disable pop-ups, etc

//    TODO DesiredCapabilities
//    Desired Capabilities Class is used to modify multiple properties of web drivers.
//    It provides key-value pairs to change individual properties of web drivers such as
//    browser name, browser platform, etc.
//    A common method of Desired Capabilities class is the setCapability method.
//    It is mostly used with Selenium Grid, where the same test case needs to be executed on different browsers.


    @Test
    public void headLessBrowserTest() {


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, UnexpectedAlertBehaviour.ACCEPT);
        capabilities.setCapability(CapabilityType.PLATFORM_NAME, UnexpectedAlertBehaviour.ACCEPT);
        capabilities.setCapability(CapabilityType.BROWSER_VERSION, UnexpectedAlertBehaviour.ACCEPT);
        capabilities.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
        capabilities.setCapability("applicationCacheEnabled", false);

        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", "downloadPath");
        chromePrefs.put("download.prompt_for_download", false);

        System.setProperty("webdriver.driver.chrome", "chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");

        options.setExperimentalOption("pref",chromePrefs);
        options.merge(capabilities);

        WebDriver driver = new ChromeDriver(options);
        driver.get("https://demoqa.com/select-menu");

//        TODO for fireFox
//         FirefoxOptions options = new FirefoxOptions();
//        options.setHeadless(true);
//

//        TODO Selenium Webdriver handle SSL certificate
//        //Using the accept insecure cert method with true as parameter to accept the untrusted certificate
//		handlingSSL.setAcceptInsecureCerts(true);
//


        WebElement firstDropDown = driver.findElement(By.xpath("(//div[@class=' css-1wy0on6'])[1]"));
//        .UnexpectedTagNameException: Element should have been "select" but was "div"
//        when we pass non select tag element in select Class,
//        For select class tag name should be Select
        WebElement elementDropDown = driver.findElement(By.id("oldSelectMenu"));
        Select select = new Select(elementDropDown);
//        to get all values inside dropdown
        System.out.println(select.getOptions());
        for (WebElement element : select.getOptions())
            System.out.println(element.getText());

        System.out.println("------- Is Multiple  ------------");
//        to check we can select multiple value or not
        System.out.println(select.isMultiple());
        System.out.println("--------- -------");


//        to select by value (<option value="red">Red</option>)
        select.selectByValue("red");
//      to select by value (<option value="green">Green</option>)
        select.selectByVisibleText("Green");
//        to select by Index inside select tag
        select.selectByIndex(5);


    }
}
