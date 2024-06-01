import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class excel_reader {
    public static void main(String[] args) {
        try {
// Create a new Excel workbook
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Sheet1");

// Write data into the Excel file
            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Hello, World!");

            FileOutputStream fileOut = new FileOutputStream("example.xlsx");
            workbook.write(fileOut);
            fileOut.close();

            System.out.println("Data has been written to the Excel file.");

// Read data from the Excel file
            FileInputStream fileIn = new FileInputStream(new File("example.xlsx"));
            XSSFWorkbook readWorkbook = new XSSFWorkbook(fileIn);
            XSSFSheet readSheet = readWorkbook.getSheetAt(0);
            Row readRow = readSheet.getRow(0);
            Cell readCell = readRow.getCell(0);

            String data = readCell.getStringCellValue();
            System.out.println("Data read from the Excel file: " + data);

            fileIn.close();
            readWorkbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}