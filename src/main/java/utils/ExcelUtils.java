package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtils {

    // Initialize Log4j2 Logger
    private static final Logger logger = LogManager.getLogger(ExcelUtils.class);

    // Path to the Excel file that contains test steps
    private static final String EXCEL_PATH = "./test-data/testSteps.xlsx";

    /**
     * Reads test steps from the given Excel sheet.
     * Each row should have 5 columns: TestCase, Action, LocatorType, LocatorValue, Value
     *
     * @param sheetName the sheet from which to read test steps
     * @return a list of String arrays, where each array represents a test step
     */
    public static List<String[]> getTestSteps(String sheetName) {
        List<String[]> testSteps = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(EXCEL_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Get the desired sheet
            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> iterator = sheet.iterator();

            // Skip the header row
            if (iterator.hasNext()) iterator.next();

            // Read each row and convert it into a String array
            while (iterator.hasNext()) {
                Row row = iterator.next();
                String[] rowData = new String[5]; // To store 5 columns of data

                // Loop through each column and get the value
                for (int i = 0; i < 5; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    rowData[i] = cell.toString().trim(); // Trim to remove leading/trailing spaces
                }

                // Add this step to the list
                testSteps.add(rowData);

                // Log the read step
                logger.info("Read step: TestCase='{}', Action='{}', LocatorType='{}', LocatorValue='{}', Value='{}'",
                        rowData[0], rowData[1], rowData[2], rowData[3], rowData[4]);
            }

        } catch (Exception e) {
            logger.error("❌ Error reading Excel file '{}': {}", EXCEL_PATH, e.getMessage());
        }

        return testSteps;
    }
}
