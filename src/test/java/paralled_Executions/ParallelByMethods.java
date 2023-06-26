package paralled_Executions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class ParallelByMethods {

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
    }


}
