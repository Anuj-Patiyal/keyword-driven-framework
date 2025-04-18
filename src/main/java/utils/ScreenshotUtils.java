package utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

    // Method to capture screenshot for failed test case
    public static String captureScreenshot(WebDriver driver, String testName) {
        // Get current timestamp
        String timestamp = new SimpleDateFormat("ddMMMyyyy_HHmmss").format(new Date());

        // Create screenshot filename
        String fileName = "failedScreenshots/" + testName + "_" + timestamp + ".png";

        // Capture screenshot
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        // Copy the screenshot to the desired location
        File destination = new File(fileName);
        try {
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;  // Return the path of the saved screenshot
    }
}
