package tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.TestResultContainer;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class TestBase {

    //change API KEY for your keys
    protected final String API_KEY = "lOAhnlAHAEOJW110z64rNtKcbCACeE110pAWrbS9TNUbELs110";
    protected Configuration config;
    protected WebDriver webDriver;
    protected VisualGridRunner runner;
    protected Eyes eyes;

    @BeforeSuite
    public void setUpConfiguration() {
        runner = new VisualGridRunner(10);
        config = new Configuration();
        config.setApiKey(API_KEY);
        config.addBrowser(1200, 700, BrowserType.CHROME);
        config.addBrowser(1200, 700, BrowserType.FIREFOX);
        config.addBrowser(1200, 700, BrowserType.EDGE_CHROMIUM);
        config.addBrowser(768, 700, BrowserType.CHROME);
        config.addBrowser(768, 700, BrowserType.FIREFOX);
        config.addBrowser(768, 700, BrowserType.EDGE_CHROMIUM);
        config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        config.setBatch(new BatchInfo("UFG Hackathon"));
        WebDriverManager.chromedriver().setup();
    }

    protected WebDriver createDriver() {
        webDriver = new ChromeDriver();
        return webDriver;
    }


    @AfterMethod
    public void afterEachTest(ITestResult result) {
        boolean testFailed = result.getStatus() == ITestResult.FAILURE;
        if (!testFailed) {
            eyes.closeAsync();
        } else {
            eyes.abortAsync();
        }
        webDriver.quit();
    }

    @AfterSuite
    public void afterTestSuite(ITestContext testContext) {
        //Wait until the test results are available and retrieve them
        TestResultsSummary allTestResults = runner.getAllTestResults(false);
        for (TestResultContainer result : allTestResults) {
            System.out.println(result);
        }
    }
}
