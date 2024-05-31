package POM;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Setter
@Getter
@ToString
//@NoArgsConstructor
public class ElementPage{

    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstNameField_Without_Cache;

    @CacheLookup
    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstNameField_cache;

    public ElementPage(WebDriver driver, WebElement firstNameField_Without_Cache, WebElement firstNameField_cache) {
//        super(driver);
        this.firstNameField_Without_Cache = firstNameField_Without_Cache;
        this.firstNameField_cache = firstNameField_cache;
    }

    public ElementPage(WebDriver driver) {
//        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public String toString() {
        return "ElementPage{" +
                "firstNameField_Without_Cache=" + firstNameField_Without_Cache +
                ", firstNameField_cache=" + firstNameField_cache +
                '}';
    }
}
