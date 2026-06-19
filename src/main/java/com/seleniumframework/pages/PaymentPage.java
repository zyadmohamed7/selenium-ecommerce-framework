package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage extends BasePage {

    private final By nameOnCardInput = By.xpath("//input[@name='name_on_card']");
    private final By cardNumberInput = By.xpath("//input[@name='card_number']");
    private final By cvcInput = By.xpath("//input[@name='cvc']");
    private final By expiryMonthInput = By.xpath("//input[@name='expiry_month']");
    private final By expiryYearInput = By.xpath("//input[@name='expiry_year']");
    private final By payButton = By.id("submit");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public PaymentSuccessPage payAndConfirmOrder(String name, String cardNumber, String cvc, String month, String year) {
        elementActions.type(nameOnCardInput, name);
        elementActions.type(cardNumberInput, cardNumber);
        elementActions.type(cvcInput, cvc);
        elementActions.type(expiryMonthInput, month);
        elementActions.type(expiryYearInput, year);
        elementActions.click(payButton);
        return new PaymentSuccessPage(driver);
    }
}
