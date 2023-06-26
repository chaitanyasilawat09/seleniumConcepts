package extra;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class Headlessbrowser {

    @Test
    public void headLessBrowserTest() {

        System.setProperty("webdriver.driver.chrome", "chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://demoqa.com/select-menu");

//        TODO for fireFox
//         FirefoxOptions options = new FirefoxOptions();
//        options.setHeadless(true);
//

//        TODO Selenium Webdriver handle SSL certificate
//        //Using the accept insecure cert method with true as parameter to accept the untrusted certificate
//		handlingSSL.setAcceptInsecureCerts(true);
//


        WebElement firstDropDown = driver.findElement(By.xpath("(//div[@class=' css-1wy0on6'])[1]"));
//        .UnexpectedTagNameException: Element should have been "select" but was "div"
//        when we pass non select tag element in select Class,
//        For select class tag name should be Select
        WebElement elementDropDown = driver.findElement(By.id("oldSelectMenu"));
        Select select = new Select(elementDropDown);
//        to get all values inside dropdown
        System.out.println(select.getOptions());
        for (WebElement element : select.getOptions())
            System.out.println(element.getText());

        System.out.println("------- Is Multiple  ------------");
//        to check we can select multiple value or not
        System.out.println(select.isMultiple());
        System.out.println("--------- -------");


//        to select by value (<option value="red">Red</option>)
        select.selectByValue("red");
//      to select by value (<option value="green">Green</option>)
        select.selectByVisibleText("Green");
//        to select by Index inside select tag
        select.selectByIndex(5);


    }
}
