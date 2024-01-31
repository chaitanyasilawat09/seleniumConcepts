package elements;

import Base.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class A_1_Alerts extends BaseTest {

    @Test
    public void handleAlerts() {
        driver.get("https://demoqa.com/alerts");
//        List<WebElement> ListOfHeading = driver.findElements(By.xpath("//span[@class='mr-3']"));
        driver.findElement(By.xpath("//button[@id='alertButton']")).click();
        // Clik on Alerts
        Alert alerts = driver.switchTo().alert();
        sleep(4000);
        alerts.accept();
        sleep(4000);

        driver.findElement(By.xpath("//button[@id='timerAlertButton']")).click();
        sleep(10000);
        Alert wait_5_second = driver.switchTo().alert();
        sleep(4000);
        wait_5_second.accept();
        wait_5_second.getText();
        sleep(4000);

        // Dismiss alert
        driver.findElement(By.xpath("//button[@id='confirmButton']")).click();
        Alert cancleAlert = driver.switchTo().alert();
        sleep(4000);
        cancleAlert.dismiss();
        sleep(4000);

        // Send values to alerts
        driver.findElement(By.xpath("//button[@id='promtButton']")).click();
        Alert sendKeys = driver.switchTo().alert();
        sleep(4000);
        sendKeys.sendKeys("Chaitanya");
        sleep(4000);

    }
}
