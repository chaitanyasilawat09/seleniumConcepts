package elements;

import Base.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class A_1_Alerts extends BaseTest {

    @Test
    public void handleAlerts() {
        driver.get("https://demoqa.com/alerts");
//        List<WebElement> ListOfHeading = driver.findElements(By.xpath("//span[@class='mr-3']"));
        driver.findElement(By.xpath("//button[@id='alertButton']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        // Clik on Alerts
        Alert alerts = driver.switchTo().alert();
        alerts.accept();
        alerts.getText();
        alerts.dismiss();
        alerts.sendKeys("sent to alerts");


    }
}
