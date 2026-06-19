package com.seleniumframework.drivers;

import com.seleniumframework.utils.dataReaders.ConfigReader;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxFactory extends AbstractDriver {

    private FirefoxOptions getOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless");
        }
        return options;
    }

    @Override
    public WebDriver createDrivers() {
        return new FirefoxDriver(getOptions());
    }
}
