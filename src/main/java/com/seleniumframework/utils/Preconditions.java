package com.seleniumframework.utils;

import com.seleniumframework.pages.*;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class Preconditions {
    private final WebDriver driver;

    public Preconditions(WebDriver driver) {
        this.driver = driver;
    }
    public HomePage createNewUserPrecondition(String name, String email, Map<String, Object> details) {
        SignupLoginPage signupLoginPage = new HomePage(driver).clickSignupLogin();
        RegisterPage registerPage = signupLoginPage.signup(name, email);

        registerPage.selectGender((String) details.get("title"));
        registerPage.fillAccountInformation(
                (String) details.get("password"),
                (String) details.get("day"),
                (String) details.get("month"),
                (String) details.get("year"),
                (Boolean) details.get("newsletter"),
                (Boolean) details.get("optin")
        );
        registerPage.fillAddressInformation(
                (String) details.get("firstName"),
                (String) details.get("lastName"),
                (String) details.get("company"),
                (String) details.get("address1"),
                (String) details.get("address2"),
                (String) details.get("country"),
                (String) details.get("state"),
                (String) details.get("city"),
                (String) details.get("zip"),
                (String) details.get("mobile")
        );

        AccountCreatedPage accountCreatedPage = registerPage.clickCreateAccount();
        return accountCreatedPage.clickContinue();
    }
}
