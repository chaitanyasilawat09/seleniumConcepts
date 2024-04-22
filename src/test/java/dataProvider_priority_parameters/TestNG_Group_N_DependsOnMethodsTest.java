package dataProvider_priority_parameters;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestNG_Group_N_DependsOnMethodsTest {

//It is important to note that Groups are declared in the testngGroup.xml file
// in the TestNG and can be found inside <test> tag or <suite> tag.

    //Also, remember that the groups defined in <test> tag apply to only that particular test tag,
// but the groups defined in <suite> tag apply to all the <test> tags in the XML file.
    @Test(groups = {"smoke"}, dependsOnMethods = "group2")
    public void group1() {
        System.out.println("smoke group");
    }


//    TODO  depends on Group
//       	<groups>
//   		<dependencies>
//   			<group depends-on= "smoke" name= "regression"></group>
//   		</dependencies>
//   		</groups>

    @Test(groups = {"regression"})
    public void group2() {
        System.out.println("Regression Group");
        Assert.assertEquals(true, false);
    }

    @Test(groups = {"smoke", "regression"})
    public void group3() {
        System.out.println("Smoke and Regression Group");
    }
}
