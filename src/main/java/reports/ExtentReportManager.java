package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import drivers.DriverManager;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

            sparkReporter.config().setDocumentTitle("Automation Test Report");
            sparkReporter.config().setReportName("Keyword Driven Framework - Execution Report");
            sparkReporter.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);

            // System info
            extent.setSystemInfo("Project Name", "Keyword Driven Framework");
            extent.setSystemInfo("Tester", "Anuj Kumar");

            try {
                String os = System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")";
                String javaVersion = System.getProperty("java.version");
                String machineName = InetAddress.getLocalHost().getHostName();

                extent.setSystemInfo("Operating System", os);
                extent.setSystemInfo("Machine Name", machineName);
                extent.setSystemInfo("Java Version", javaVersion);
                extent.setSystemInfo("User", System.getProperty("user.name"));

                // WebDriver details (if available)
                if (DriverManager.getDriver() != null) {
                    Capabilities caps = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
                    String browserName = caps.getBrowserName();
                    String browserVersion = caps.getBrowserVersion();

                    extent.setSystemInfo("Browser", browserName);
                    extent.setSystemInfo("Browser Version", browserVersion);
                }

            } catch (UnknownHostException e) {
                extent.setSystemInfo("Machine", "Unknown (Could not fetch)");
            }
        }
        return extent;
    }
}
