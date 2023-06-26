package actionClass;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class GetAttributeTest extends BaseTest {

    @Test
    public void getAttribute() {
        driver.get("https://demoqa.com/tool-tips/");
        WebElement ageTextBox = driver.findElement(By.id("age"));

        //Get title attribute value
        String tooltipText = ageTextBox.getAttribute("title");

        System.out.println("Retrieved tooltip text as :" + tooltipText);

        //Verification if tooltip text is matching expected value
        if (tooltipText.equalsIgnoreCase("We ask for your age only for statistical purposes.")) {
            System.out.println("Pass : Tooltip matching expected value");
        } else {
            System.out.println("Fail : Tooltip NOT matching expected value");
        }

    }

}
