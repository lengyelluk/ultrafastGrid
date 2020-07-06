package tests;

import config.BrowserType;
import config.DeviceType;
import config.RunType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;

public class BaseTest {

    protected RunType runType;
    protected ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    protected ThreadLocal<String> sessionId = new ThreadLocal<>();
    //replace with your credentials, only free trial account without free minutes
    protected final String username = "lukaslengyel2";
    protected final String accesskey = "51NMnxDitMEuWxC1yyJp";

    protected WebDriver getWebDriver() {
        return threadDriver.get();
    }

    /**
     * it provides browser configuration (browser type, device => dimensions and run type)
     * Default run type is local, but it is possible to run tests in Browserstack (-DrunType=BROWSERSTACK)
     */
    @DataProvider(name = "browsers", parallel = true)
    public static Object[][] browserDataProvider(Method beforeMethod) {
        //default run type set to Browserstack
        RunType runType = RunType.BROWSERSTACK;
        if(System.getProperty("runType") != null) {
            runType = RunType.valueOf(System.getProperty("runType").toUpperCase());
        }

        return new Object[][] {
                new Object[]{BrowserType.CHROME, "latest", DeviceType.LAPTOP, runType},
                new Object[]{BrowserType.FIREFOX, "latest", DeviceType.LAPTOP, runType},
                new Object[]{BrowserType.EDGE, "latest", DeviceType.LAPTOP, runType},
                new Object[]{BrowserType.CHROME, "latest", DeviceType.TABLET, runType},
                new Object[]{BrowserType.FIREFOX, "latest", DeviceType.TABLET, runType},
                new Object[]{BrowserType.EDGE, "latest", DeviceType.TABLET, runType},
                new Object[]{BrowserType.CHROME, "latest", DeviceType.MOBILE, runType},
        };
    }

    /**
     * create driver that is used to automate the browser based on specified parameters
     * Note: browser version is not used as simply latest version can be used
     * @param browser - BrowserType enum specifying the browser
     * @param browserVersion - not implemented
     * @param device - DeviceType enum specifying width of viewport as height was set as constant in instructions
     * @param testName - name of the test that will be executed using the driver
     * @param runType - either local or remote = browserstack
     */
    protected void createDriver(BrowserType browser, String browserVersion, DeviceType device, String testName, RunType runType) {
        this.runType = runType;
        MutableCapabilities capabilities = new MutableCapabilities();
        Dimension dimension = new Dimension(device.getDeviceWidth(), 700);
        if(browser == BrowserType.CHROME) {
            capabilities = new ChromeOptions();
        } else if(browser == BrowserType.FIREFOX) {
            capabilities = new FirefoxOptions();
        } else if(browser == BrowserType.EDGE) {
            capabilities = new EdgeOptions();
        }

        switch(runType) {
            case LOCAL:
                createLocalDriver(browser, capabilities, dimension);
                break;
            case BROWSERSTACK:
                capabilities.setCapability("os", "Windows");
                capabilities.setCapability("os_version", "10");
                capabilities.setCapability("browser", browser.getBrowserName());
                capabilities.setCapability("build", "Traditional Tests V2");
                capabilities.setCapability("name", testName);
                capabilities.setCapability("browserstack.local", "false");
                capabilities.setCapability("browserstack.selenium_version", "3.141.59");
                createBrowserstackDriver(browser, capabilities, dimension, testName);
                break;
        }
    }

    /**
     * it opens browser of choice based on browserType locally and set the dimensions based on deviceType
     * @param browser
     * @param capabilities
     * @param dimension
     */
    protected void createLocalDriver(BrowserType browser, MutableCapabilities capabilities, Dimension dimension) {
        if (browser == BrowserType.CHROME) {
            threadDriver.set(new ChromeDriver((ChromeOptions) capabilities));
        } else if (browser == BrowserType.FIREFOX) {
            threadDriver.set(new FirefoxDriver((FirefoxOptions) capabilities));
        } else if (browser == BrowserType.EDGE) {
            threadDriver.set(new EdgeDriver((EdgeOptions) capabilities));
        }
        threadDriver.get().manage().window().setSize(dimension);
    }

    /**
     * it opens browser of choice based on browserType in browserstack and set the dimensions based on deviceType
     * credentials must be either added manually to code or to environment variables - TO DO!
     * @param browserType
     * @param capabilities
     * @param dimension
     * @param testName
     */
    protected void createBrowserstackDriver(BrowserType browserType, MutableCapabilities capabilities, Dimension dimension, String testName) {
        String BROWSER_STACK_REMOTE_URL = "https://" + username + ":" + accesskey + "@hub-cloud.browserstack.com/wd/hub";
        try {
            threadDriver.set(new RemoteWebDriver(new URL(BROWSER_STACK_REMOTE_URL), capabilities));
            threadDriver.get().manage().window().setSize(dimension);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        sessionId.set(((RemoteWebDriver)getWebDriver()).getSessionId().toString());
    }

    /**
     * using WebDriverManager to effortlessly set drivers for all three browsers
     * can be replaced by pointing to drivers stored locally if needed
     */
    @BeforeSuite
    public void setUpBeforeSuite() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }

    /**
     * used to mark a test as either passed or fail in Browserstack
     * Used as separate method in case other providers (e.g. saucelabs) will be added to the project
     * @param result
     */
    @AfterMethod
    public void afterTest(ITestResult result) {
        try {
            if(runType.equals(RunType.BROWSERSTACK)) {
                try {
                    mark(result);
                } catch (URISyntaxException e) {

                } catch (IOException ex) {

                }
            }
        } finally {
            threadDriver.get().quit();
        }
    }

    /**
     * copy the test results created in module root folder to the project root folder as per instructions
     */
    @AfterSuite
    public void copyResultsToRootFolder() {
        File source = new File("Traditional-V2-TestResults.txt");
        File dest = new File("../Traditional-V2-TestResults.txt");
        try {
            Files.copy(source.toPath(), dest.toPath());
        } catch (IOException e) {
            System.out.println("Exception copying file to root folder");
        }
    }

    /**
     * mark the test in BrowserStack as passed or failed based on results of assertions in the test
     * @param result
     * @throws URISyntaxException
     * @throws IOException
     */
    protected void mark(ITestResult result) throws URISyntaxException, IOException {
        String id = ((RemoteWebDriver)getWebDriver()).getSessionId().toString();
        URI uri = new URI("https://" + username + ":" + accesskey + "@api.browserstack.com/automate/sessions/" + id + ".json");
        HttpPut putRequest = new HttpPut(uri);

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add((new BasicNameValuePair("status", result.isSuccess() ? "passed" : "failed")));
        nameValuePairs.add((new BasicNameValuePair("reason", "")));
        putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpClientBuilder.create().build().execute(putRequest);
    }
}
