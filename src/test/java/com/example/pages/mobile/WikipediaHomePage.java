package com.example.pages.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WikipediaHomePage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "search_menu_text")
    private WebElement searchButton;

    @AndroidFindBy(id = "search_src_text")
    private WebElement searchInput;

    @AndroidFindBy(id = "search_close_btn")
    private WebElement closeSearchButton;

    @AndroidFindBy(id = "search_container")
    private WebElement searchContainer;

    public WikipediaHomePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void openSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }

    public void enterSearchQuery(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public void closeSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(closeSearchButton));
        closeSearchButton.click();
    }

    public void selectFirstSearchResult() {
        wait.until(ExpectedConditions.visibilityOf(searchContainer));
        searchContainer.findElement(org.openqa.selenium.By.xpath("./android.widget.TextView[1]")).click();
    }
}
