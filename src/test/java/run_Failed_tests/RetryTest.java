package run_Failed_tests;

import Base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public class RetryTest  {

    @Test(retryAnalyzer = FailedTestRun_Using_IRetryAnalyzer.class)
    public void test(){
        System.out.println("11111111");
        assertThat("true",false);
    }

    @Test(retryAnalyzer = FailedTestRun_Using_IRetryAnalyzer.class)
//    @Test(invocationCount = 4)
    public void test1(){
        System.out.println("2222222222");
        Assert.assertTrue(false,"vv");
//        assertThat("true",false);
    }
}
