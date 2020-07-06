package tests;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.Navigation;
import pages.ProductDetailsPage;

import java.lang.reflect.Method;
import java.util.List;

public class ModernTests extends TestBase {

    private Eyes eyes;

    @Test(priority = 0)
    public void elementsVisibilityTests() {
    try {
        //create thread grid and thread driver
        VisualGridRunner runner = this.createGrid();
        WebDriver webDriver = this.createDriver();
        eyes = new Eyes(runner);
        eyes.setConfiguration(config);
        WebDriver driverFromEyes = eyes.open(webDriver, "Appli Fashion", "Task 1", new RectangleSize(800, 600));
        //open page
        Navigation navigation = new Navigation(driverFromEyes);
        MainPage mainPage = navigation.goToMainPage();
        //take screenshots
        eyes.check(Target.window().fully().withName("Cross-Device Elements Test"));

        // Call Close on eyes to let the server know it should display the results
        eyes.closeAsync();
        } finally {
            eyes.abortAsync();
        }
    }

    @Test(priority = 1)
    public void filterBlackShoesTest() {
        try {
            VisualGridRunner runner = this.createGrid();
            WebDriver webDriver = this.createDriver();
            eyes = new Eyes(runner);
            eyes.setConfiguration(config);
            WebDriver driverFromEyes = eyes.open(webDriver, "Appli Fashion", "Task 2", new RectangleSize(800, 600));

            Navigation navigation = new Navigation(driverFromEyes);
            MainPage mainPage = navigation.goToMainPage();
            mainPage.showBlackShoesOnly();
            eyes.check(Target.region(mainPage.getShoesGrid()).fully().withName("Filter Results"));

            // Call Close on eyes to let the server know it should display the results
            eyes.closeAsync();
        }
        finally {
            eyes.abortAsync();
        }
    }

    @Test(priority = 2)
    public void displayDetailsOfBlackShoesTest() {
        try {
            VisualGridRunner runner = this.createGrid();
            WebDriver webDriver = this.createDriver();
            eyes = new Eyes(runner);
            eyes.setConfiguration(config);
            WebDriver driverFromEyes = eyes.open(webDriver, "Appli Fashion", "Task 3", new RectangleSize(800, 600));

            Navigation navigation = new Navigation(driverFromEyes);
            MainPage mainPage = navigation.goToMainPage();
            mainPage.showBlackShoesOnly();
            ProductDetailsPage productDetailsPage = mainPage.accessFirstItemProductDetailsPage();
            eyes.check(Target.window().fully().withName("Product Details test"));

            // Call Close on eyes to let the server know it should display the result
            eyes.closeAsync();
        } finally {
            eyes.abortAsync();
        }
    }
}
