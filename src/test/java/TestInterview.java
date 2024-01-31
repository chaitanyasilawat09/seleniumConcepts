import Base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.TestInstance;

import java.time.temporal.TemporalAccessor;

public class TestInterview {

    WebDriver driver;
    @BeforeMethod
    public void beforeMethod(){

        WebDriverManager.chromedriver().arch64().setup();
        driver = new ChromeDriver();

        driver.get("Amazon");

    }

    @Test(priority = 1)
    public void m1(){

        WebElement element = driver.findElement(By.xpath("Account"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        WebElement element1= driver.findElement(By.xpath("MyAccount"));
        element1.click();

    }

}
