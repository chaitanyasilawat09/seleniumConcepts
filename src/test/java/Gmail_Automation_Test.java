import Base.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Gmail_Automation_Test {

    @Test
    public void sendEmail(){

        String url = "https://accounts.google.com/signin";
        WebDriverManager.chromedriver().arch64().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        WebDriver driver =  new ChromeDriver(options);
//        driver.get("https://demoqa.com/elements");
        driver.get(url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement email_phone = driver.findElement(By.xpath("//input[@id='identifierId']"));
        email_phone.sendKeys("chetanya1992@gmail.com");
        driver.findElement(By.id("identifierNext")).click();
        WebElement password = driver.findElement(By.xpath("//input[@name='password']"));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(password));
        password.sendKeys("xyz96321");
        driver.findElement(By.id("passwordNext")).click();

    }
}
