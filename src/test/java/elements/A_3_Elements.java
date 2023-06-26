package elements;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

public class A_3_Elements extends BaseTest {

    @Test
    public void verifyElementsTab() {
        //        css selector tag + class name  TODO (tag.ClassName)
        WebElement TextBox = driver.findElement(By.cssSelector("span.text"));
        TextBox.click();

//      css selector tag + ID  TODO (tag#Id)
        WebElement fullNameField = driver.findElement(By.cssSelector("input#userName"));
        fullNameField.clear();

//        send keys using JavascriptExecutor  TODO SendKeys By JS
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String fullName = "Chaitanya";
        String email = "abc@gmail.com";
        js.executeScript("document.getElementById('userName').value='" + fullName + "';");
        js.executeScript("document.getElementById('userEmail').value='" + email + "'");


//        css selector  Tag and attribute  TODO (tag[attribute=value])
        WebElement CurrentAddress = driver.findElement(By.cssSelector("textarea[id=currentAddress]"));
        CurrentAddress.clear();
        CurrentAddress.sendKeys("Current Address is Current Address");

//        css selector  Tag and attribute  TODO (tag.className[attribute=value])

        WebElement permanentAddress = driver.findElement(By.cssSelector("textarea.form-control[id=permanentAddress]"));
        permanentAddress.clear();
        permanentAddress.sendKeys("Permanent Address is Permanent Address");

//        TODO click()  by JS
        js.executeScript("document.getElementById('submit').click();");
        sleep(4000);


//        to refresh page using JS TODO Refresh By JS
        js.executeScript("history.go(0)");

        sleep(3000);
//      Vertical scroll - down by 500  pixels
        js.executeScript("window.scrollBy(0,500)");

    }
}
