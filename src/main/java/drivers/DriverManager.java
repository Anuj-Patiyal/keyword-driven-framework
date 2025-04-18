package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    // ThreadLocal to manage separate WebDriver instances for parallel tests
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    /**
     * Initializes the WebDriver based on the passed browser type.
     * @param browser The browser to initialize (e.g., "chrome", "firefox")
     */
    public static void initializeDriver(String browser) {
        if (driverThreadLocal.get() == null) {
            WebDriver driver = createDriver(browser);
            driverThreadLocal.set(driver);
        }
    }

    /**
     * Gets the WebDriver for the current thread.
     * @return WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    /**
     * Quits the WebDriver for the current thread.
     */
    public static void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
        }
    }

    /**
     * Creates a WebDriver instance based on the browser type.
     * @param browser The browser to initialize (e.g., "chrome", "firefox")
     * @return WebDriver instance
     */
    private static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                return new ChromeDriver();
            case "firefox":
                return new FirefoxDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}
