import Base.ExtentReport_BaseTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;


public class ExtentReportTes extends ExtentReport_BaseTest{

    @Test
    public void extentReportsDemo()
    {
        test = reports.startTest("extentReportsDemoTest");
        driver.get("https://www.google.co.in");
        if(driver.getTitle().equals("Google"))
        {
            test.log(LogStatus.PASS, "Navigated to the specified URL");
        }
        else
        {
            test.log(LogStatus.FAIL, "Test Failed");
        }
    }

    @Test
    public void ExtentTest1(){
        test = reports.startTest("ExtentTest1");

        test.log(LogStatus.INFO,"Info by 1 ");
//        test.log(LogStatus.WARNING,"warning by me ");
        test.log(LogStatus.PASS,"Passed by me ");
    }

}
