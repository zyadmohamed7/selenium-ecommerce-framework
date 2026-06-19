package com.seleniumframework.listeners;

import com.seleniumframework.drivers.DriverFactory;
import com.seleniumframework.media.Screenshots;
import com.seleniumframework.utils.logs.Logs;
import com.seleniumframework.validations.Validations;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

public class TestListener implements IInvokedMethodListener {

    private static final ThreadLocal<Validations> validationsHolder = new ThreadLocal<>();

    public static void registerValidations(Validations validations) {
        validationsHolder.set(validations);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) return;

        // take screenshot on failure
        if (testResult.getStatus() == ITestResult.FAILURE) {
            try {
                WebDriver driver = DriverFactory.getDriver();
                if (driver != null) {
                    String testName = testResult.getMethod().getMethodName();
                    Screenshots.fullPageScreenshot(driver, testName);
                }
            } catch (Exception e) {
                Logs.error("Could not capture failure screenshot: " + e.getMessage());
            }
        }

        //  assertAll() for soft assertions after every test method
        Validations validations = validationsHolder.get();
        if (validations != null && validations.hasSoftAssertions()) {
            try {
                validations.assertAll();
            } catch (AssertionError e) {
                testResult.setStatus(ITestResult.FAILURE);
                testResult.setThrowable(e);
            }
        }
    }
}
