package extra;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class WebDriverManagerTest {

    @Test
    public void test() {

//        If we do not want to set driver Manually so use WebDriverManager
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/select-menu");


    }
}
