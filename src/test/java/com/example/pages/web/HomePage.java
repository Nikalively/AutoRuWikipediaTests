package com.example.pages.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(css = "a[data-qa='header-category-menu']")
    private WebElement catalogLink;

    @FindBy(css = "input[data-name='Search-Input']")
    private WebElement searchInput;

    @FindBy(css = "button[data-name='Search-Button']")
    private WebElement searchButton;

    @FindBy(css = "a[href*='/newauto/']")
    private WebElement newCarsLink;

    @FindBy(css = "a[href*='/used/']")
    private WebElement usedCarsLink;

    @FindBy(css = "h1[data-name='PageTitle']")
    private WebElement pageTitle;

    @FindBy(css = ".ListingItem")
    private WebElement listingItem;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToCatalog() {
        wait.until(ExpectedConditions.elementToBeClickable(catalogLink));
        catalogLink.click();
    }

    public void searchCar(String query) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(query);
        searchButton.click();
    }

    public void navigateToNewCars() {
        wait.until(ExpectedConditions.elementToBeClickable(newCarsLink));
        newCarsLink.click();
    }

    public void navigateToUsedCars() {
        wait.until(ExpectedConditions.elementToBeClickable(usedCarsLink));
        usedCarsLink.click();
    }

    public String getPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return pageTitle.getText();
    }

    public boolean isListingVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(listingItem));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
