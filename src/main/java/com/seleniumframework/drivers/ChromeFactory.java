package com.seleniumframework.drivers;

import com.seleniumframework.utils.dataReaders.ConfigReader;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeFactory extends AbstractDriver {

    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        return options;
    }

    @Override
    public WebDriver createDrivers() {
        return new ChromeDriver(getOptions());
    }
}
