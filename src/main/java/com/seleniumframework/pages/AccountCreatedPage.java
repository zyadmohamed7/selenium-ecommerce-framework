package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountCreatedPage extends BasePage {

    private final By accountCreatedHeader = By.xpath("//h2[@data-qa='account-created']");
    private final By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public AccountCreatedPage(WebDriver driver) {
        super(driver);
    }

    public String getCreatedHeadingText() {
        return elementActions.getText(accountCreatedHeader);
    }

    public HomePage clickContinue() {
        elementActions.click(continueButton);
        return new HomePage(driver);
    }
}
