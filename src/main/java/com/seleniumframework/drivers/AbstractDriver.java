package com.seleniumframework.drivers;

import org.openqa.selenium.WebDriver;

public abstract class AbstractDriver {

    // this what creates the driver at the end, it is abstracted as what extends does its implementation, which are
    // the browsers factories such as chrome and firefox...etc
    public abstract WebDriver createDrivers();
}
