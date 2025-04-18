package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ExtentReportManager {

    private static ExtentReports extentReports;
    private static final ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();

    private static final Map<String, ExtentTest> testMap = new HashMap<>();

    // Generates filename with format ExtentReport_DDMMMYYYY_HHMMSS.html
    private static String getTimestampedReportName() {
        return "ExtentReport_" + new SimpleDateFormat("ddMMMyyyy_HHmmss").format(new Date()) + ".html";
    }

    public static void initReports() {
        if (extentReports == null) {
            String reportFile = System.getProperty("user.dir") + "/reports/" + getTimestampedReportName();
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportFile);
            reporter.config().setDocumentTitle("Automation Execution Report");
            reporter.config().setReportName("Keyword Driven Test Results");
            reporter.config().setTheme(Theme.STANDARD);
            reporter.config().setEncoding("utf-8");

            extentReports = new ExtentReports();
            extentReports.attachReporter(reporter);

            // Capture dynamic system and environment info
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("OS Version", System.getProperty("os.version"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
    }

    public static synchronized ExtentTest createTest(String testName) {
        ExtentTest test = extentReports.createTest(testName);
        extentTestThreadLocal.set(test);
        testMap.put(testName, test);
        return test;
    }

    public static synchronized ExtentTest getTest() {
        return extentTestThreadLocal.get();
    }

    public static void flushReports() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }

    public static void unload() {
        extentTestThreadLocal.remove();
    }
}

