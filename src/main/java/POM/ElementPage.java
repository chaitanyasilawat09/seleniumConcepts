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


}
