package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MainPage {

    private WebDriver driver;
    private WebDriverWait wait;
    public final String url = "https://demo.applitools.com/gridHackathonV2.html";


    @FindBy(id = "product_grid")
    private WebElement shoesGrid;

    @FindBy(className = "grid_item")
    private List<WebElement> items;

    @FindBy(className = "grid_item")
    private WebElement item;

    @FindBy(id = "IMG____9")
    private WebElement logo;

    @FindBy(id = "SPAN__checkmark__107")
    private WebElement blackShoesFilterCheckbox;

    @FindBy(id = "filterBtn")
    private WebElement filterButton;

    @FindBy(id = "ti-filter")
    private WebElement filterIcon;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
        PageFactory.initElements(driver, this);
    }

    public String getURL() {
        return url;
    }

    public WebElement getShoesGrid() {
        return shoesGrid;
    }

    public void showBlackShoesOnly() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        //for tablet and mobile filtering options must be expanded
        if (!blackShoesFilterCheckbox.isDisplayed()) {
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

    public ProductDetailsPage accessFirstItemProductDetailsPage() {
        //wait until the first black shoe can be clicked
        WebElement firstItem = items.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(firstItem));
        firstItem.click();
        ProductDetailsPage productDetailPage = new ProductDetailsPage(driver);
        PageFactory.initElements(driver, productDetailPage);
        //try if necessary
        wait.until(ExpectedConditions.visibilityOf(logo));
        //check if url is the expected one
        return productDetailPage;
    }
}
