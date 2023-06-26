package dataProvider_priority_parameters;

import org.testng.annotations.Test;

public class TestNG_DataProvider_Child extends TestNg_DataProvider {


    //    using Dataprovider of parent class
    @Test(dataProvider = "testDP", dataProviderClass = TestNg_DataProvider.class)
    public void test(String name) {
        System.out.println(name);
    }
}
