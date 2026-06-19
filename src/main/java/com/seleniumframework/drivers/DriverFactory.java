package com.seleniumframework.drivers;

import com.seleniumframework.utils.dataReaders.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    private DriverFactory() {}

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            String browser = ConfigReader.get("browser");
            Browsers browserEnum = Browsers.valueOf(browser.toUpperCase());
            AbstractDriver abstractDriver = browserEnum.getDriverFactory();
            WebDriver driver = ThreadGuard.protect(abstractDriver.createDrivers());
            driverThreadLocal.set(driver);
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
