package javaPrograms;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;


public class Selenium_Questions {

    public void get_Network_log(){
        Object driver = null;
        DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
    }
}
