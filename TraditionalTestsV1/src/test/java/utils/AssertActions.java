package utils;

import config.BrowserType;
import config.DeviceType;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class AssertActions {

    public SoftAssert assertions;
    private List<String> testResult;

    public AssertActions() {
        assertions = new SoftAssert();
        testResult = new ArrayList<>();
    }

    public void softAssertTrue(boolean expectedCondition, BrowserType browserType, DeviceType deviceType) {
        System.out.println("doing assertions");
        assertions.assertTrue(expectedCondition);
        System.out.println("assertions done");
    }

    public void assertTrue(boolean expectedCondition) {
        Assert.assertTrue(expectedCondition);
    }

    public void softAssertAll() {
        assertions.assertAll();
    }

    public void softAssertTrue(boolean result) {
        TestReporter testReporter = new TestReporter();
        //do I need it?
        //testReporter.hackathonReporter();
        assertions.assertTrue(result);
    }

    public void collectTestResults(String task, String testName, String domId, String browser, int width,
                                   String deviceName, boolean comparisonResult) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Traditional-V1-TestResults.txt", true))) {
            String result = "Task: " + task + ", Test Name: " + testName + ", DOM Id: " + domId + ", Browser: " + browser
                    + ", Viewport: " + width + "X700, Device: " + deviceName + ", Status: " + (comparisonResult ? "Pass" : "Fail");
            writer.write(result);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error writing to report");
            e.printStackTrace();
        }
    }

}
