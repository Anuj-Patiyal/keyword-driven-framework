package tests;

import keywords.ActionKeywords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.ExcelUtils;

import org.testng.annotations.Test;

import java.util.List;

public class KeywordDrivenTest {

    // Logger for this test class
    private static final Logger logger = LogManager.getLogger(KeywordDrivenTest.class);

    @Test
    public void executeKeywordsFromExcel() {
        // Read steps from Excel sheet "TestSteps"
        List<String[]> steps = ExcelUtils.getTestSteps("TestSteps");

        for (String[] step : steps) {
            String testCase = step[0];
            String action = step[1];
            String locatorType = step[2];
            String locatorValue = step[3];
            String value = step[4];

            logger.info("Executing Step -> TestCase: {}, Action: {}, LocatorType: {}, LocatorValue: {}, Value: {}",
                    testCase, action, locatorType, locatorValue, value);

            try {
                switch (action.toLowerCase()) {
                    case "openbrowser":
                        ActionKeywords.openBrowser(value); // browser name
                        break;

                    case "navigate":
                        ActionKeywords.navigate(value); // URL
                        break;

                    case "click":
                        ActionKeywords.click(getBy(locatorType, locatorValue));
                        break;

                    case "sendkeys":
                        ActionKeywords.sendKeys(getBy(locatorType, locatorValue), value);
                        break;

                    case "closebrowser":
                        ActionKeywords.closeBrowser();
                        break;

                    default:
                        logger.warn("Unknown action keyword: {}", action);
                }
            } catch (Exception e) {
                logger.error("Error executing step for action '{}': {}", action, e.getMessage());
            }
        }
    }

    /**
     * Converts string-based locator type and value into Selenium By object.
     *
     * @param type  id, name, xpath, css, etc.
     * @param value locator value
     * @return By locator
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
                logger.warn("Invalid locator type: {}", type);
                throw new IllegalArgumentException("Invalid locator type: " + type);
        }
    }
}
