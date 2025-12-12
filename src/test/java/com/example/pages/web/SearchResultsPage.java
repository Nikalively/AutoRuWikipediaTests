package com.example.pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SearchResultsPage {
    private WebDriver driver;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(css = ".ListingItem")
    private List<WebElement> resultsList;

    @FindBy(css = "select[data-name='YearFrom']")
    private WebElement yearFromFilter;

    @FindBy(css = "option[value='2020']")
    private WebElement yearOption;

    @FindBy(css = "button[data-name='ApplyFilters']")
    private WebElement applyFiltersButton;

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getResultsCount() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.cssSelector(".ListingItem")));
        return resultsList.size();
    }

    public void applyYearFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(yearFromFilter));
        yearFromFilter.click();
        yearOption.click();
        applyFiltersButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(org.openqa.selenium.By.cssSelector(".Loader")));
    }
}
