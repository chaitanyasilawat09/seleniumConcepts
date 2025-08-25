package paralled_Executions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class Using_Test_Annotation {

//            | `invocationCount = 4` | Run the method **4 times** total.         |
//            | `threadPoolSize = 4`  | Run them in **parallel using 4 threads**. |

    //    for this single test case 4 browser will open
    @Test(invocationCount = 4, threadPoolSize = 4)
    public void setup() {
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
