package run_Failed_tests;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Using_Listeners implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation,
                          Class testClass,
                          Constructor testConstructor,
                          Method testMethod) {
        annotation.setRetryAnalyzer(FailedTestRun_Using_IRetryAnalyzer.class);
        System.out.println(".....................");
        System.out.println(testMethod.getName());
        System.out.println("--------------------xxxxxx--------------");
    }
}
