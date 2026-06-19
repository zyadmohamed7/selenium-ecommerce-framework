package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {

    private final By deliveryAddressBlock = By.id("address_delivery");
    private final By billingAddressBlock = By.id("address_invoice");
    private final By commentTextArea = By.xpath("//div[@id='ordermsg']/textarea");
    private final By placeOrderButton = By.xpath("//a[contains(text(),'Place Order')]");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public String getDeliveryAddressText() {
        return elementActions.getText(deliveryAddressBlock);
    }

    public String getBillingAddressText() {
        return elementActions.getText(billingAddressBlock);
    }

    public void enterComment(String comment) {
        elementActions.type(commentTextArea, comment);
    }

    public PaymentPage clickPlaceOrder() {
        elementActions.click(placeOrderButton);
        return new PaymentPage(driver);
    }
}
