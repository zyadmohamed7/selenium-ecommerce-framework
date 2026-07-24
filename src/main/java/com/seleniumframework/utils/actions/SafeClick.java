package com.seleniumframework.utils.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SafeClick {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public SafeClick(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Safely clicks an element using progressive fallback strategies.
     *
     * Strategy Order:
     * 1. Native Selenium click (preferred)
     * 2. Scroll into view + Actions click
     * 3. JavaScript click (last resort)
     *
     * Native Selenium click always has priority because it best simulates
     * real user behavior. JavaScript click is only used when browser
     * interaction fails.
     *
     * @param locator Selenium locator blueprint of the target element
     */
    public void safeClick(By locator) {

        try {
            nativeClick(locator);
            return;

        } catch (
                ElementNotInteractableException |
                 StaleElementReferenceException e) {

            System.out.println("[WARN] Native click failed. Trying Actions click...");
        }

        try {
            actionsClick(locator);
            return;

        } catch (
                ElementNotInteractableException |
                 StaleElementReferenceException e) {

            System.out.println("[WARN] Actions click failed. Trying JavaScript click...");
        }

        try {
            javascriptClick(locator);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to click element using Native, Actions, and JavaScript strategies.",
                    e
            );
        }
    }

    /**
     * Preferred click implementation.
     * Waits until the element is clickable then performs a native Selenium click.
     */
    private void nativeClick(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator));

        element.click();
    }

    /**
     * Scrolls the element into the center of the viewport
     * then performs an Actions click.
     */
    private void actionsClick(By locator) {
        // Fetch presence to guarantee the node is available in the DOM
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator));

        // Center the viewport on the element to clear fixed headers
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element);

        // Perform the hardware-level pointer click simulation directly.
        // If an overlay blocks it, this step safely throws an interaction exception.
        new Actions(driver)
                .moveToElement(element)
                .click()
                .perform();
    }

    /**
     * Last resort.
     * Executes a DOM click through JavaScript.
     * This bypasses Selenium's interactability checks entirely.
     */
    private void javascriptClick(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.presenceOfElementLocated(locator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                element);
    }
}