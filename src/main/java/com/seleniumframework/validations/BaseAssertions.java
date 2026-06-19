package com.seleniumframework.validations;

import com.seleniumframework.utils.Waits;
import com.seleniumframework.utils.actions.ElementActions;
import com.seleniumframework.utils.logs.Logs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseAssertions {
    protected final WebDriver driver;
    protected final Waits waits;
    protected final ElementActions elementActions;

    protected BaseAssertions(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
        this.elementActions = new ElementActions(driver);
    }

    public abstract void assertTrue(boolean condition, String message);

    public abstract void assertFalse(boolean condition, String message);

    public abstract void assertEquals(Object actual, Object expected, String message);

    protected void logPass(String message) {
        Logs.info("PASSED: " + message);
    }

    protected void logFail(String message, Object expected, Object actual) {
        Logs.error("FAILED: " + message + " | Expected: [" + expected + "] but found: [" + actual + "]");
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            waits.explicitWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (RuntimeException e) {
            Logs.warn("Element is not displayed:", locator.toString(), e.getMessage());
            return false;
        }
    }

    protected boolean isElementEnabled(By locator) {
        try {
            WebElement element = waits.explicitWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isEnabled();
        } catch (RuntimeException e) {
            Logs.warn("Element is not enabled:", locator.toString(), e.getMessage());
            return false;
        }
    }

    protected String getElementText(By locator) {
        try {
            return waits.explicitWait()
                    .until(ExpectedConditions.visibilityOfElementLocated(locator))
                    .getText();
        } catch (RuntimeException e) {
            Logs.warn("Could not read element text:", locator.toString(), e.getMessage());
            return "";
        }
    }

    protected String currentUrl() {
        return driver.getCurrentUrl();
    }

    protected String pageTitle() {
        return driver.getTitle();
    }
}
