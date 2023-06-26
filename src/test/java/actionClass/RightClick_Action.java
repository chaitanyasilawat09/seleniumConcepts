package actionClass;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RightClick_Action extends BaseTest {


    private String clickMessage = "You have done a right click";
    private String doubleClickMessage = "You have done a double click";
    private String url = "http://demoqa.com/buttons";

    @Test
    public void rightClickActions() {

        driver.get(url);
        WebElement element = driver.findElement(By.id("rightClickBtn"));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOf(element));
//        TODO right click
        Actions actions = new Actions(driver);
        actions.contextClick(element).perform();

        WebElement rightClickMessage = driver.findElement(By.id("rightClickMessage"));
        wait.until(ExpectedConditions.visibilityOf(rightClickMessage));
        assertThat(rightClickMessage.getText(), is(clickMessage));
    }

    @Test
    public void doubleClickAction() {

        driver.get(url);
        WebElement doubleClickButton = driver.findElement(By.id("doubleClickBtn"));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(doubleClickButton));
        wait.until((ExpectedConditions.elementToBeClickable(doubleClickButton)));

//        TODO double Click
        Actions actions = new Actions(driver);
        actions.doubleClick(doubleClickButton).perform();

        WebElement message = driver.findElement(By.id("doubleClickMessage"));
        wait.until(ExpectedConditions.visibilityOf(message));

        assertThat(message.getText(), is(doubleClickMessage));
    }

    @Test
    public void dragNDrop() {

        driver.get("https://demoqa.com/droppable/");
        WebElement tab = driver.findElement(By.id("droppableExample-tab-preventPropogation"));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(tab));
        tab.click();
        WebElement drag = driver.findElement(By.id("dragBox"));
        wait.until(ExpectedConditions.visibilityOf(drag));
//        WebElement outerDrop = driver.findElement(By.xpath("//p[text()='Dropped!']"));
        WebElement outerDrop = driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/div[2]/div[2]/div/div[3]/div/div[2]/div[1]/p"));
        wait.until(ExpectedConditions.visibilityOf(outerDrop));

//        TODO drag N drop action
        Actions actions = new Actions(driver);
        actions.dragAndDrop(drag, outerDrop).perform();
        sleep(5000);
    }

    //    TODO xOffset is horizontal movement, yOffset is a vertical movement
//    TODO  dragAndDropBy(webElementToBeDrag,100,100);
    @Test
    public void dragNDropByOffset() {

        driver.get("https://demoqa.com/droppable/");
        WebElement tab = driver.findElement(By.id("droppableExample-tab-preventPropogation"));
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOf(tab));
        tab.click();
        WebElement drag = driver.findElement(By.id("dragBox"));
        wait.until(ExpectedConditions.visibilityOf(drag));

//        TODO drag N drop action
        Actions actions = new Actions(driver);
        actions.dragAndDropBy(drag, 100, 100);
        sleep(5000);
    }

}
