package actionClass;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class MouseHover extends BaseTest {


    @Test
    public void normalMouseHover() {

        driver.get("https://demoqa.com/menu/");
        Actions action = new Actions(driver);
        WebElement menu = driver.findElement(By.xpath("//a[text()='Main Item 2']"));
        action.moveToElement(menu).perform();

        WebElement subMenu = driver.findElement(By.xpath("//a[text()='SUB SUB LIST »']"));
        action.moveToElement(subMenu).perform();
        assertThat(String.valueOf(subMenu.isDisplayed()), true);
        sleep(2000);

        WebElement sub_subMenu = driver.findElement(By.xpath("//a[text()='Sub Sub Item 1']"));
        action.moveToElement(sub_subMenu).perform();
        assertThat(String.valueOf(sub_subMenu.isDisplayed()), true);
//        sub_subMenu.click();
        sleep(5000);
    }

    @Test
    public void moveSlider() {
        driver.get("https://demoqa.com/slider/");
        WebElement sliderPoint_1 = driver.findElement(By.xpath("//div[text()='0']"));

        Actions actions = new Actions(driver);
        //Retrieve WebElemnt 'slider' to perform mouse hover
        WebElement slider = driver.findElement(By.id("slider"));
        //Move mouse to x offset 50 i.e. in horizontal direction
        actions.moveToElement(slider, 50, 0).perform();
        slider.click();
        System.out.println("Moved slider in horizontal directions");

    }
}
