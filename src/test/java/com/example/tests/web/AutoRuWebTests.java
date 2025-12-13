package com.example.tests.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.example.pages.web.HomePage;
import com.example.pages.web.SearchResultsPage;
import com.example.utils.ConfigReader;
import com.example.utils.DriverManager;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;

public class AutoRuWebTests {
    private WebDriver driver;
    private HomePage homePage;
    private SearchResultsPage searchResultsPage;

    @BeforeEach
    void setUp() {
        driver = DriverManager.createDriver();
        driver.get(ConfigReader.getProperty("site.url"));
        homePage = new HomePage(driver);
        searchResultsPage = new SearchResultsPage(driver);
    }

    @AfterEach
    void tearDown() {
        DriverManager.quitDriver();
    }

    @Test
    void testNavigateToCatalog() {
        homePage.navigateToCatalog();
        assertEquals("Catalog", homePage.getPageTitle().trim());
    }

    @Test
    void testNavigateToNewCars() {
        homePage.navigateToNewCars();
        assertTrue(homePage.getPageTitle().contains("New cars"));
    }

    @Test
    void testNavigateToUsedCars() {
        homePage.navigateToUsedCars();
        assertTrue(homePage.getPageTitle().contains("Used cars"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Toyota", "BMW", "Lada", "Volkswagen", "Mercedes"})
    void testSearchCar(String query) {
        homePage.searchCar(query);
        assertTrue(searchResultsPage.getResultsCount() > 0);
        assertTrue(homePage.isListingVisible());
    }

    @Test
    void testSearchAndApplyFilter() {
        homePage.searchCar("Toyota");
        searchResultsPage.applyYearFilter();
        assertTrue(searchResultsPage.getResultsCount() > 0);
    }

    @Test
    void testPageLoadTitle() {
        assertTrue(homePage.getPageTitle().contains("AUTO.RU"));
    }

    @Test
    void testInvalidSearch() {
        homePage.searchCar("NonExistentCar123");
        assertTrue(searchResultsPage.getResultsCount() == 0 || driver.getPageSource().contains("Nothing found"));
    }

    @Test
    void testListingVisibilityAfterSearch() {
        homePage.searchCar("Volkswagen");
        assertTrue(homePage.isListingVisible());
    }

    @Test
    void testSearchWithPriceFilter() {
        homePage.searchCar("BMW");
        searchResultsPage.addPriceFilter();
        assertTrue(searchResultsPage.getResultsCount() > 0);
    }

    @Test
    void testNavigateAndSearchFromCatalog() {
        homePage.navigateToCatalog();
        homePage.searchCar("Audi");
        assertTrue(homePage.isListingVisible());
    }
}

