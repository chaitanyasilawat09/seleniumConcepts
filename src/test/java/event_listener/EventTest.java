package event_listener;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class EventTest {

    WebDriver eventDriver;


    //      TODO:- Listeners are special classes that listen to test execution events
//             and perform actions automatically when those events happen.
//Listeners = code that runs automatically when test events occur (start, pass, fail, skip, etc.).
//You don’t call them manually — TestNG calls them.
    @Test
    public void test() {

        WebDriver driver = new ChromeDriver();

        EventHandler handler = new EventHandler();

        eventDriver =
                new EventFiringDecorator<>(handler)
                        .decorate(driver);

        eventDriver.get("https://toolsqa.com");

        eventDriver.findElement(By.tagName("body")).click();
    }

    @AfterMethod
    public void tearDown() {

        if (eventDriver != null) {
            eventDriver.quit();
        }
    }
}



