package com.example.pages.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ArticlePage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "view_page_title_text")
    private WebElement articleTitle;

    @AndroidFindBy(id = "languages")
    private WebElement languagesButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='English']")
    private WebElement englishLanguage;

    @AndroidFindBy(id = "content_text")
    private WebElement contentText;

    @AndroidFindBy(id = "some_scroll_element")
    private WebElement scrollTarget;

    public ArticlePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public String getArticleTitle() {
        wait.until(ExpectedConditions.visibilityOf(articleTitle));
        return articleTitle.getText();
    }

    public void switchToEnglish() {
        wait.until(ExpectedConditions.elementToBeClickable(languagesButton));
        languagesButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(englishLanguage));
        englishLanguage.click();
    }

    public boolean isContentVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(contentText));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void scrollToContent() {
        TouchAction touchAction = new TouchAction(driver);
        int width = driver.manage().window().getSize().getWidth();
        int height = driver.manage().window().getSize().getHeight();
        touchAction.press(PointOption.point(width / 2, height))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(width / 2, height / 2))
                .release()
                .perform();
        wait.until(ExpectedConditions.visibilityOf(scrollTarget));
    }
}

