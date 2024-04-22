package POM;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class ElementPage {

    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstNameField_Without_Cache;

    @CacheLookup
    @FindBy(xpath = "//*[@id=\"firstName\"]")
    private WebElement firstNameField_cache;

    public ElementPage(WebElement firstNameField_Without_Cache, WebElement firstNameField_cache) {
        this.firstNameField_Without_Cache = firstNameField_Without_Cache;
        this.firstNameField_cache = firstNameField_cache;
    }

    @Override
    public String toString() {
        return "ElementPage{" +
                "firstNameField_Without_Cache=" + firstNameField_Without_Cache +
                ", firstNameField_cache=" + firstNameField_cache +
                '}';
    }
}
