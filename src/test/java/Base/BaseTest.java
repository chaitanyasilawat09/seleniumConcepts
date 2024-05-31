package Base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.time.Duration;
import java.util.Collections;
import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseTest {

    public WebDriver driver = null;

    @BeforeTest
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver","chromedriver1.exe");
        System.setProperty("webdriver.chrome.logfile", "TestLog.txt");
//        WebDriverManager.chromedriver().arch64().setup();
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");
        implicateWait();
        pageLoadWait();
        driver.manage().window().maximize();
        sleep(1000);
        assertThat(driver.getTitle(), is("ToolsQA"));

    }

    @AfterTest
    public void teatDown() {
        driver.quit();

    }

    //    TODO  Implicate Wait
    public void implicateWait() {
        driver.manage().timeouts().implicitlyWait(40, SECONDS);
    }

    //    TODO PageLoad time
    public void pageLoadWait() {

        driver.manage().timeouts().pageLoadTimeout(20, SECONDS);
    }

    //    TODO Explicate Wait  visibleElement
    public void explicateWaitVisibleElement(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(element));

//        wait.until(ExpectedConditions.visibilityOfAllElements(element,element,element));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("")));
    }

    //    TODO Explicate Wait element Clickable
    public void explicateWaitElementClickable(WebElement element) {

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(element));
//        wait.until(ExpectedConditions.alertIsPresent());
    }

    //    TODO Fluent wait
    public void fluentWait(WebElement element) {
        Wait wait = (Wait) new FluentWait(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public WebElement findElementFunction(String path) {
        WebElement element = driver.findElement(By.xpath(path));
        explicateWaitVisibleElement(element);
        return element;
    }


    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
