package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupLoginPage extends BasePage {

    private final By loginEmailInput = By.xpath("//input[@data-qa='login-email']");
    private final By loginPasswordInput = By.xpath("//input[@data-qa='login-password']");
    private final By loginButton = By.xpath("//button[@data-qa='login-button']");

    private final By signupNameInput = By.xpath("//input[@data-qa='signup-name']");
    private final By signupEmailInput = By.xpath("//input[@data-qa='signup-email']");
    private final By signupButton = By.xpath("//button[@data-qa='signup-button']");

    public SignupLoginPage(WebDriver driver) {
        super(driver);
    }

    public HomePage login(String email, String password) {
        elementActions.type(loginEmailInput, email);
        elementActions.type(loginPasswordInput, password);
        elementActions.click(loginButton);
        return new HomePage(driver);
    }

    public RegisterPage signup(String name, String email) {
        elementActions.type(signupNameInput, name);
        elementActions.type(signupEmailInput, email);
        elementActions.click(signupButton);
        return new RegisterPage(driver);
    }
}
