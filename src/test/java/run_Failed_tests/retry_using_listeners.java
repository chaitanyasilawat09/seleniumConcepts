package run_Failed_tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class retry_using_listeners {

        @Test
    public void test1(){
        System.out.println("1111111111");
        Assert.assertTrue(true,"vv");
//        assertThat("true",false);
    }

        @Test
    public void test2(){
        System.out.println("2222222222");
        System.out.println("333333333");
//        This test will re-run
//        according to FailedTestRun_Using_IRetryAnalyzer and Using_Listeners
        Assert.assertTrue(false,"vv");
        }
}
