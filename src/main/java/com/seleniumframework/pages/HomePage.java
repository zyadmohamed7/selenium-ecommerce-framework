package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final By subscriptionEmailInput = By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By subscriptionSuccessMessage = By.id("success-subscribe");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void enterSubscriptionEmail(String email) {
        elementActions.type(subscriptionEmailInput, email);
    }

    public void clickSubscribe() {
        elementActions.click(subscribeButton);
    }

    public String getSubscriptionSuccessText() {
        return elementActions.getText(subscriptionSuccessMessage);
    }
}
