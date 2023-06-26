package event_listener;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

public class EventHandler implements WebDriverEventListener {
    @Override
    public void beforeAlertAccept(WebDriver webDriver) {

    }

    @Override
    public void afterAlertAccept(WebDriver webDriver) {

    }

    @Override
    public void afterAlertDismiss(WebDriver webDriver) {

    }

    @Override
    public void beforeAlertDismiss(WebDriver webDriver) {

    }

    @Override
    public void beforeNavigateTo(String s, WebDriver webDriver) {
        System.out.println(s+"....beforeNavigateTo");

    }

    @Override
    public void afterNavigateTo(String s, WebDriver webDriver) {
        System.out.println(s+"....afterNavigateTo");

    }

    @Override
    public void beforeNavigateBack(WebDriver webDriver) {
        System.out.println("....beforeNavigateBack");

    }

    @Override
    public void afterNavigateBack(WebDriver webDriver) {
        System.out.println("....afterNavigateBack");
    }

    @Override
    public void beforeNavigateForward(WebDriver webDriver) {
        System.out.println("....beforeNavigateForward");

    }

    @Override
    public void afterNavigateForward(WebDriver webDriver) {
        System.out.println("....afterNavigateForward");

    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {
        System.out.println("....beforeNavigateRefresh");

    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {
        System.out.println("....afterNavigateRefresh");

    }

    @Override
    public void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        System.out.println(by+"...."+webElement+"....beforeFindBy");

    }

    @Override
    public void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
        System.out.println("Web element find "+webElement.getText()+"....afterFindBy");

    }

    @Override
    public void beforeClickOn(WebElement webElement, WebDriver webDriver) {
        System.out.println(webElement.getText()+"....beforeClickOn");

    }

    @Override
    public void afterClickOn(WebElement webElement, WebDriver webDriver) {
        System.out.println("....afterClickOn");

    }

    @Override
    public void beforeChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {
        System.out.println("....beforeChangeValueOf");

    }

    @Override
    public void afterChangeValueOf(WebElement webElement, WebDriver webDriver, CharSequence[] charSequences) {

    }

    @Override
    public void beforeScript(String s, WebDriver webDriver) {
        System.out.println(s+"....beforeScript");

    }

    @Override
    public void afterScript(String s, WebDriver webDriver) {
        System.out.println(s+"....afterScript");

    }

    @Override
    public void beforeSwitchToWindow(String s, WebDriver webDriver) {
        System.out.println(s+"....beforeSwitchToWindow");

    }

    @Override
    public void afterSwitchToWindow(String s, WebDriver webDriver) {
        System.out.println(s+"....afterSwitchToWindow");

    }

    @Override
    public void onException(Throwable throwable, WebDriver webDriver) {
        System.out.println(throwable.getStackTrace()+"....onException");

    }

    @Override
    public <X> void beforeGetScreenshotAs(OutputType<X> outputType) {

    }

    @Override
    public <X> void afterGetScreenshotAs(OutputType<X> outputType, X x) {

    }

    @Override
    public void beforeGetText(WebElement webElement, WebDriver webDriver) {
        System.out.println(webElement.getText()+"....beforeGetText");

    }

    @Override
    public void afterGetText(WebElement webElement, WebDriver webDriver, String s) {
        System.out.println(webElement.getText()+"....afterGetText");

    }
}
