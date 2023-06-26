package elements;

import Base.BaseTest;
import POM.ElementPage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

public class A_5_CacheLookup_Annotations extends BaseTest {
//    We use @FindBy and @FindAll annotations to mark a WebElement in the Page Object.
//    @CacheLookup is a very important and overlooked annotation that
//    can help us make our tests run faster.

    //@CacheLookup, as the name suggests helps us control when to cache a WebElement and
// when not to. This annotation, when applied over a WebElement, instructs Selenium to
// keep a cache of the WebElement instead of searching for the WebElement every time from
// the WebPage. This helps us save a lot of time.
    @Test
    public void test() {
        driver.get("https://demoqa.com/automation-practice-form");
        ElementPage page = PageFactory.initElements(driver, ElementPage.class);
        page.getFirstNameField_Without_Cache().sendKeys("ram");
        page.getFirstNameField_cache().getText();
        page.getFirstNameField_Without_Cache().getText();

        System.out.println(page.getFirstNameField_cache().getLocation());
        System.out.println(page.getFirstNameField_cache().getLocation());
        System.out.println(page.getFirstNameField_Without_Cache().getLocation());
        System.out.println(page.getFirstNameField_Without_Cache().getLocation());


    }


}
