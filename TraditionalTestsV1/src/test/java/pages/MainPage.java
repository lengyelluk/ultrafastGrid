package pages;

import config.BrowserType;
import config.DeviceType;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestReporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPage {

    private final String URL = "https://demo.applitools.com/gridHackathonV1.html";
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private TestReporter testReporter;

    public String getURL() {
        return URL;
    }

    public MainPage() {}

    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
        actions = new Actions(driver);
        testReporter = new TestReporter();
    }

    @FindBy(id = "IMG____9")
    private WebElement logo;

    @FindBy(id = "A__wishlist__52")
    private WebElement wishlistIcon;

    @FindBy(id = "STRONG____50")
    private WebElement cartIconItemsNumber;

    @FindBy(id = "INPUTtext____42")
    private WebElement searchBarTextField;

    @FindBy(id = "I__headericon__44")
    private WebElement searchButton;

    @FindBy(id = "I__tiviewgrid__202")
    private WebElement viewGridOptionV1;

    @FindBy(id = "I__tiviewgrid__203")
    private WebElement viewGridOptionV2;

    @FindBy(id = "I__tiviewlist__204")
    private WebElement viewListOptionV1;

    @FindBy(id = "I__tiviewlist__205")
    private WebElement viewListOptionV2;

    @FindBy(id = "ti-filter")
    private WebElement filterIcon;

    @FindBy(id = "SPAN____208")
    private WebElement filterIconTextV1;

    @FindBy(id = "SPAN____209")
    private WebElement filterIconTextV2;

    @FindBy(xpath = "//i[contains(@id, \"I__ticontrols\")]")
    private List<WebElement> addToCompareTooltips;

    @FindBy(xpath = "//i[contains(@id, \"I__tishopping\")]")
    private List<WebElement> addToCartTooltips;

    @FindBy(className = "ti-heart")
    private List<WebElement> addToFavoritesTooltips;

    @FindBy(className = "grid_item")
    private List<WebElement> items;

    @FindBy(xpath = "//h3[text()=\"Quick Links\"]")
    private WebElement quickLinks;

    @FindBy(id = "SPAN__checkmark__107")
    private WebElement blackShoesFilterCheckbox;

    @FindBy(id = "filterBtn")
    private WebElement filterButton;

    @FindBy(id = "product_grid")
    private WebElement shoesGrid;


    /**
     * use to check visibility of specified element. Parameter shouldBeVisible enables to use the it for two cases:
     * 1, if element should be visible, it returns false if it is not visible. If it is visible, it returns true.
     * 2, if element should not be visible, it returns true if it is visible. If it is not visible, it returns false.
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
        boolean isVisible = element.isDisplayed();
        boolean result = isVisible == shouldBeVisible;
        //adding the test result to the report
        testReporter.collectTestResults(task, testName, element.getAttribute("id")//do I want to get it from @FindBy
                 , browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), result);
        return result;
    }

    /** use to check visibility of elements that are descendants of target elements (e.g. tooltips).
     * In case that there are too many elements in the list and the same behaviour is expected for every element in the list,
     * param checkAllElements set to false can limit the verification to only one item from the list.
     * Parameter shouldBeVisible enables to use the it for two cases:
     *  1, if element should be visible, it returns false if it is not visible. If it is visible, it returns true.
     *  2, if element should not be visible, it returns true if it is visible. If it is not visible, it returns false.
     *
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param targetElements - List of WebElements which are the ancestors of elements which visibility will be verified
     * @param selectorClasses - Selectors of elements which visibility is verified
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @param shouldBeVisible - Boolean if the element should be visible or not
     * @param checkAllElements - Boolean if every target element from the list should be verified or only the first one
     * @return - true or false as described above
     */
    public boolean checkDescendantsVisibility(String task, String testName, List<WebElement> targetElements,
                                              List<String> selectorClasses, BrowserType browserType,
                                              DeviceType deviceType, boolean shouldBeVisible, boolean checkAllElements) {
        boolean tempResult = false;
        List<Boolean> result = new ArrayList<>();
        //loop through all items one by one
        for(WebElement e : targetElements) {
            for(String selectorClass : selectorClasses) {
                //if specific tooltip not visible, catch the exception
                try {
                    wait.until(ExpectedConditions.visibilityOf(e.findElement(By.className(selectorClass))));
                    //no exception, the element is visible
                    tempResult = true;
                } catch (TimeoutException exception) {
                    System.out.println("Element with class " + selectorClass + " not displayed");
                    //element is not displayed,
                    // if it should be visible => store false, if it should not be visible => store
                    result.add(!shouldBeVisible);
                    testReporter.collectTestResults(task, testName,
                            selectorClass, browserType.getBrowserName(), deviceType.getDeviceWidth(),
                            deviceType.getDeviceName(), !shouldBeVisible);
                }
                //if tooltip element found after hovering it is visible - looping to get DOM Id
                if(tempResult) {
                    //get tooltip to capture DOM Id
                    WebElement newElement = e.findElement(By.className(selectorClass));
                    //store the result - the element is visible, so use only shouldBeVisible
                    result.add(shouldBeVisible);
                    testReporter.collectTestResults(task, testName,
                            newElement.getAttribute("id"), browserType.getBrowserName(), deviceType.getDeviceWidth(),
                            deviceType.getDeviceName(), shouldBeVisible);
                }
            }
            //if only one target element should be verified, break the loop
            if(!checkAllElements) break;
        }
        //as true/false was correctly stored, return false if any condition fails
        return !result.contains(Boolean.FALSE);
    }

    /** use to check visibility of elements that are descendants of target elements (e.g. tooltips) after hovering over target element.
     * In case that there are too many elements in the list and the same behaviour is expected for every element in the list,
     * param checkAllElements set to false can limit the verification to only one item from the list.
     * Parameter shouldBeVisible enables to use the it for two cases:
     *  1, if element should be visible, it returns false if it is not visible. If it is visible, it returns true.
     *  2, if element should not be visible, it returns true if it is visible. If it is not visible, it returns false.
     *
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param targetElements - List of WebElements which are the ancestors of elements which visibility will be verified
     * @param selectorClasses - Selectors of elements which visibility is verified
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @param shouldBeVisible - Boolean if the element should be visible or not
     * @param checkAllElements - Boolean if every target element from the list should be verified or only the first one
     * @return - true or false as described above
     */
    public boolean checkDescendantsVisibilityAfterHover(String task, String testName, List<WebElement> targetElements,
                                                        List<String> selectorClasses, BrowserType browserType,
                                                        DeviceType deviceType, boolean shouldBeVisible, boolean checkAllElements) {
        boolean tempResult = false;
        List<Boolean> result = new ArrayList<>();
        //scroll to first element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //loop through all items one by one
        for(WebElement e : targetElements) {
            //scroll to item, so hover action can be executed
            js.executeScript("arguments[0].scrollIntoView(true);", e);
            actions.moveToElement(e).build().perform();
            //check each tooltip under one item
            for(String selectorClass : selectorClasses) {
                //if specific tooltip not visible, catch the exception
                try {
                    wait.until(ExpectedConditions.visibilityOf(e.findElement(By.className(selectorClass))));
                    //no exception, element is visible
                    tempResult = true;
                } catch (TimeoutException exception) {
                    System.out.println("Element with class " + selectorClass + " not displayed after hovering");
                    //element is not visible, so opposite of shouldBeVisible can be stored
                    result.add(!shouldBeVisible);
                    testReporter.collectTestResults(task, testName,
                            selectorClass, browserType.getBrowserName(), deviceType.getDeviceWidth(),
                            deviceType.getDeviceName(), false);
                }
                //if tooltip element found after hovering it is visible
                if(tempResult) {
                    //get tooltip element displayed after hovering - to capture specific Dom Id
                    WebElement newElement = e.findElement(By.className(selectorClass));
                    //store the result by using shouldBeVisible
                    result.add(shouldBeVisible);
                    testReporter.collectTestResults(task, testName,
                            newElement.getAttribute("id"), browserType.getBrowserName(), deviceType.getDeviceWidth(),
                            deviceType.getDeviceName(), true);
                }
            }
            //if only one target element should be verified, break the loop
            if(!checkAllElements) break;
        }
        //as true/false was correctly stored, return false if any condition fails
        return !result.contains(Boolean.FALSE);
    }

    /**
     * to verify if a chosen section in footer is collapsed or expanded. It can be use for both cases:
     * 1, if section should be collapsed, it returns false if it is expanded. If it is collapsed, it returns true.
     * 2, if section should not be collapsed, it returns true if it is expanded. If it is collapsed, it returns false.
     * @param task - Name of task that is printed in test report
     * @param testName - Name of test that is printed in test report
     * @param element - WebElement which will be verified
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @param shouldBeCollapsed - boolean if the section should be collapsed or not
     * @return - true or false as described above
     */
    public boolean checkMenuCollapsed(String task, String testName, WebElement element, BrowserType browserType,
                                      DeviceType deviceType, boolean shouldBeCollapsed) {
        //not the smartest way, but the only working one that I found
        boolean isOpen = element.getAttribute("class").contains("opened");
        //if it should be collapsed but it is open return false
        boolean result = isOpen != shouldBeCollapsed;
        testReporter.collectTestResults(task, testName, element.getAttribute("id")//to be changed
                , browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), result);
        return result;

    }

    //bug for MOBILE - to check if quick links is collapsed on mobile
    public boolean checkQuickLinksMode(BrowserType browserType, DeviceType deviceType) {
        return checkMenuCollapsed("Task 1", "Quick links menu is collapsed on mobile", quickLinks,
                browserType, deviceType, true);
    }

    //bug for LAPTOP - filter icon should not be visible
    public boolean checkFilterIconVisibility(BrowserType browserType, DeviceType deviceType) {
        if(deviceType == DeviceType.LAPTOP) {
            return checkElementVisibility("Task 1", "Filter icon is not displayed on laptop", filterIcon,
                    browserType, deviceType, false);
        } else {
            return checkElementVisibility("Task 1", "Filter icon is displayed on tablet/mobile", filterIcon,
                    browserType, deviceType, true);
        }
    }

    //bug for LAPTOP - filter icon text should not be visible
    public boolean checkFilterIconTextVisibility(BrowserType browserType, DeviceType deviceType) {
        //choose the correct ID based on version of the app
        WebElement filterIconText = driver.getCurrentUrl().contains("V1") ? filterIconTextV1 : filterIconTextV2;
        if(deviceType == DeviceType.LAPTOP || deviceType == DeviceType.MOBILE) {
            return checkElementVisibility("Task 1", "Filter icon text is not displayed on laptop/mobile", filterIconText,
                    browserType, deviceType, false);
        } else {
            return checkElementVisibility("Task 1", "Filter icon text is displayed on tablet", filterIconText,
                    browserType, deviceType, true);
        }
    }

    //bug only for LAPTOP - tooltips should be visible after hovering over item
    public boolean checkTooltipsVisibilityAfterHover(BrowserType browserType, DeviceType deviceType) {
        List<String> tooltipsElementsSelectors = Arrays.asList("ti-heart", "ti-control-shuffle", "ti-shopping-cart");
        return checkDescendantsVisibilityAfterHover("Task 1", "Tooltips under items are displayed after hovering on laptop",
                    items, tooltipsElementsSelectors, browserType, deviceType, true, false);
    }

    //bug for TABLET & MOBILE - add to favorite icon should not be visible
    public boolean checkAddToFavoriteIconVisibility(BrowserType browserType, DeviceType deviceType) {
        if(deviceType == DeviceType.LAPTOP) {
            return checkElementVisibility("Task 1", "Add to favorite icon is displayed on laptop", wishlistIcon,
                    browserType, deviceType, true);
        } else {
            return checkElementVisibility("Task 1", "Add to favorite icon is not displayed on tablet/mobile", wishlistIcon,
                    browserType, deviceType, false);
        }
    }

    //bug for TABLET & MOBILE - view grid option should not be visible
    public boolean checkViewGridOptionVisibility(BrowserType browserType, DeviceType deviceType) {
        //choose the correct ID based on version of the app
        WebElement viewGridOption = getURL().contains("V1") ? viewGridOptionV1 : viewGridOptionV2;
        if(deviceType == DeviceType.LAPTOP) {
            return checkElementVisibility ("Task 1", "View grid option is displayed on laptop", viewGridOption,
                    browserType, deviceType, true);
        } else {
            return checkElementVisibility ("Task 1", "View grid option is not displayed on tablet/mobile", viewGridOption,
                    browserType, deviceType, false);
        }
    }

    //bug for TABLET & MOBILE - view list option should not be visible
    public boolean checkViewListOptionVisibility(BrowserType browserType, DeviceType deviceType) {
        WebElement viewListOption = getURL().contains("V1") ? viewListOptionV1 : viewListOptionV2;
        if(deviceType == DeviceType.LAPTOP) {
            return checkElementVisibility("Task 1", "View list option is displayed on laptop", viewListOption,
                    browserType, deviceType, true);
        } else {
            return checkElementVisibility("Task 1", "View list option is not displayed on tablet/mobile", viewListOption,
                    browserType, deviceType, false);
        }
    }

    //bug for TABLET & MOBILE - tooltips should be visible under each item
    public boolean checkTooltipsVisibility(BrowserType browserType, DeviceType deviceType) {
        List<String> tooltipsElementsSelectors = Arrays.asList("ti-heart", "ti-control-shuffle", "ti-shopping-cart");
        return checkDescendantsVisibility("Task 1", "Tooltips under items are displayed on tablet/mobile",
                    items, tooltipsElementsSelectors, browserType, deviceType, true, false);
    }

    //bug for MOBILE - search bar with text should not be visible
    public boolean checkSearchBarTextFieldVisibility(BrowserType browserType, DeviceType deviceType) {
        if(deviceType == DeviceType.MOBILE) {
            return checkElementVisibility("Task 1", "Search bar is not displayed on mobile",
                    searchBarTextField, browserType, deviceType, false);
        } else {
            return checkElementVisibility("Task 1", "Search bar is displayed on laptop/tablet",
                    searchBarTextField, browserType, deviceType, true);
        }
    }

    //bug for MOBILE - search icon in search bar should not be visible
    public boolean checkSearchIconVisibility(BrowserType browserType, DeviceType deviceType) {
        if(deviceType == DeviceType.MOBILE) {
            return checkElementVisibility("Task 1", "Search icon in search bar is not displayed on mobile",
                    searchButton, browserType, deviceType, false);
        } else {
            return checkElementVisibility("Task 1", "Search icon in search bar is displayed on laptop/tablet",
                    searchButton, browserType, deviceType, true);
        }
    }

    //bug for MOBILE - cart icon number should not be visible
    public boolean checkCartIconNumberVisibility(BrowserType browserType, DeviceType deviceType) {
        if(deviceType == DeviceType.MOBILE) {
            return checkElementVisibility("Task 1", "Number of items in cart icon is not displayed on mobile",
                    cartIconItemsNumber, browserType, deviceType, false);
        } else {
            return checkElementVisibility("Task 1", "Number of items in cart icon is displayed on laptop/tablet",
                    cartIconItemsNumber, browserType, deviceType, true);
        }
    }

    /**
     * used to show only shoes that have black color. If filter menu is not visible, it is expanded
     */
    public void showBlackShoesOnly() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //for tablet and mobile filtering options must be expanded
        if(!blackShoesFilterCheckbox.isDisplayed()) {
            filterIcon.click();
        }
        //wait until the checkbox is clickable
        wait.until(ExpectedConditions.elementToBeClickable(blackShoesFilterCheckbox));
        //scroll to the checkbox
        js.executeScript("arguments[0].scrollIntoView(true);", blackShoesFilterCheckbox);
        //click black option
        blackShoesFilterCheckbox.click();
        //scroll to filter button
        js.executeScript("arguments[0].scrollIntoView(true);", filterButton);
        filterButton.click();
    }

    /**
     * it executes all verifications identified as needed after showing black shoes only. Following verifications are executed:
     * 1, Expected number of shoes is shown - based on size of black shoes list
     * 2, Name of the shoes is the same as alt attribute of displayed image
     * 3, Name of the shoes is one of the expected shoes names
     * 4, Alt attribute of the shoes image is one of the expected shoes names
     * @param blackShoes - List of names of black shoes that are black and should be displayed. The assumption is that
     *                   images of those shoes have the same alt attribute as the shoes name
     * @param browserType - Browser from BrowserType enums that will be used for test
     * @param deviceType - Device from DeviceType enums that will be used for test => devices have specific viewport dimensions
     * @return - returns trues only if all 4 verifications are fine. Otherwise, it returns false
     */
    public boolean checkOnlyBlackShoesAreDisplayed(List<String> blackShoes, BrowserType browserType, DeviceType deviceType) {
        boolean result = false;
        List<String> foundShoesNames = new ArrayList<>();
        List<String> foundShoesImages = new ArrayList<>();
        //check size
        int expectedNumOfItems = blackShoes.size();
        int actualNumOfItems = items.size();
        boolean sizeCheck = expectedNumOfItems == actualNumOfItems;
        testReporter.collectTestResults("Task 2", "Check that expected number of black shoes is displayed",
                    String.valueOf(expectedNumOfItems), browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), sizeCheck);

        //if there is at least one element, check that expected black shoes are displayed
        if(actualNumOfItems > 0) {
            for (WebElement e : items) {
                WebElement tempShoe = e.findElement(By.tagName("h3"));
                String shoeName = tempShoe.getText().trim();

                WebElement tempImg = e.findElement(By.tagName("img"));
                String shoeImage = tempImg.getAttribute("alt").trim();

                //check and write the name if it is ok
                boolean nameMatchesImage = shoeName.equals(shoeImage);
                testReporter.collectTestResults("Task 2", "Check that name of the captured shoes is the same as image alt attribute",
                        shoeName, browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), nameMatchesImage);

                //check that the shoe name was expected but it was not previously found
                boolean nameIsInExpectedList = blackShoes.contains(shoeName) && !foundShoesNames.contains(shoeName);
                foundShoesNames.add(shoeName);
                testReporter.collectTestResults("Task 2", "Check that captured name of the shoes is the expected one",
                        shoeName, browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), nameIsInExpectedList);

                boolean imageIsInExpectedList = blackShoes.contains(shoeImage) && !foundShoesImages.contains(shoeImage);
                foundShoesImages.add(shoeImage);
                testReporter.collectTestResults("Task 2", "Check that image of the shoes is the expected one",
                        shoeImage, browserType.getBrowserName(), deviceType.getDeviceWidth(), deviceType.getDeviceName(), imageIsInExpectedList);

            }
            boolean areShoeNamesAsExpected = blackShoes.equals(foundShoesNames);
            boolean areShoeImagesAsExpected = blackShoes.equals(foundShoesImages);
            result = areShoeNamesAsExpected && areShoeImagesAsExpected;
        }
        return result;
    }

    /**
     * used to open the first item (first shoes) in the page
     * @return - it returns ProductDetailPage which should be visible after clicking on any item
     */
    public ProductDetailsPage accessFirstItemProductDetailsPage() {
        //wait until the first black shoe can be clicked
        WebElement firstItem = items.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(firstItem));
        firstItem.click();
        ProductDetailsPage productDetailPage = new ProductDetailsPage(driver);
        PageFactory.initElements(driver, productDetailPage);
        //to make sure that ProductDetailsPage was loaded
        wait.until(ExpectedConditions.visibilityOf(logo));
        //check if url of Product Detail Page is correct one - skipped
        return productDetailPage;
    }
}
