package extra;

import Base.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.HasFullPageScreenshot;
import org.openqa.selenium.remote.Augmenter;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Screenshots extends BaseTest {

    @Test
    public void screenshots() throws IOException {
        driver.get("https://demoqa.com/select-menu");

//        TODO take screenshot
//        capturing a screenshot, using Selenium WebDriver, of the currently visible part of the Web page:
        TakesScreenshot screenshots = ((TakesScreenshot) driver);
        File file = screenshots.getScreenshotAs(OutputType.FILE);
        System.out.println(file.getName());

//        copy screenshot file to CWD
//        FileUtils.copyFile(file, new File("C:\\projectScreenshots\\homePageScreenshot.png"));

//   To capture the full page screenshot, we have to use a third-party library named Ashot.
//   It provides the ability to take a screenshot of a particular WebElement as well as a full-page screenshot.

//        // capture screenshot and store the image
//        Screenshot s=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
//        ImageIO.write(s.getImage(),"PNG",new File("C:\\projectScreenshots\\fullPageScreenshot.png"));
//


        // Locate the web element
        WebElement logo = driver.findElement(By.xpath("//*[@id=\"app\"]/header/a/img"));
//
        // capture screenshot with getScreenshotAs() of the WebElement class
        File f = logo.getScreenshotAs(OutputType.FILE);
//
//        FileUtils.copyFile(f, new File("C:\\projectScreenshots\\logoScreeshot.png"));
//

        WebDriver augmented = new Augmenter().augment(driver);
        File fullPage = ((HasFullPageScreenshot) augmented)
                .getFullPageScreenshotAs(OutputType.FILE);
        Files.copy(fullPage.toPath(), Path.of("fullpage.png"));
    }
}
