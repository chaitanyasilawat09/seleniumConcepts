import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Set;

public class SimpleTest  {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","chromedriver121/chromedriver");
        WebDriver driver = new ChromeDriver();
            driver.get("https://rahulshettyacademy.com/AutomationPractice/");
        List<WebElement> trLIst = driver.findElements(By.xpath("(//table//tbody)[2]//tr"));

        for(WebElement td : trLIst){

            List<WebElement> tbLIst  =  td.findElements(By.tagName("td"));


            System.out.println(tbLIst.get(3).getText());

        }


    }
}