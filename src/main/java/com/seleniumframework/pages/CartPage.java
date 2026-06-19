package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By proceedToCheckoutButton = By.xpath("//a[contains(text(),'Proceed To Checkout')]");
    
    private final By registerLoginModalLink = By.xpath("//div[@class='modal-content']//a[contains(.,'Register / Login')]");

    private By getProductInCartLocator(String productName) {
        return By.xpath("//td[@class='cart_description']//a[contains(text(),'" + productName + "')]");
    }

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void clickProceedToCheckout() {
        elementActions.click(proceedToCheckoutButton);
    }

    public SignupLoginPage clickRegisterLoginFromModal() {
        elementActions.click(registerLoginModalLink);
        return new SignupLoginPage(driver);
    }

    public CheckoutPage proceedToCheckoutLoggedIn() {
        elementActions.click(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }

    public boolean isProductInCart(String productName) {
        try {
            return driver.findElement(getProductInCartLocator(productName)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
