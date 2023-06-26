package paralled_Executions;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;

public class Parallel_By_DataProvider {

    //    for this single test case 4 browser will open
    @Test(dataProvider = "dp1")
//    public void setup(LinkedHashMap<String,String> name){
    public void setup(String i, String j) {
        WebDriverManager.chromedriver().arch64().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/elements");
        System.out.println(i + "..." + j);
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();

    }

    @DataProvider(name = "dp", parallel = true)
    public Object[][] dataProvider() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("shiv", "shakti");
        map.put("ram", "sita");
        map.put("krishna", "radha");


        return new Object[][]{new Object[]{map}};
    }

    @DataProvider(name = "dp1", parallel = true)
    public Object[][] dp1() {
        return new Object[][]{
                new Object[]{"shiv", "shakti"},
                new Object[]{"Ram", "shakti"},
                new Object[]{"krishna", "shakti"},
                new Object[]{"lakshman", "urmila"}

        };
    }
}
