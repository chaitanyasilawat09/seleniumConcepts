package upload_Download_Files;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class Download_And_Read_file {

    public WebDriver driver;

    @BeforeMethod
    public void t1() {
        WebDriverManager.chromedriver().arch64().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void GetDownloadFileFromDownload() {

        driver.get("http://the-internet.herokuapp.com/");

        WebElement uploadButton = driver.findElement(By.xpath("//li/a[contains(text(),'File Upload')]"));
        uploadButton.click();
        WebElement chooseFile = driver.findElement(By.xpath("//input[@id='file-upload']"));

        String pathOfTextFIle = System.getProperty("user.dir") + "/src/test/test.text";
        chooseFile.sendKeys(pathOfTextFIle);

        WebElement submitFile = driver.findElement(By.xpath("//input[@id='file-submit']"));
        submitFile.click();

        WebElement uploadedFile = driver.findElement(By.xpath("//div[contains(text(),'test.text')]"));
        assertThat("Upload file not available ", uploadedFile.getText(), containsString("test.text"));

        driver.navigate().back();
        driver.navigate().back();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        driver.findElement(By.xpath("//a[@href='/download']")).click();
        driver.findElement(By.xpath("//a[@href='download/test.text']")).click();
        FileInputStream file = getDownloadedFile();
        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        XSSFSheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getPhysicalNumberOfRows();
        System.out.println("Total Row in Sheet := " + rowCount);
        for (int i = 0; i < rowCount; i++) {
            int columnCountPerRow = sheet.getRow(i).getPhysicalNumberOfCells();
            System.out.println(i + "-- Row has total column := " + columnCountPerRow);
            List<String> list = new ArrayList<>();
            for (int j = 0; j < columnCountPerRow; j++) {
                XSSFCell cell = sheet.getRow(i).getCell(j);
                list.add(cell.getStringCellValue());
            }
            System.out.println(list);
        }
    }


    public FileInputStream getDownloadedFile() {

        FileInputStream file = null;
        String path = openChromeGetDownloadFileName();
        System.out.println("... Path is..... :- " + path);
        try {
            file = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return file;
    }

    public String openChromeGetDownloadFileName() {

        String mainWindow = driver.getWindowHandle();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.open()");
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        driver.get("chrome://downloads");
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        String fileName = (String) js1.executeScript(
                "return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
        System.out.println(fileName);

        String path = (String) js1.executeScript(
                "return document.querySelector(\"body > downloads-manager\").shadowRoot.querySelector(\"#frb0\").shadowRoot.querySelector(\"#file-icon\").src");
        path = path.split("path=")[1];
        path = path.split("&")[0];
        try {
            path = java.net.URLDecoder.decode(path, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e1) {

            e1.printStackTrace();
        }
        System.out.println(path);
        boolean name = true;
        if (name) {
            try {
                RemoteWebDriver rs = (RemoteWebDriver) driver;
                String content = null;
                WebElement elem = (WebElement) (rs).executeScript(
                        "var input = window.document.createElement('INPUT'); " + "input.setAttribute('type', 'file'); "
                                + "input.hidden = true; " + "input.onchange = function (e) { e.stopPropagation() }; "
                                + "return window.document.documentElement.appendChild(input); ",
                        "");
                elem.sendKeys(path);
                content = (String) (rs).executeAsyncScript(
                        "var input = arguments[0], callback = arguments[1]; " + "var reader = new FileReader(); "
                                + "reader.onload = function (ev) { callback(reader.result) }; "
                                + "reader.onerror = function (ex) { callback(ex.message) }; "
                                + "reader.readAsDataURL(input.files[0]); " + "input.remove(); ",
                        elem);
                if (!content.startsWith("data:")) {
                    System.out.println("Failed to get file content");
                }
//                String GRIDDOWNLOAD_PATH = "./GridDownloads/";
//                FileOutputStream fos = new FileOutputStream(GRIDDOWNLOAD_PATH + fileName);
//                FileOutputStream fos = new FileOutputStream(path);
                FileOutputStream fos = new FileOutputStream(path);
                byte[] decoder = Base64.decodeBase64(content.substring(content.indexOf("base64,") + 7));
                fos.write(decoder);
                boolean check = true;
                int i = 1;
                while (check && i <= 10) {
                    try {
                        fos.close();
                        check = false;
                    } catch (IOException e) {
                        Thread.sleep(500);
                        i++;
                        continue;
                    }
                }
                System.out.println("File saved to local.");
//                path = GRIDDOWNLOAD_PATH + fileName;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        if (!getProperty("RunningOn").equalsIgnoreCase("plexus")) {
//
//            try {
//                driver.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                System.out.println(e.getMessage());
//            }
//        }

        driver.switchTo().window(mainWindow);
        return path;

    }


}
