package tests;

import com.applitools.eyes.BatchInfo;
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
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class TestBase {

    protected Configuration config;
    protected WebDriver webDriver;
    protected VisualGridRunner runner;

    @BeforeSuite
    public void setUpConfiguration() {
        config = new Configuration();
        config.setApiKey("1dQ54JBEoYe10842EP5MHH102w8456LODkkByU101mzWBv4Wo110");
        config.addBrowser(1200, 700, BrowserType.CHROME);
        config.addBrowser(1200, 700, BrowserType.FIREFOX);
        config.addBrowser(1200, 700, BrowserType.EDGE_CHROMIUM);
        config.addBrowser(768, 700, BrowserType.CHROME);
        config.addBrowser(768, 700, BrowserType.FIREFOX);
        config.addBrowser(768, 700, BrowserType.EDGE_CHROMIUM);
        config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
        config.setBatch(new BatchInfo("UFG Hackathon 3"));

        WebDriverManager.chromedriver().setup();
    }

    protected VisualGridRunner createGrid() {
        runner = new VisualGridRunner(10);
        return runner;

    }

    protected WebDriver createDriver() {
        webDriver = new ChromeDriver();
        return webDriver;
    }

    /**
     * Method that gets invoked after test.
     * Dumps browser log and
     * Closes the browser
     */
    @AfterMethod
    public void tearDown(ITestResult result) throws Exception {
        webDriver.quit();
        TestResultsSummary testResults = runner.getAllTestResults(false);
        System.out.println(testResults);
    }

}
