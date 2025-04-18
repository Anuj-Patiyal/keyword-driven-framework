package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import drivers.DriverManager;

public class WaitUtils {

    /**
     * Waits for an element to be visible on the page.
     * @param locator The locator for the element.
     * @return The WebElement that is visible.
     */
    public static WebElement waitForVisibility(By locator) {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be clickable on the page.
     * @param locator The locator for the element.
     * @return The WebElement that is clickable.
     */
    public static WebElement waitForClickability(By locator) {
        WebDriver driver = DriverManager.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
