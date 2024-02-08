import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class t1 {

    public static void main(String[] args) {




    }


    public static void selectTab(String tabName){
        WebDriver driver = new ChromeDriver();
        String [] str = {"des","spe","rev"};
        for(String s : str){

            WebElement des = driver.findElement(By.xpath("//a[@href=\"#tab-"+tabName+"\"]/.."));
            WebElement spe = driver.findElement(By.xpath("//a[@href=\"#tab-"+tabName+"\"]/.."));
            WebElement rev = driver.findElement(By.xpath("//a[@href=\"#tab-"+tabName+"\"]/.."));

            if(tabName.equals(des)){
                if(spe.getAttribute("class").equals("")&& rev.getAttribute("class").equals("")){
                    des.click();
                }
            }


        }




    }
}
