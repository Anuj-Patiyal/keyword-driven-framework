package drivers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    // Logger instance
    private static final Logger logger = LogManager.getLogger(DriverManager.class);

    // WebDriver instance
    private static WebDriver driver;

    /**
     * Initializes the WebDriver based on the browser type.
     * Supported: "chrome", "firefox"
     *
     * @param browser the name of the browser to launch
     */
    public static void initializeDriver(String browser) {
        try {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                logger.info("Initialized Chrome browser.");
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                logger.info("Initialized Firefox browser.");
            } else {
                logger.error("Unsupported browser type: {}", browser);
                throw new RuntimeException("Browser not supported: " + browser);
            }

            // Maximize and setup default config
            driver.manage().window().maximize();
            logger.info("Browser window maximized.");

        } catch (Exception e) {
            logger.error("Failed to initialize browser '{}': {}", browser, e.getMessage());
        }
    }

    /**
     * Provides access to the current WebDriver instance.
     *
     * @return the active WebDriver
     */
    public static WebDriver getDriver() {
        return driver;
    }

    /**
     * Quits the browser and cleans up WebDriver.
     */
    public static void quitDriver() {
        try {
            if (driver != null) {
                driver.quit();
                logger.info("WebDriver quit successfully.");
            }
        } catch (Exception e) {
            logger.error("Error while quitting the WebDriver: {}", e.getMessage());
        }
    }
}
