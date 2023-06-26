package dataProvider_priority_parameters;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;

public class TestNg_DataProvider {

    @DataProvider(name = "testDP")
    public Object[][] test1() {

        return new Object[][]{{"ram"}, {"shiva"}, {"krishna"}, {"radha"}};
    }


    @Test(dataProvider = "testDP")
    public void DP(String name) {
        System.out.println(name);

    }


    @DataProvider(name = "testDPMultipleData")
    public Object[][] MultipleData() {

        return new Object[][]{{"ram", "sita"}, {"shiva", "shakti"}, {"krishna", "Radha"}, {"radha", "Shayam"}};
    }


    @Test(dataProvider = "testDPMultipleData")
    public void MultipleDataTest(String name, String name2) {
        System.out.println("Name :-" + name + ",  Name :-" + name2);

    }


    @DataProvider(name = "dp")
    public Object[][] dataProviderS() {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("Ram", "Sita");
        map.put("Shiv", "Shakti");
        map.put("Krishna", "Radha");

        return new Object[][]{
                new Object[]{map}};
    }

    @Test(dataProvider = "dp", dataProviderClass = TestNg_DataProvider.class)
    public void test2(LinkedHashMap<String, String> testData) {
        System.out.println(testData.get("Shiv"));
        System.out.println(testData.get("Ram"));
        System.out.println(testData.get("Krishna"));
    }
}
