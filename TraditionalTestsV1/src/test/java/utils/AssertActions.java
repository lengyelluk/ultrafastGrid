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
        assertions.assertTrue(result);
    }
}
