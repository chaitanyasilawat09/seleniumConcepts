import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ParallelBrowser {

    public WebDriver driver = null;

    @BeforeMethod
    @Parameters("browser")
    public void setUp() throws InterruptedException {

//        System.setProperty("webdriver.chrome.driver","chromedriver");
//        System.setProperty("webdriver.chrome.logfile", "TestLog.txt");
        WebDriverManager.chromedriver().arch64().setup();
        driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");

        driver.manage().window().maximize();
    }
    @AfterMethod
    public void teatDown() {
        driver.quit();

    }
}
