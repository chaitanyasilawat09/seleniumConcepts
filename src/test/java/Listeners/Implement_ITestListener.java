package Listeners;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

// TODO use Listener inside class
//@Listeners(ITestListener_Override_Methods.class)
public class Implement_ITestListener {

    @Test
    public void test1() {
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        assertTrue(true);
    }

    @Test
    public void test2() {
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        assertFalse(true);
    }


    @Test
    public void test3() {
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }

    @Test(successPercentage = 60, invocationCount = 3)
    public void test4() {
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
        throw new SkipException("Skipping The Test Method ");

    }

}
