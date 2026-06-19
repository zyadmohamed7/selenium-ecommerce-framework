package com.seleniumframework.utils.actions;

import com.seleniumframework.utils.Waits;
import com.seleniumframework.utils.logs.Logs;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

public class ElementActions {
    private final WebDriver driver;
    private final Waits waits;

    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waits = new Waits(driver);
    }

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});", element);
    }

    public void click(By locator) {
        Logs.info("Clicking element: " + locator);
        waits.fluentWait().until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollIntoView(element);
                element.click();
                return true;
            } catch (
                    Exception e) {
                Logs.warn("Retrying click on " + locator + " — " + e.getMessage());
                return false;
            }
        });
    }


    public void type(By locator, String text) {
        Logs.info("Typing '" + text + "' into element: " + locator);
        waits.fluentWait().until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollIntoView(element);
                element.clear();
                element.sendKeys(text);
                return true;
            } catch (
                    Exception e) {
                Logs.warn("Retrying type on " + locator + " — " + e.getMessage());
                return false;
            }
        });
    }

    public String getText(By locator) {
        Logs.info("Getting text from element: " + locator);
        return waits.fluentWait().until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollIntoView(element);
                String text = element.getText();
                return !text.isEmpty() ? text : null;
            } catch (
                    Exception e) {
                Logs.warn("Retrying getText on " + locator + " — " + e.getMessage());
                return null;
            }
        });
    }

    public void uploadFile(By locator, String filePath) {
        String fileName = System.getProperty("user.dir") + File.separator + filePath;
        Logs.info("Uploading file: " + fileName + " to element: " + locator);
        waits.fluentWait().until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollIntoView(element);
                element.sendKeys(fileName);
                return true;
            } catch (
                    Exception e) {
                Logs.warn("Retrying uploadFile on " + locator + " — " + e.getMessage());
                return false;
            }
        });
    }

    public void selectByValue(By locator, String value) {
        Logs.info("Selecting option '" + value + "' from dropdown: " + locator);
        waits.fluentWait().until(driver -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollIntoView(element);
                org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(element);
                select.selectByValue(value);
                return true;
            } catch (Exception e) {
                Logs.warn("Retrying selectByValue on " + locator + " — " + e.getMessage());
                return false;
            }
        });
    }
}

