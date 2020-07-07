package tests;

import config.BrowserType;
import config.DeviceType;
import config.RunType;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import pages.MainPage;
import pages.Navigation;
import pages.ProductDetailsPage;
import utils.AssertActions;

import java.util.List;

public class TraditionalTests extends BaseTest {

    @Test(dataProvider = "browsers", priority = 0)
    public void elementsVisibilityTest(ITestContext context, BrowserType browser, String browserVersion, DeviceType device, RunType runType) {
        context.setAttribute("sessionId", sessionId.get());
        //overall result of test
        boolean result = false;

        createDriver(browser, browserVersion, device, "Task 1", runType);
        //adding assertions, each driver has own instance of soft assert class
        AssertActions assertActions = new AssertActions();

        Navigation navigation = new Navigation(threadDriver.get());
        MainPage mainPage = navigation.goToMainPage();

        //bugs relevant only for LAPTOPS
        boolean isFilterIconNotVisible = mainPage.checkFilterIconVisibility(browser, device);
        assertActions.softAssertTrue(isFilterIconNotVisible);
        boolean isFilterIconTextNotVisible = mainPage.checkFilterIconTextVisibility(browser, device);
        assertActions.softAssertTrue(isFilterIconTextNotVisible);

        //bugs relevant for both tablets and mobiles
        boolean isAddToFavoriteNotVisible = mainPage.checkAddToFavoriteIconVisibility(browser, device);
        assertActions.softAssertTrue(isAddToFavoriteNotVisible);
        boolean isViewGridOptionNotVisible = mainPage.checkViewGridOptionVisibility(browser, device);
        assertActions.softAssertTrue(isViewGridOptionNotVisible);
        boolean isViewListOptionNotVisible = mainPage.checkViewListOptionVisibility(browser, device);
        assertActions.softAssertTrue(isViewListOptionNotVisible);

        //bugs related to items, running them only to discover bugs, reduced to run only for one item
        if(device != DeviceType.LAPTOP) {
            boolean areAllTooltipsVisible = mainPage.checkTooltipsVisibility(browser, device);
            assertActions.softAssertTrue(areAllTooltipsVisible);
        } else {
            boolean areAllTooltipsAfterHoverVisible = mainPage.checkTooltipsVisibilityAfterHover(browser, device);
            assertActions.softAssertTrue(areAllTooltipsAfterHoverVisible);
        }

        boolean isSearchBarTextFieldNotVisible = mainPage.checkSearchBarTextFieldVisibility(browser, device);
        assertActions.softAssertTrue(isSearchBarTextFieldNotVisible);
        boolean isSearchIconNotVisible = mainPage.checkSearchIconVisibility(browser, device);
        assertActions.softAssertTrue(isSearchIconNotVisible);
        boolean isCartIconNumberNotVisible = mainPage.checkCartIconNumberVisibility(browser, device);
        assertActions.softAssertTrue(isCartIconNumberNotVisible);

        //bug related only to mobile which has menu which options to be collapsed and expanded
        if(device == DeviceType.MOBILE) {
            boolean isQuickLinksCollapsed = mainPage.checkQuickLinksMode(browser, device);
            assertActions.softAssertTrue(isQuickLinksCollapsed);
        }
        assertActions.softAssertAll();
    }

    @Test(dataProvider = "browsers", priority = 1)
    public void filterBlackShoesTest(BrowserType browser, String browserVersion, DeviceType device, RunType runType) {
        createDriver(browser, browserVersion, device, "Task 2", runType);
        AssertActions assertActions = new AssertActions();
        Navigation navigation = new Navigation(threadDriver.get());
        MainPage mainPage = navigation.goToMainPage();
        mainPage.showBlackShoesOnly();
        //list of black shoes that should appear after filtering
        List<String> blackShoesList = List.of("Appli Air x Night", "Appli Air 720");
        boolean areOnlyBlackShoesDisplayed = mainPage.checkOnlyBlackShoesAreDisplayed(blackShoesList, browser, device);
        assertActions.assertTrue(areOnlyBlackShoesDisplayed);
    }

    @Test(dataProvider = "browsers", priority = 2)
    public void displayDetailsOfBlackShoesTest(BrowserType browser, String browserVersion, DeviceType device, RunType runType) {
        createDriver(browser, browserVersion, device, "Task 3", runType);
        AssertActions assertActions = new AssertActions();
        Navigation navigation = new Navigation(threadDriver.get());
        MainPage mainPage = navigation.goToMainPage();
        mainPage.showBlackShoesOnly();
        ProductDetailsPage productDetailsPage = mainPage.accessFirstItemProductDetailsPage();
        boolean isSkuNumberVisible = productDetailsPage.checkSkuNumberVisibility(browser, device);
        assertActions.softAssertTrue(isSkuNumberVisible);
        boolean isNewPriceCorrect = productDetailsPage.checkNewPriceValue("$33.00", browser, device);
        assertActions.softAssertTrue(isNewPriceCorrect);
        boolean isOldPriceStyleCorrect = productDetailsPage.checkOldPriceStyle(browser, device);
        assertActions.softAssertTrue(isOldPriceStyleCorrect);
        boolean isDefaultSizeCorrect = productDetailsPage.checkDefaultShoesSize("Small (S)", browser, device);
        assertActions.softAssertTrue(isDefaultSizeCorrect);
        //new tries - icons smashed together in tablet
        boolean areIconsNotOverlappingInHeader = productDetailsPage.checkIconsOverlappingInHeader(browser, device);
        assertActions.softAssertTrue(areIconsNotOverlappingInHeader);
        //tablet and mobile review text and stars
        boolean areReviewStarsAndTextNotOverlapping = productDetailsPage.checkReviewOverlapping(browser, device);
        assertActions.softAssertTrue(areReviewStarsAndTextNotOverlapping);
        //only tablet
        boolean areAddToCartButtonAndQuantityBoxNotOverlapping = productDetailsPage.checkAddToCarButtonAndQuantityBoxNotOverlapping(browser, device);
        assertActions.softAssertTrue(areAddToCartButtonAndQuantityBoxNotOverlapping);
        //check if image is displayed - only firefox
        boolean isProductImageDisplayed = productDetailsPage.checkIsImageDisplayed(browser, device);
        assertActions.softAssertTrue(isProductImageDisplayed);

        assertActions.softAssertAll();
    }

}