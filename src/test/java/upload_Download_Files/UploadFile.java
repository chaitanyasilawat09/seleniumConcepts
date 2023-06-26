package upload_Download_Files;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class UploadFile {

    @Test
    public void upload() {

        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://the-internet.herokuapp.com/");
        WebElement uploadButton = driver.findElement(By.xpath("//li/a[contains(text(),'File Upload')]"));
        uploadButton.click();
        WebElement chooseFile = driver.findElement(By.xpath("//input[@id='file-upload']"));

        String filePath = System.getProperty("user.dir") + "/src/test/test.text";

        chooseFile.sendKeys(filePath);
        WebElement submitFile = driver.findElement(By.xpath("//input[@id='file-submit']"));
        submitFile.click();

        WebElement uploadedFile = driver.findElement(By.xpath("//div[contains(text(),'test.text')]"));
        assertThat("Upload file not available ", uploadedFile.getText(), containsString("test.text"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
