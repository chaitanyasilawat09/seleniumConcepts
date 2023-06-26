package event_listener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Test;

public class EventTest {
    @Test
public void test() {
    WebDriverManager.chromedriver().arch64().setup();
    WebDriver driver = new ChromeDriver();
    EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);

    EventHandler handler = new EventHandler();
    eventDriver.register(handler);
    eventDriver.get("https://toolsqa.com");
    WebElement element = eventDriver.findElement(By.id("search-form"));
    element.click();
}
}
