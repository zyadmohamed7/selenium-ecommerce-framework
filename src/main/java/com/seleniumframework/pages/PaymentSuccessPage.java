package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentSuccessPage extends BasePage {

    private final By orderPlacedHeading = By.xpath("//h2[@data-qa='order-placed']");
    private final By successMessage = By.xpath("//p[contains(text(),'Congratulations!')]");
    private final By downloadInvoiceButton = By.xpath("//a[contains(text(),'Download Invoice')]");
    private final By continueButton = By.xpath("//a[@data-qa='continue-button']");

    public PaymentSuccessPage(WebDriver driver) {
        super(driver);
    }

    public String getOrderPlacedHeadingText() {
        return elementActions.getText(orderPlacedHeading);
    }

    public String getSuccessMessageText() {
        return elementActions.getText(successMessage);
    }

    public void clickDownloadInvoice() {
        elementActions.click(downloadInvoiceButton);
        try {
            Thread.sleep(2000); // Give it a moment to download the file
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public HomePage clickContinue() {
        elementActions.click(continueButton);
        return new HomePage(driver);
    }
}
