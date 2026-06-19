package com.seleniumframework.media;

import com.seleniumframework.utils.TimeManager;
import com.seleniumframework.utils.logs.Logs;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Screenshots {
    private static final String SCREENSHOT_DIR = "test-output/screenshots/";

    public static void fullPageScreenshot(WebDriver driver, String screenshotName) {
        try {
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            io.qameta.allure.Allure.addAttachment(screenshotName, new java.io.ByteArrayInputStream(screenshotBytes));

            File screenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screenshotDis = new File(SCREENSHOT_DIR + screenshotName + "-" + TimeManager.getTimeStamp() + ".png");
            
            Files.copy(screenshotSrc.toPath(), screenshotDis.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Logs.info("PASSED: Screenshot captured successfully -> " + screenshotDis.getAbsolutePath());

        } catch (Exception e) {
            Logs.error("FAILED to capture screenshot: " + screenshotName + " | Error: " + e.getMessage());
        }
    }

    public static void elementScreenshot(WebDriver driver, By elementSelector, String screenshotName) {
        try {
            String accessibleName = driver.findElement(elementSelector).getAccessibleName();

            byte[] screenshotBytes = driver.findElement(elementSelector).getScreenshotAs(OutputType.BYTES);
            io.qameta.allure.Allure.addAttachment(screenshotName != null ? screenshotName : accessibleName, new java.io.ByteArrayInputStream(screenshotBytes));

            File screenshotSrc = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);
            File screenshotDis = new File(SCREENSHOT_DIR + accessibleName + "-" + TimeManager.getTimeStamp() + ".png");
            
            Files.copy(screenshotSrc.toPath(), screenshotDis.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Logs.info("PASSED: Element screenshot captured successfully -> " + screenshotDis.getAbsolutePath());

        } catch (Exception e) {
            Logs.error("FAILED to capture element screenshot: " + screenshotName + " | Error: " + e.getMessage());
        }
    }
}
