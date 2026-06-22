package com.seleniumframework.pages;

import com.seleniumframework.utils.actions.ElementActions;
import com.seleniumframework.utils.actions.BrowserActions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    protected ElementActions elementActions;
    protected BrowserActions browserActions;

    protected By homeLink = By.xpath("//a[contains(., 'Home')]");
    protected By productsLink = By.xpath("//a[contains(., 'Products')]");
    protected By cartLink = By.xpath("//a[contains(., 'Cart')]");
    protected By signupLoginLink = By.xpath("//a[contains(., 'Signup / Login')]");
    protected By contactUsLink = By.xpath("//a[contains(., 'Contact us')]");
    protected By logoutLink = By.xpath("//a[contains(., 'Logout')]");
    protected By deleteAccountLink = By.xpath("//a[contains(., 'Delete Account')]");
    protected By loggedInAsText = By.xpath("//a[contains(., 'Logged in as')]");

    protected By continueShoppingButton = By.xpath("//button[contains(@class,'close-modal')]");
    protected By viewCartLink = By.xpath("//div[@class='modal-content']//a[contains(., 'View Cart')]");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.elementActions = new ElementActions(driver);
        this.browserActions = new BrowserActions(driver);
    }


    // a transaction method
    public HomePage clickHome() {
        elementActions.click(homeLink);
        return new HomePage(driver);
    }

    public ProductsPage clickProducts() {
        elementActions.click(productsLink);
        return new ProductsPage(driver);
    }

    public CartPage clickCart() {
        elementActions.click(cartLink);
        return new CartPage(driver);
    }

    public SignupLoginPage clickSignupLogin() {
        elementActions.click(signupLoginLink);
        return new SignupLoginPage(driver);
    }

    public ContactUsPage clickContactUs() {
        elementActions.click(contactUsLink);
        return new ContactUsPage(driver);
    }

    public SignupLoginPage clickLogout() {
        elementActions.click(logoutLink);
        return new SignupLoginPage(driver);
    }

    public AccountDeletedPage clickDeleteAccount() {
        elementActions.click(deleteAccountLink);
        return new AccountDeletedPage(driver);
    }

    public String getLoggedInUserText() {
        return elementActions.getText(loggedInAsText);
    }

    public boolean isLoggedInAs(String username) {
        try {
            String text = getLoggedInUserText();
            return text != null && text.contains(username);
        } catch (Exception e) {
            return false;
        }
    }

    public void clickContinueShopping() {
        elementActions.click(continueShoppingButton);
    }

    public CartPage clickViewCartFromModal() {
        elementActions.click(viewCartLink);
        return new CartPage(driver);
    }
}
