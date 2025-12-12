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
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @AndroidFindBy(id = "view_page_title_text")
    private WebElement articleTitle;

    @AndroidFindBy(id = "languages")
    private WebElement languagesButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='English']")
    private WebElement englishLanguage;

    @AndroidFindBy(id = "content_text")
    private WebElement contentText;

    @AndroidFindBy(id = "some_scroll_element")  // Placeholder for scroll target, adjust based on app
    private WebElement scrollTarget;

    public ArticlePage(AppiumDriver driver) {
        this.driver = driver;
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
        touchAction.press(PointOption.point(driver.manage().window().getSize().getWidth() / 2, driver.manage().window().getSize().getHeight()))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
                .moveTo(PointOption.point(driver.manage().window().getSize().getWidth() / 2, driver.manage().window().getSize().getHeight() / 2))
                .release()
                .perform();
        wait.until(ExpectedConditions.visibilityOf(scrollTarget));
    }
}
