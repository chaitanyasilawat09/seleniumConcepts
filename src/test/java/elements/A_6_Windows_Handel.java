package elements;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class A_6_Windows_Handel extends BaseTest {

    @Test
    public void switch_From_New_Tab_To_Parent_Tab() {

        String mainWindow = driver.getWindowHandle();
        System.out.println("++++++++++mainWindow==========" + mainWindow);

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement windowsTab = driver.findElement(By.xpath("//div[text()='Alerts, Frame & Windows']"));
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(windowsTab));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", windowsTab);

//        windowsTab.click();

        WebElement browserWindows = driver.findElement(By.xpath("//span[text()='Browser Windows']"));
        js.executeScript("arguments[0].click();", browserWindows);
        sleep(5000);
        WebElement newTab = driver.findElement(By.id("tabButton"));
        newTab.click();
        sleep(5000);

        driver.switchTo().window(mainWindow);
        newTab.click();
        sleep(5000);
        driver.switchTo().window(mainWindow);
        newTab.click();
        sleep(5000);
        driver.switchTo().window(mainWindow);
//      TODO cast Set to ArrayList
        ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());

        String lastWindow = null;
        for (String window : tabs2) {
            System.out.println(window);
            lastWindow = window;
        }
        sleep(5000);
        //TODO Switch to last tab (first open window)
        driver.switchTo().window(tabs2.get(1));
        sleep(5000);
        System.out.println("all windows=====" + tabs2);



//        // Opens a new tab and switches to new tab
        driver.switchTo().newWindow(WindowType.TAB);
//
//// Opens a new window and switches to new window
        driver.switchTo().newWindow(WindowType.WINDOW);

        //Switch back to the old tab or window
        driver.switchTo().window(mainWindow);

    }


    @Test
    public void handleNewWindow_NotTab() {

        String mainWindow = driver.getWindowHandle();
        System.out.println("mainWindow---" + mainWindow);
        WebElement tab = driver.findElement(By.xpath("//div[text()='Alerts, Frame & Windows']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", tab);
        WebElement browserWindows = driver.findElement(By.xpath("//span[text()='Browser Windows']"));
        js.executeScript("arguments[0].click();", browserWindows);
        WebElement newWindow = driver.findElement(By.xpath("//button[@id='windowButton']"));
        newWindow.click();
        sleep(5000);
        ArrayList<String> allWindow = new ArrayList<String>(driver.getWindowHandles());
        System.out.println(allWindow);
        for (String window : allWindow) {
            System.out.println(window);
            if (!(mainWindow.contains(window))) {
                System.out.println("inside");
                driver.switchTo().window(window);
                WebElement newWindowText = driver.findElement(By.xpath("//h1[@id='sampleHeading']"));
                System.out.println(newWindowText.getText());
                sleep(5000);
            }
        }
    }
}
