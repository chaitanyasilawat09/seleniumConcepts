package run_Failed_tests;

import org.testng.TestNG;

import java.util.Collections;

public class ReRunFailedTests {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestSuites(Collections.singletonList("test-output/testng-failed.xml"));
        testng.run();


        //| Task                | How                           |
        //| ------------------- | ----------------------------- |
        //| Re-run failed tests | Use testng-failed.xml       |
        //| Run via code        | Use TestNG.setTestSuites()  |
        //| Run via Maven       | -DsuiteXmlFile=...         |
//      TODO  mvn test -DsuiteXmlFile=test-output/testng-failed.xml

        //| Retry automatically | Use a custom `IRetryAnalyzer` |
        //
    }
}

