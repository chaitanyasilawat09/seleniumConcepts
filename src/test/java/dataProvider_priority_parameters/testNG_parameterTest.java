package dataProvider_priority_parameters;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class testNG_parameterTest {
//
//    @Test
//    @Parameters({"userName", "password"})
////    testNG/testngGroup.xml
//    public void parameter(String uName, String pass){
//        System.out.println("UserName is :"+uName);
//        System.out.println("password is :"+pass);
//    }

    @Test
    @Parameters({"message"})
    public void OptionalParameter1(@Optional(" Optional Parameter Selected") String message) {

        System.out.println("1");
        System.out.println(message);

    }

    @Test
    @Parameters("message")
    public void OptionalParameter2(@Optional() String message) {
        System.out.println("2");
        System.out.println(message);
    }

}
