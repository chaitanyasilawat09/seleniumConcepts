package event_listener;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class UploadFile {

//    TODO  Get method name and class name before  and after execution of test
    @BeforeMethod
    public void beforeSetup(Method m){
        System.out.println("Before Test----"+m.getDeclaringClass().getName()+"-----"+m.getName());
    }
    @AfterMethod
    public void afterSetup(Method m){
        System.out.println("After Test----"+m.getName());
    }

    @Test
    public void upload1() {
    }
        @Test
    public void upload() {

        WebDriverManager.chromedriver().arch64().setup();
        WebDriver eventDriver = new ChromeDriver();
        EventFiringWebDriver eventDriver1 = new EventFiringWebDriver(eventDriver);
        EventHandler eventHandler = new EventHandler();
        eventDriver1.register(eventHandler);
        eventDriver1.get("http://the-internet.herokuapp.com/");
        WebElement uploadButton = eventDriver1.findElement(By.xpath("//li/a[contains(text(),'File Upload')]"));
        uploadButton.click();
        WebElement chooseFile = eventDriver1.findElement(By.xpath("//input[@id='file-upload']"));

        String filePath = System.getProperty("user.dir") + "/src/test/test.text";

        chooseFile.sendKeys(filePath);
        WebElement submitFile = eventDriver1.findElement(By.xpath("//input[@id='file-submit']"));
        submitFile.click();

        WebElement uploadedFile = eventDriver1.findElement(By.xpath("//div[contains(text(),'test.text')]"));
        assertThat("Upload file not available ", uploadedFile.getText(), containsString("test.text"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
