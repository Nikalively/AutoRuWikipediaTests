package com.example.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class AppiumDriverManager {
    private static final Properties properties = new Properties();
    private static AppiumDriver driver;

    static {
        try (InputStream input = AppiumDriverManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("File config.properties not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static AndroidDriver createAndroidDriver() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", properties.getProperty("platformName"));
        caps.setCapability("deviceName", properties.getProperty("deviceName"));
        caps.setCapability("platformVersion", properties.getProperty("platformVersion"));
        caps.setCapability("automationName", properties.getProperty("automationName"));
        caps.setCapability("udid", properties.getProperty("udid"));

        String appPath = properties.getProperty("appium.app.path");
        if (appPath != null && !appPath.isEmpty()) {
            caps.setCapability("app", appPath);
        }

        caps.setCapability("appPackage", properties.getProperty("appPackage"));
        caps.setCapability("appActivity", properties.getProperty("appActivity"));
        caps.setCapability("noReset", Boolean.parseBoolean(properties.getProperty("noReset")));
        caps.setCapability("newCommandTimeout", Integer.parseInt(properties.getProperty("appium.new.command.timeout")));
        caps.setCapability("appWaitActivity", properties.getProperty("appium.wait.activity"));
        caps.setCapability("appWaitPackage", properties.getProperty("appium.wait.package"));

        driver = new AndroidDriver(new URL(properties.getProperty("appium.server.url")), caps);
        return (AndroidDriver) driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
