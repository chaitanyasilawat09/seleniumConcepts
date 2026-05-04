//package event_listener;
//
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.support.events.EventFiringWebDriver;
//import org.testng.annotations.Test;
//
//public class EventTest {
//    @Test
//public void test() {
//
////      TODO:- Listeners are special classes that listen to test execution events
////             and perform actions automatically when those events happen.
////Listeners = code that runs automatically when test events occur (start, pass, fail, skip, etc.).
////You don’t call them manually — TestNG calls them.
//
//    //WebDriverManager.chromedriver().arch64().setup();
//    WebDriver driver = new ChromeDriver();
//    EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
//
//    EventHandler handler = new EventHandler();
//    eventDriver.register(handler);
//    eventDriver.get("https://toolsqa.com");
//    WebElement element = eventDriver.findElement(By.id("search-form"));
//    element.click();
//}
//}
