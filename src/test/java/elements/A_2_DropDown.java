package elements;

import Base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class A_2_DropDown extends BaseTest {

    @Test
    public void handleDropDown() {


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
