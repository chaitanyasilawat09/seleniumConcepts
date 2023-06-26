package run_Failed_tests;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class FailedTestRun_Using_IRetryAnalyzer implements IRetryAnalyzer {

    int count = 0;
    int retry = 2;
    @Override
    public boolean retry(ITestResult iTestResult) {
        if (count<retry) {
            count++;
            return true;
        }
        return false;
    }
}
