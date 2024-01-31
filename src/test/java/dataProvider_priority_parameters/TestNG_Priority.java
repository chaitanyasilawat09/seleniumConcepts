package dataProvider_priority_parameters;

import org.testng.annotations.Test;

public class TestNG_Priority {

//    a method with no priority, the priority is set to 0 by default.
//    if two or more methods have the same priorities in TestNG, then their running test sequence is alphabetic.


//    @Test(priority = 0) public void A_Test(){}
//    @Test(priority = 1) public void B_Test(){}
//    @Test(priority = -1) public void C_Test(){}
//    @Test public void D_Test(){}

    @Test(priority = 1)
    public void A_Test() {
    }

    @Test(priority = 2)
    public void B_Test() {
    }

    @Test(priority = -1)
    public void C_Test() {
    }

    @Test
    public void D_Test() {
    }
}
