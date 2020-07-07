package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Navigation {

    private WebDriver driver;
    private WebDriverWait wait;

    public Navigation(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(this.driver, 10);
    }

    public MainPage goToMainPage() {
        MainPage mainPage = new MainPage(driver);
        PageFactory.initElements(driver, mainPage);
        driver.get(mainPage.getURL());
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        return mainPage;
    }
}
