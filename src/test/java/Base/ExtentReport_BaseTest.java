package Base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class ExtentReport_BaseTest {

    public WebDriver driver = null;
    public static ExtentReports reports;
    public static ExtentTest test;

    @BeforeMethod
    public void beforeTest() {
        reports = new ExtentReports(System.getProperty("user.dir")+"/testQA.html");
//        reports = new ExtentReports(Paths.get("").toString());
               reports.addSystemInfo("Host Name", "SoftwareTestingMaterial")
                .addSystemInfo("Environment", "Automation Testing")
                .addSystemInfo("User Name", "Chaitanya");

//        driver = new ChromeDriver();
        driver = DriverFactory.setUpDriverAndGet(new ChromeDriver());
        driver.get("https://demoqa.com/elements");

    }

    @AfterMethod
    public void test1() {
        reports.endTest(test);  // 1. End the test
        reports.flush();        // 2. Flush the report (write everything to file)
        driver.quit();          // 3. Quit the browser
    }

    public static String capture(WebDriver driver) {

//        download the jar file of the Ashot library from "https://jar-download.com/artifacts/ru.yandex.qatools.ashot/ashot "
        // capture screenshot and store the image
//        Screenshot s=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
//        ImageIO.write(s.getImage(),"PNG",new File("C:\\projectScreenshots\\fullPageScreenshot.png"));
//
        TakesScreenshot takesScreenshot = ((TakesScreenshot) driver);
        File scrFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File Dest = new File("src/../ErrImages/" + System.currentTimeMillis()
                + ".png");
        String errflpath = Dest.getAbsolutePath();
        try {
            FileUtils.copyFile(scrFile, Dest);
        } catch (Exception e) {
            test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Test Failed");
            e.printStackTrace();
        }
        return errflpath;
    }
}
