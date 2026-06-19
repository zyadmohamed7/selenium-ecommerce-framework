package com.seleniumframework.validations;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Verifications extends BaseAssertions {
    public Verifications(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, true, condition);
            throw e;
        }
    }

    @Override
    public void assertFalse(boolean condition, String message) {
        try {
            Assert.assertFalse(condition, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, false, condition);
            throw e;
        }
    }

    @Override
    public void assertEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            logPass(message);
        } catch (AssertionError e) {
            logFail(message, expected, actual);
            throw e;
        }
    }

    public void assertElementDisplayed(By locator, String message) {
        try {
            waits.fluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            logPass(message);
        } catch (Exception e) {
            logFail(message, "Element to be visible", "Element not visible");
            Assert.fail(message + " | Element not displayed | locator: " + locator);
        }
    }

    public void assertElementEnabled(By locator, String message) {
        try {
            waits.fluentWait().until(d -> d.findElement(locator).isEnabled());
            logPass(message);
        } catch (Exception e) {
            logFail(message, "Element to be enabled", "Element not enabled");
            Assert.fail(message + " | Element not enabled | locator: " + locator);
        }
    }

    public void assertElementTextEquals(By locator, String expectedText, String message) {
        try {
            waits.fluentWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
            logPass(message);
        } catch (Exception e) {
            String actualText = getElementText(locator);
            logFail(message, expectedText, actualText);
            Assert.assertEquals(actualText, expectedText, message);
        }
    }

    public void assertElementTextContains(By locator, String expectedText, String message) {
        try {
            waits.fluentWait().until(d -> d.findElement(locator).getText().contains(expectedText));
            logPass(message);
        } catch (Exception e) {
            String actualText = getElementText(locator);
            logFail(message, expectedText, actualText);
            Assert.assertTrue(actualText.contains(expectedText), message);
        }
    }

    public void assertCurrentUrlContains(String expectedUrlPart, String message) {
        try {
            waits.fluentWait().until(ExpectedConditions.urlContains(expectedUrlPart));
            logPass(message);
        } catch (Exception e) {
            String actualUrl = currentUrl();
            logFail(message, expectedUrlPart, actualUrl);
            Assert.assertTrue(actualUrl.contains(expectedUrlPart), message);
        }
    }

    public void assertTitleEquals(String expectedTitle, String message) {
        try {
            waits.fluentWait().until(ExpectedConditions.titleIs(expectedTitle));
            logPass(message);
        } catch (Exception e) {
            String actualTitle = pageTitle();
            logFail(message, expectedTitle, actualTitle);
            Assert.assertEquals(actualTitle, expectedTitle, message);
        }
    }
}
