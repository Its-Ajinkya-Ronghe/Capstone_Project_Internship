package utils;

import org.apache.poi.ss.usermodel.*;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class ExcelReader {
    public static Map<String, String> getRowData(String filePath, String sheetName, int rowIndex) throws Exception {
        Map<String, String> data = new HashMap<>();
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        Row headerRow = sheet.getRow(0);
        Row dataRow = sheet.getRow(rowIndex);

        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            String key = headerRow.getCell(i).getStringCellValue();
            String value = "";
            Cell cell = dataRow.getCell(i);
            if (cell != null) {
                value = cell.toString();
            }
            data.put(key, value);
        }

        workbook.close();
        fis.close();
        return data;
    }
}