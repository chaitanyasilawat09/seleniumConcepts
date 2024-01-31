package elements;

import Base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class A_4_Frames extends BaseTest {

    @Test
    public void handleFrames() {

        driver.get("https://demoqa.com/frames");
//        driver.switchTo().frame(0);
        driver.switchTo().frame("frame1");
        WebElement element = findElementFunction("//h1[@id='sampleHeading']");
        System.out.println(element.getText());
        sleep(5000);

        //Switch back to main page
        driver.switchTo().defaultContent();

        //Switch back to parent frame
        driver.switchTo().parentFrame();

        //switch to next frame
        driver.switchTo().frame("frame2");
        WebElement element1 = findElementFunction("//h1[@id='sampleHeading']");
        System.out.println(element1.getText());
    }
}
