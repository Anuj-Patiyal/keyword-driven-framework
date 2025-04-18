package tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import utils.ExcelUtils;
import keywords.ActionKeywords;
import org.testng.ITestContext;

import java.util.List;

public class KeywordDrivenTest {

    private static final Logger logger = LogManager.getLogger(KeywordDrivenTest.class);

    @BeforeClass
    @Parameters({"browser"}) // This allows passing the browser value from TestNG XML
    public void setupReport(String browser, ITestContext context) {
        // Initialize the extent report
        ExtentReportManager.initReports();
        ExtentReportManager.createTest("TC_01 - Saucedemo Login Test");
        
        // Log browser being used for this test execution
        logger.info("Running tests on browser: " + browser);
        
        // Open the specified browser
        ActionKeywords.openBrowser(browser);
    }

    @Test
    public void executeKeywordsFromExcel() {
        List<String[]> steps = ExcelUtils.getTestSteps("TestSteps");

        for (String[] step : steps) {
            String testCase = step[0];
            String action = step[1];
            String locatorType = step[2];
            String locatorValue = step[3];
            String value = step[4];

            // Skip any empty step (such as end row in Excel)
            if (action == null || action.trim().isEmpty()) {
                logger.warn("⚠ Skipping empty row or missing action keyword.");
                ExtentReportManager.getTest().warning("⚠ Skipping empty row or missing action keyword.");
                continue;
            }

            logger.info("Executing Step -> TestCase: {}, Action: {}, LocatorType: {}, LocatorValue: {}, Value: {}",
                    testCase, action, locatorType, locatorValue, value);

            ExtentReportManager.getTest().info("🔹 Executing Action: `" + action + "` | LocatorType: `" + locatorType + "` | LocatorValue: `" + locatorValue + "` | Value: `" + value + "`");

            try {
                switch (action.toLowerCase()) {
                    case "openbrowser":
                        ActionKeywords.openBrowser(value);
                        ExtentReportManager.getTest().pass("✅ Browser launched: " + value);
                        break;

                    case "navigate":
                        ActionKeywords.navigate(value);
                        ExtentReportManager.getTest().pass("✅ Navigated to URL: " + value);
                        break;

                    case "click":
                        ActionKeywords.click(getBy(locatorType, locatorValue));
                        ExtentReportManager.getTest().pass("✅ Clicked on element: " + locatorType + " = " + locatorValue);
                        break;

                    case "sendkeys":
                        ActionKeywords.sendKeys(getBy(locatorType, locatorValue), value);
                        ExtentReportManager.getTest().pass("✅ Sent text: '" + value + "' to element: " + locatorType + " = " + locatorValue);
                        break;

                    case "closebrowser":
                        ActionKeywords.closeBrowser();
                        ExtentReportManager.getTest().pass("✅ Browser closed successfully.");
                        break;

                    case "verifytitle":
                        ActionKeywords.verifyTitle(value);
                        ExtentReportManager.getTest().pass("✅ Title matched: '" + value + "'");
                        break;

                    default:
                        logger.warn("❌ Unknown action keyword: {}", action);
                        ExtentReportManager.getTest().warning("❌ Unknown action keyword: " + action);
                }

            } catch (Exception e) {
                logger.error("❌ Error executing action '{}': {}", action, e.getMessage());
                ExtentReportManager.getTest().fail("❌ Exception during action '" + action + "': " + e.getMessage());
            }
        }
    }

    @AfterClass
    public void tearDownReport() {
        ExtentReportManager.flushReports();
        logger.info("📄 Extent report generated successfully.");
    }

    /**
     * Converts locator type and value into Selenium By object.
     */
    private By getBy(String type, String value) {
        switch (type.toLowerCase()) {
            case "id": return By.id(value);
            case "name": return By.name(value);
            case "xpath": return By.xpath(value);
            case "css": return By.cssSelector(value);
            case "classname": return By.className(value);
            case "tagname": return By.tagName(value);
            case "linktext": return By.linkText(value);
            case "partiallinktext": return By.partialLinkText(value);
            default:
                logger.warn("❌ Invalid locator type: {}", type);
                ExtentReportManager.getTest().warning("❌ Invalid locator type: " + type);
                throw new IllegalArgumentException("❌ Invalid locator type: " + type);
        }
    }
}
