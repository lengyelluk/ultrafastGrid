package tests;

import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.Navigation;
import pages.ProductDetailsPage;

public class ModernTests extends TestBase {

    @Test(priority = 0)
    public void elementsVisibilityTests() {
        WebDriver webDriver = this.createDriver();
        eyes = new Eyes(runner);
        eyes.setConfiguration(config);
        WebDriver driverFromEyes = eyes.open(webDriver, "Appli Fashion", "Task 1", new RectangleSize(800, 600));
        //open page
        Navigation navigation = new Navigation(driverFromEyes);
        MainPage mainPage = navigation.goToMainPage();
        //take screenshots
        eyes.check(Target.window().fully().withName("Cross-Device Elements Test"));
    }

    @Test(priority = 1)
    public void filterBlackShoesTest() {
        WebDriver webDriver = this.createDriver();
        eyes = new Eyes(runner);
        eyes.setConfiguration(config);
        WebDriver driverFromEyes = eyes.open(webDriver, "Appli Fashion", "Task 2", new RectangleSize(800, 600));
        Navigation navigation = new Navigation(driverFromEyes);
        MainPage mainPage = navigation.goToMainPage();
        mainPage.showBlackShoesOnly();
        eyes.check(Target.region(mainPage.getShoesGrid()).fully().withName("Filter Results"));
    }

    @Test(priority = 2)
    public void displayDetailsOfBlackShoesTest() {
        WebDriver webDriver = this.createDriver();
        eyes = new Eyes(runner);
        eyes.setConfiguration(config);
        WebDriver driverFromEyes = eyes.open(webDriver, "Appli Fashion", "Task 3", new RectangleSize(800, 600));
        Navigation navigation = new Navigation(driverFromEyes);
        MainPage mainPage = navigation.goToMainPage();
        mainPage.showBlackShoesOnly();
        ProductDetailsPage productDetailsPage = mainPage.accessFirstItemProductDetailsPage();
        eyes.check(Target.window().fully().withName("Product Details test"));
    }
}
