package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountDeletedPage extends BasePage {

    private final By accountDeletedHeader = By.xpath("//h2[@data-qa='account-deleted']");
    private final By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public AccountDeletedPage(WebDriver driver) {
        super(driver);
    }

    public String getDeletedHeadingText() {
        return elementActions.getText(accountDeletedHeader);
    }

    public HomePage clickContinue() {
        elementActions.click(continueButton);
        return new HomePage(driver);
    }
}
