package com.seleniumframework.utils;

import java.util.List;

import com.seleniumframework.utils.dataReaders.ConfigReader;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;

public class Waits {
    private final WebDriver driver;

    public Waits(WebDriver driver) {
        this.driver = driver;
    }

    public FluentWait<WebDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(ConfigReader.getInt("fluentWaitTimeout")))
                .pollingEvery(Duration.ofMillis(ConfigReader.getInt("fluentWaitPolling")))
                .withMessage("Element not found or not interactable after timeout")
                .ignoreAll(getExceptions());
    }

    public WebDriverWait explicitWait() {
        return new WebDriverWait(driver,
                Duration.ofSeconds(ConfigReader.getInt("explicitWaitTimeout")));
    }

    private List<Class<? extends Throwable>> getExceptions() {
        return List.of(
                NoSuchElementException.class,
                StaleElementReferenceException.class,
                ElementNotInteractableException.class,
                ElementClickInterceptedException.class
        );
    }
}

