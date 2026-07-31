package extra;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.IOException;

public class Write_CreateExcel {
    public static void main(String[] args) {
        // Create a new workbook
//        .xlsx ke liye XSSFWorkbook use karo
//        .xls ke liye HSSFWorkbook use karo
        Workbook workbook = new XSSFWorkbook();

        // Create a sheet
        Sheet sheet = workbook.createSheet("Employee Data");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Name");
        headerRow.createCell(2).setCellValue("Department");

        // Create some data rows
        Object[][] data = {
                {1, "Alice", "QA"},
                {2, "Bob", "Development"},
                {3, "Charlie", "HR"}
        };

        int rowNum = 1;
        for (Object[] rowData : data) {
            Row row = sheet.createRow(rowNum++);
            for (int col = 0; col < rowData.length; col++) {
                row.createCell(col).setCellValue(rowData[col].toString());
            }
        }

        // Resize columns to fit content
        for (int i = 0; i < 3; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to file
        try (FileOutputStream fos = new FileOutputStream("EmployeeData.xlsx")) {
            workbook.write(fos);
            workbook.close();
            System.out.println("Excel file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

