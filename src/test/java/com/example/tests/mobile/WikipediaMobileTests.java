package com.example.tests.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.example.pages.mobile.ArticlePage;
import com.example.pages.mobile.WikipediaHomePage;
import com.example.utils.AppiumDriverManager;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;

public class WikipediaMobileTests {
    private AndroidDriver driver;
    private WikipediaHomePage homePage;
    private ArticlePage articlePage;

    @BeforeEach
    void setUp() throws Exception {
        driver = AppiumDriverManager.createAndroidDriver();
        homePage = new WikipediaHomePage(driver);
        articlePage = new ArticlePage(driver);
    }

    @AfterEach
    void tearDown() {
        AppiumDriverManager.quitDriver();
    }

    @Test
    void testAppLaunch() {
        assertTrue(homePage.openSearch() != null);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Java", "Python", "Wikipedia", "Android", "Selenium"})
    void testSearchArticle(String query) {
        homePage.openSearch();
        homePage.enterSearchQuery(query);
        driver.findElement(By.id("search_container")).findElement(By.xpath("//android.widget.TextView")).click();
        assertTrue(articlePage.getArticleTitle().toLowerCase().contains(query.toLowerCase()));
    }

    @Test
    void testArticleContentVisibility() {
        homePage.openSearch();
        homePage.enterSearchQuery("Java");
        driver.findElement(By.id("search_container")).findElement(By.xpath("//android.widget.TextView")).click();
        assertTrue(articlePage.isContentVisible());
    }

    @Test
    void testSwitchLanguage() {
        homePage.openSearch();
        homePage.enterSearchQuery("Java");
        driver.findElement(By.id("search_container")).findElement(By.xpath("//android.widget.TextView")).click();
        articlePage.switchToEnglish();
        assertEquals("Java", articlePage.getArticleTitle().trim());
    }

    @Test
    void testCloseSearch() {
        homePage.openSearch();
        homePage.enterSearchQuery("Test");
        homePage.closeSearch();
        assertTrue(driver.getPageSource().contains("Explore"));
    }

    @Test
    void testSearchInEnglish() {
        articlePage.switchToEnglish();
        homePage.openSearch();
        homePage.enterSearchQuery("Programming");
        driver.findElement(By.id("search_container")).findElement(By.xpath("//android.widget.TextView")).click();
        assertTrue(articlePage.getArticleTitle().contains("Programming"));
    }

    @Test
    void testScrollInArticle() {
        homePage.openSearch();
        homePage.enterSearchQuery("Java");
        driver.findElement(By.id("search_container")).findElement(By.xpath("//android.widget.TextView")).click();
        articlePage.scrollToContent();
        assertTrue(articlePage.isContentVisible());
    }

    @Test
    void testLanguageSwitchAfterSearch() {
        homePage.openSearch();
        homePage.enterSearchQuery("Python");
        driver.findElement(By.id("search_container")).findElement(By.xpath("//android.widget.TextView")).click();
        articlePage.switchToEnglish();
        assertTrue(articlePage.getArticleTitle().contains("Python"));
    }
}

