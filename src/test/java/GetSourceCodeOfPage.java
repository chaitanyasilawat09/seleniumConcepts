import Base.BaseTest;
import org.testng.annotations.Test;

public class GetSourceCodeOfPage extends BaseTest {

    @Test
    public void getPageSourceCode() {
        driver.get("https://demoqa.com/");
        sleep(5000);
//        TODO use to get source code of Page
        System.out.println(driver.getPageSource());
    }
}
