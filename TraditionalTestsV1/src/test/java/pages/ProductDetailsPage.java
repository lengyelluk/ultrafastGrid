package pages;

import config.BrowserType;
import config.DeviceType;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestReporter;

public class ProductDetailsPage {

    private final String URL = "https://demo.applitools.com/gridHackathonProductDetailsV1.html?id=1";
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private TestReporter testReporter;

    public String getURL() {
        return URL;
    }

    public ProductDetailsPage() {}

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        actions = new Actions(driver);
        testReporter = new TestReporter();
    }

    @FindBy(id = "IMG____9")
    private WebElement logo;

    @FindBy(id = "A__wishlist__52")
    private WebElement wishlistIcon;

    @FindBy(id = "A__accesslink__56")
    private WebElement profileIcon;

    @FindBy(id = "A__cartbt__49")
    private WebElement cartIcon;

    @FindBy(id = "SMALL____84")
    private WebElement skuNumber;

    @FindBy(id = "new_price")
    private WebElement newPrice;

    @FindBy(id = "old_price")
    private WebElement oldPrice;

    @FindBy(id = "DIV__customsele__92")
    private WebElement sizeSelectedDropdown;

    @FindBy(id = "I__iconstar__81")
    private WebElement fifthReviewStar;

    @FindBy(id = "EM__ratingcoun__82")
    private WebElement reviewTextV2;

    @FindBy(id = "EM____82")
    private WebElement reviewTextV1;

    @FindBy(id = "A__btn__114")
    private WebElement addToCartButton;

    @FindBy(id = "DIV__numbersrow__102")
    private WebElement quantityBox;

    /**
     * used to check if two specified elements are overlapping each other. In other words if they at least share the same
     * point in horizontal or vertical axis.
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param firstElement - first WebElement which position against second element will be verified
     * @param secondElement - second WebElement which position against first element will be verified
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @return true if there is no overlapping between two specified elements. If there is overlap, it returns false.
     */
    public boolean checkOverlappingOfIcons(String task, String testName, WebElement firstElement, WebElement secondElement,
                                           BrowserType browserType, DeviceType deviceType) {
        Rectangle rec1 = firstElement.getRect();
        Rectangle rec2 = secondElement.getRect();

        //check if there is an overlap between two chosen elements
        boolean overlap = !(rec1.getX() + rec1.getWidth() <= rec2.getX() ||
                rec1.getX() >= rec1.getX() + rec2.getWidth() ||
                rec1.getY() + rec1.getHeight() <= rec2.getY() ||
                rec1.getY() >= rec2.getY() + rec2.getHeight());
        //return opposite as overlap is never expected on the page, no need to add shouldBeOverlapped
        testReporter.collectTestResults(task, testName, firstElement.getAttribute("id"), secondElement.getAttribute("id"), //to be changed
                browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), !overlap);

        return !overlap;
    }

    /**
     * used to check if displayed default option (e.g. value in dropdown which is shown before user makes any selection).
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param element - WebElement which default value will be verified
     * @param expectedValue - String of default value
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @return true if captured value is the same as expected value. Otherwise, it returns false
     */
    public boolean checkDefaultDropdownValue(String task, String testName, WebElement element, String expectedValue,
                                             BrowserType browserType, DeviceType deviceType) {
        WebElement selectedDropdown = element.findElement(By.className("current"));
        String capturedValue = selectedDropdown.getText().trim();
        boolean result = capturedValue.equals(expectedValue);
        testReporter.collectTestResults(task, testName, element.getAttribute("id"), expectedValue, capturedValue//to be changed
                , browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), result);
        return result;
    }

    /**
     * used to check if specified css attribute contains the expected value. It can be used for verification of visual appearance of element.
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param element - WebElement which css value will be verified
     * @param cssAttribute - css attribute that should be verified (e.g. display)
     * @param expectedValue - Value of specified css attributed (e.g. none)
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @return true if captured value is the same as expected value. Otherwise, it returns false
     */
    public boolean checkCssAttribute(String task, String testName, WebElement element, String cssAttribute,
                                     String expectedValue, BrowserType browserType, DeviceType deviceType) {
        String capturedValue = element.getCssValue(cssAttribute);
        System.out.println("Captured value: " + capturedValue);
        boolean result = capturedValue.contains(expectedValue);
        testReporter.collectTestResults(task, testName, element.getAttribute("id"), expectedValue, capturedValue//to be changed
                , browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), result);
        return result;
    }

    /**
     * used to check if displayed value (e.g. value of price label).
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param element - WebElement which value will be verified
     * @param expectedValue - String of default value
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @return true if captured value is the same as expected value. Otherwise, it returns false
     */
    public boolean checkExpectedValue(String task, String testName, WebElement element, String expectedValue,
                                      BrowserType browserType, DeviceType deviceType) {
        String capturedValue = element.getText().trim();
        boolean result = capturedValue.equals(expectedValue);
        testReporter.collectTestResults(task, testName, element.getAttribute("id"), expectedValue, capturedValue//to be changed
                , browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), result);
        return result;
    }

    /**
     * use to check visibility of specified element. Parameter shouldBeVisible enables to use the it for two cases:
     * 1, if element should be visible, it returns false if it is not visible. If it is visible, it returns true.
     * 2, if element should not be visible, it returns true if it is visible. If it is not visible, it returns false.
     * Difference between implementation in ProductDetailsPage when comparing to MainPage is that the element is also considered
     * as not visible, if its color is white. In MainPage this is not verified.
     *
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param element - WebElement which visibility will be verified
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @param shouldBeVisible - Boolean if the element should be visible or not
     * @return - true or false as described above
     */
    public boolean checkElementVisibility(String task, String testName, WebElement element, BrowserType browserType,
                                          DeviceType deviceType, boolean shouldBeVisible) {
        boolean isDisplayNotNone = element.isDisplayed();
        String whiteColor = "rgb(255, 255, 255)";
        boolean isWhite = element.getCssValue("color").equals(whiteColor) ? true : false;
        boolean result = isDisplayNotNone && !isWhite == shouldBeVisible;
        testReporter.collectTestResults(task, testName, element.getAttribute("id")//to be changed
                , browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), result);
        return result;
    }

    //to verify that icons are not overlapping in the header
    public boolean checkIconsOverlappingInHeader(BrowserType browserType, DeviceType deviceType) {
        boolean isProfileOverlappingWishlistIcon = checkOverlappingOfIcons("Task 3", "Check profile icon does not overlap wishlist icon",
                profileIcon, wishlistIcon, browserType, deviceType);
        boolean isWishlistIconOverlappingCartIcon = checkOverlappingOfIcons("Task 3", "Check wishlist icon does not overlap cart icon",
                wishlistIcon, cartIcon, browserType, deviceType);
        return isProfileOverlappingWishlistIcon && isWishlistIconOverlappingCartIcon;
    }

    //to verify that review starts and review text are not overlapping
    public boolean checkReviewOverlapping(BrowserType browserType, DeviceType deviceType) {
        WebElement reviewTex = driver.getCurrentUrl().contains("V1") ? reviewTextV1 : reviewTextV2;
        return checkOverlappingOfIcons("Task 3", "Check review stars do not overlap with review text rating",
                fifthReviewStar, reviewTex, browserType, deviceType);
    }

    //to verify that sku number of the product is displayed
    public boolean checkSkuNumberVisibility(BrowserType browserType, DeviceType deviceType) {
        //this is fine just because sku number is in DOM without display: none even in "buggy" version
        wait.until(ExpectedConditions.visibilityOf(skuNumber));
        return checkElementVisibility("Task 3", "Sku number is displayed",
                skuNumber, browserType, deviceType, true);
    }

    //to verify that new price has the expected value
    public boolean checkNewPriceValue(String expectedValue, BrowserType browserType, DeviceType deviceType) {
        return checkExpectedValue("Task 3", "New price has the expected value",
                newPrice, expectedValue, browserType, deviceType);
    }

    //to verify that default size of shoes in the dropdown is the same as expected
    public boolean checkDefaultShoesSize(String expectedValue, BrowserType browserType, DeviceType deviceType) {
        return checkDefaultDropdownValue("Task 3", "Default shoes size has the expected value",
                sizeSelectedDropdown, expectedValue, browserType, deviceType);
    }

    //to verify that the old price is crossed out and color is grey. It returns true only if both conditions are met
    public boolean checkOldPriceStyle(BrowserType browserType, DeviceType deviceType) {
        boolean isOldPriceStrikethrough = checkCssAttribute("Task 3", "Check the old price is strikethrough",
                oldPrice, "text-decoration", "line-through", browserType, deviceType);
        boolean isOldPriceGrayedOut = checkCssAttribute("Task 3", "Check the old price is grayed out",
                oldPrice, "text-decoration", "rgb(153, 153, 153)", browserType, deviceType);
        return isOldPriceStrikethrough && isOldPriceGrayedOut;
    }

    //to verify that the button "add to cart" does not overlap with quantity box
    public boolean checkAddToCarButtonAndQuantityBoxNotOverlapping(BrowserType browserType, DeviceType deviceType) {
        return checkOverlappingOfIcons("Task 3", "Check add to cart button do not overlap with quantity box",
                addToCartButton, quantityBox, browserType, deviceType);
    }
}
