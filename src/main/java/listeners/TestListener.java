package listeners;

import com.aventstack.extentreports.Status;

import drivers.DriverManager;

import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentReportManager;

public class TestListener implements ITestListener {

    WebDriver driver = DriverManager.getDriver();

    @Override
    public void onTestStart(ITestResult result) {
        // Start the test in Extent Report
        ExtentReportManager.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log success in Extent Report
        ExtentReportManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Capture the screenshot on failure
        String screenshotPath = captureScreenshot();

        // Attach the screenshot to the Extent report
        ExtentReportManager.getTest().fail("Test failed due to: " + result.getThrowable())
                .addScreenCaptureFromPath(screenshotPath);

        // Log additional failure details
        ExtentReportManager.getTest().fail("Test failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log skipped tests
        ExtentReportManager.getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onStart(ITestContext context) {
        // Start the test suite
        ExtentReportManager.initReports();
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the report after all tests are done
        ExtentReportManager.flushReports();
    }

    // Method to capture screenshot
    private String captureScreenshot() {
        String screenshotPath = "";
        try {
            // Capture screenshot as a file
            screenshotPath = "path/to/screenshots/" + System.currentTimeMillis() + ".png";
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // Save screenshot file to the specified path
            FileHandler.copy(screenshotFile, new File(screenshotPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenshotPath;
    }
}
