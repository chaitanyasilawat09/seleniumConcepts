package dataProvider_priority_parameters;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class DataProvider_From_Excel {

    @Test(dataProvider = "excelData")
    public void testMethod(String firstName, String lastName) throws IOException {
        getData("Book1.xlsx", "Sheet1");
    }
    @DataProvider(name = "excelData")
    public Object[][] getExcelData() throws IOException {
        return getData("Book1.xlsx", "Sheet1");
    }

    public static Object[][] getData(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);  // XSSF for .xlsx
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {

                data[i - 1][j] = row.getCell(j).toString();
            }
        }

        workbook.close();
        fis.close();
        return data;
    }




}
