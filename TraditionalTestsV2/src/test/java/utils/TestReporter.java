package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class TestReporter {

    private List<String> testResult;

    public TestReporter() {
        if(testResult == null) {
            testResult = new ArrayList<>();
        }
    }

    public void collectTestResults(String task, String testName, String details, String browser, int width,
                                    String deviceName, boolean comparisonResult) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Traditional-V2-TestResults.txt", true))) {
            //all IDs contain a number but not class selectors
            String result = "";
            if(task.equalsIgnoreCase("Task 1") || task.equalsIgnoreCase("Task 3")) {
                if (details.matches(".*\\d+.*")) {
                    result = "Task: " + task + ", Test Name: " + testName + ", DOM Id: " + details + ", Browser: " + browser
                            + ", Viewport: " + width + " X 700, Device: " + deviceName + ", Status: " + (comparisonResult ? "Pass" : "Fail");
                } else {
                    result = "Task: " + task + ", Test Name: " + testName + ", Class: " + details + ", Browser: " + browser
                            + ", Viewport: " + width + " X 700, Device: " + deviceName + ", Status: " + (comparisonResult ? "Pass" : "Fail");
                }
            } else if(task.equalsIgnoreCase("Task 2")) {
                if (testName.contains("number")) {
                    result = "Task: " + task + ", Test Name: " + testName + ", Expected number: " + details + ", Browser: " + browser
                            + ", Viewport: " + width + " X 700, Device: " + deviceName + ", Status: " + (comparisonResult ? "Pass" : "Fail");
                } else {
                    result = "Task: " + task + ", Test Name: " + testName + ", Captured value: " + details + ", Browser: " + browser
                            + ", Viewport: " + width + " X 700, Device: " + deviceName + ", Status: " + (comparisonResult ? "Pass" : "Fail");
                }
            }
            writer.write(result);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error writing to report");
            e.printStackTrace();
        }
    }

    public void collectTestResults(String task, String testName, String details, String expectedValue,
                                   String capturedValue, String browser, int width,
                                   String deviceName, boolean comparisonResult) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Traditional-V2-TestResults.txt", true))) {
            String result = "";
            result = "Task: " + task + ", Test Name: " + testName + ", DOM Id: " + details + ", Expected value: " + expectedValue +
                    ", Captured value: " + capturedValue + ", Browser: " + browser
                        + ", Viewport: " + width + " X 700, Device: " + deviceName + ", Status: " + (comparisonResult ? "Pass" : "Fail");
            writer.write(result);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error writing to report");
            e.printStackTrace();
        }
    }

    public void collectTestResults(String task, String testName, String firstElementId, String secondElementId,
                                   String browser, int width, String deviceName, boolean comparisonResult) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Traditional-V2-TestResults.txt", true))) {
            String result = "";
            result = "Task: " + task + ", Test Name: " + testName + ", First DOM Id: " + firstElementId + ", Second DOM Id: " + secondElementId +
                    ", Browser: " + browser + ", Viewport: " + width + " X 700, Device: " + deviceName + ", Status: " + (comparisonResult ? "Pass" : "Fail");
            writer.write(result);
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error writing to report");
            e.printStackTrace();
        }
    }
}
