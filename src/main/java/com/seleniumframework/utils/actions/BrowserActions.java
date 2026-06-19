package com.seleniumframework.utils.actions;

import com.seleniumframework.utils.logs.Logs;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;

import java.util.ArrayList;
import java.util.List;

public class BrowserActions {
    private final WebDriver driver;
    public BrowserActions(WebDriver driver) {
        this.driver = driver;
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }
    public String getTitle() {
        return driver.getTitle();
    }
    public String getCurrentUrl() {
        String url = driver.getCurrentUrl();
        Logs.info("Current URL: " + url);
        return url;
    }
    public void navigateTo(String url) {
        driver.get(url);
        Logs.info("Navigated to URL: " + url);
    }
    public void closeCurrentWindow() {
        Logs.info("Closing current window");
        driver.close();
    }
    public void navigateBack() {
        driver.navigate().back();
        Logs.info("Navigated back");
    }

    public void navigateForward() {
        driver.navigate().forward();
        Logs.info("Navigated forward");
    }

    public void refreshPage() {
        driver.navigate().refresh();
        Logs.info("Page refreshed");
    }
    public void switchToTab(int index) {
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(index));
        Logs.info("Switched to tab index: " + index);
    }

    public void openNewTab() {
        driver.switchTo().newWindow(WindowType.TAB);
        Logs.info("Opened new tab");
    }

    public void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}
