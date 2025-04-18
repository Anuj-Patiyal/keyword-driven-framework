package keywords;

import drivers.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import java.time.Duration;

public class ActionKeywords {

    // Logger for logging each action
    private static final Logger logger = LogManager.getLogger(ActionKeywords.class);

    /**
     * Launches the browser instance.
     * @param browserType e.g., "chrome", "firefox"
     */
    public static void openBrowser(String browserType) {
        try {
            DriverManager.initializeDriver(browserType);
            logger.info("Browser launched: {}", browserType);
        } catch (Exception e) {
            logger.error("Failed to open browser: {}", e.getMessage());
        }
    }

    /**
     * Navigates to a specific URL.
     * @param url the website URL
     */
    public static void navigate(String url) {
        try {
            DriverManager.getDriver().get(url);
            DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            logger.info("Navigated to URL: {}", url);
        } catch (Exception e) {
            logger.error("Failed to navigate to URL '{}': {}", url, e.getMessage());
        }
    }

    /**
     * Sends text to an input field.
     * @param locator the WebElement locator
     * @param value the text to send
     */
    public static void sendKeys(By locator, String value) {
        try {
            WebElement element = DriverManager.getDriver().findElement(locator);
            element.clear();
            element.sendKeys(value);
            logger.info("Sent text '{}' to element: {}", value, locator);
        } catch (Exception e) {
            logger.error("Failed to send text to element '{}': {}", locator, e.getMessage());
        }
    }

    /**
     * Performs a click action on a web element.
     * @param locator the WebElement locator
     */
    public static void click(By locator) {
        try {
            DriverManager.getDriver().findElement(locator).click();
            logger.info("Clicked on element: {}", locator);
        } catch (Exception e) {
            logger.error("Failed to click on element '{}': {}", locator, e.getMessage());
        }
    }

    /**
     * Closes the browser window and quits the driver.
     */
    public static void closeBrowser() {
        try {
            DriverManager.quitDriver();
            logger.info("Browser closed successfully.");
        } catch (Exception e) {
            logger.error("Failed to close browser: {}", e.getMessage());
        }
    }
}
