package com.seleniumframework.validations;

import com.seleniumframework.utils.logs.Logs;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

public class Validations extends BaseAssertions {
    private SoftAssert softAssert = new SoftAssert();
    private boolean hasSoftAssertion = false;

    public Validations(WebDriver driver) {
        super(driver);
    }

    @Override
    public void assertTrue(boolean condition, String message) {
        hasSoftAssertion = true;
        softAssert.assertTrue(condition, message);
        if (condition) {
            logPass(message);
        } else {
            logFail(message, true, condition);
        }
    }

    @Override
    public void assertFalse(boolean condition, String message) {
        hasSoftAssertion = true;
        softAssert.assertFalse(condition, message);
        if (!condition) {
            logPass(message);
        } else {
            logFail(message, false, condition);
        }
    }

    @Override
    public void assertEquals(Object actual, Object expected, String message) {
        hasSoftAssertion = true;
        softAssert.assertEquals(actual, expected, message);
        if (actual != null && actual.equals(expected)) {
            logPass(message);
        } else {
            logFail(message, expected, actual);
        }
    }

    public void assertElementDisplayed(By locator, String message) {
        hasSoftAssertion = true;
        try {
            waits.fluentWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            logPass(message);
            softAssert.assertTrue(true, message);
        } catch (Exception e) {
            logFail(message, "Element to be visible", "Element not visible");
            softAssert.fail(message + " | Element not displayed | locator: " + locator);
        }
    }

    public void assertElementEnabled(By locator, String message) {
        hasSoftAssertion = true;
        try {
            waits.fluentWait().until(d -> d.findElement(locator).isEnabled());
            logPass(message);
            softAssert.assertTrue(true, message);
        } catch (Exception e) {
            logFail(message, "Element to be enabled", "Element not enabled");
            softAssert.fail(message + " | Element not enabled | locator: " + locator);
        }
    }

    public void assertElementTextEquals(By locator, String expectedText, String message) {
        hasSoftAssertion = true;
        try {
            waits.fluentWait().until(ExpectedConditions.textToBePresentInElementLocated(locator, expectedText));
            logPass(message);
            softAssert.assertEquals(expectedText, expectedText, message);
        } catch (Exception e) {
            String actualText = getElementText(locator);
            logFail(message, expectedText, actualText);
            softAssert.assertEquals(actualText, expectedText, message);
        }
    }

    public void assertElementTextContains(By locator, String expectedText, String message) {
        hasSoftAssertion = true;
        try {
            waits.fluentWait().until(d -> d.findElement(locator).getText().contains(expectedText));
            logPass(message);
            softAssert.assertTrue(true, message);
        } catch (Exception e) {
            String actualText = getElementText(locator);
            logFail(message, expectedText, actualText);
            softAssert.assertTrue(false, message + " | expected to contain: " + expectedText + " | actual: " + actualText);
        }
    }

    public void assertCurrentUrlContains(String expectedUrlPart, String message) {
        hasSoftAssertion = true;
        try {
            waits.fluentWait().until(ExpectedConditions.urlContains(expectedUrlPart));
            logPass(message);
            softAssert.assertTrue(true, message);
        } catch (Exception e) {
            String actualUrl = currentUrl();
            logFail(message, expectedUrlPart, actualUrl);
            softAssert.assertTrue(false, message + " | expected URL to contain: " + expectedUrlPart + " | actual: " + actualUrl);
        }
    }

    public void assertTitleEquals(String expectedTitle, String message) {
        hasSoftAssertion = true;
        try {
            waits.fluentWait().until(ExpectedConditions.titleIs(expectedTitle));
            logPass(message);
            softAssert.assertEquals(expectedTitle, expectedTitle, message);
        } catch (Exception e) {
            String actualTitle = pageTitle();
            logFail(message, expectedTitle, actualTitle);
            softAssert.assertEquals(actualTitle, expectedTitle, message);
        }
    }

    public boolean hasSoftAssertions() {
        return hasSoftAssertion;
    }

    public void assertAll() {
        if (!hasSoftAssertion) return;
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            Logs.error("Soft assertion failed.", e.getMessage());
            throw e;
        } finally {
            softAssert = new SoftAssert();
            hasSoftAssertion = false;
        }
    }
}
