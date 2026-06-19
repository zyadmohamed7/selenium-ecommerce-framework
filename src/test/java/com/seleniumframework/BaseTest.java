package com.seleniumframework;

import com.seleniumframework.drivers.DriverFactory;
import com.seleniumframework.listeners.TestListener;
import com.seleniumframework.utils.dataReaders.ConfigReader;
import com.seleniumframework.utils.actions.BrowserActions;
import com.seleniumframework.utils.actions.ElementActions;
import com.seleniumframework.validations.Validations;
import com.seleniumframework.validations.Verifications;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    private static final ThreadLocal<WebDriver> driverTL = new ThreadLocal<>();
    private static final ThreadLocal<BrowserActions> browserActionsTL = new ThreadLocal<>();
    private static final ThreadLocal<ElementActions> elementActionsTL = new ThreadLocal<>();
    private static final ThreadLocal<Validations> validationsTL = new ThreadLocal<>();
    private static final ThreadLocal<Verifications> verificationsTL = new ThreadLocal<>();

    protected WebDriver getDriver() {
        return driverTL.get();
    }

    protected BrowserActions getBrowserActions() {
        return browserActionsTL.get();
    }

    protected ElementActions getElementActions() {
        return elementActionsTL.get();
    }

    protected Validations getValidations() {
        return validationsTL.get();
    }

    protected Verifications getVerifications() {
        return verificationsTL.get();
    }

    @BeforeMethod
    public void setUp() {
        WebDriver driver = DriverFactory.getDriver();
        driverTL.set(driver);
        browserActionsTL.set(new BrowserActions(driver));
        elementActionsTL.set(new ElementActions(driver));

        Validations validations = new Validations(driver);
        validationsTL.set(validations);
        verificationsTL.set(new Verifications(driver));

        TestListener.registerValidations(validations);
        getBrowserActions().maximizeWindow();
        getBrowserActions().navigateTo(ConfigReader.get("baseUrl"));
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
        driverTL.remove();
        browserActionsTL.remove();
        elementActionsTL.remove();
        validationsTL.remove();
        verificationsTL.remove();
    }
}
