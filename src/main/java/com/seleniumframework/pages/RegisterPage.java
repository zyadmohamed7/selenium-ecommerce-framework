package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By genderMrRadio = By.id("id_gender1");
    private final By genderMrsRadio = By.id("id_gender2");

    private final By passwordInput = By.id("password");
    private final By daysDropdown = By.id("days");
    private final By monthsDropdown = By.id("months");
    private final By yearsDropdown = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By optinCheckbox = By.id("optin");

    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By companyInput = By.id("company");
    private final By address1Input = By.id("address1");
    private final By address2Input = By.id("address2");
    private final By countryDropdown = By.id("country");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipcodeInput = By.id("zipcode");
    private final By mobileNumberInput = By.id("mobile_number");

    private final By createAccountButton = By.xpath("//button[@data-qa='create-account']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void selectGender(String title) {
        if ("Mr".equalsIgnoreCase(title)) {
            elementActions.click(genderMrRadio);
        } else {
            elementActions.click(genderMrsRadio);
        }
    }

    public void fillAccountInformation(String password, String day, String month, String year, boolean newsletter, boolean optin) {
        elementActions.type(passwordInput, password);
        elementActions.selectByValue(daysDropdown, day);
        elementActions.selectByValue(monthsDropdown, month);
        elementActions.selectByValue(yearsDropdown, year);
        if (newsletter) {
            elementActions.click(newsletterCheckbox);
        }
        if (optin) {
            elementActions.click(optinCheckbox);
        }
    }

    public void fillAddressInformation(String firstName, String lastName, String company, String address1, String address2,
                                       String country, String state, String city, String zip, String mobile) {
        elementActions.type(firstNameInput, firstName);
        elementActions.type(lastNameInput, lastName);
        elementActions.type(companyInput, company);
        elementActions.type(address1Input, address1);
        elementActions.type(address2Input, address2);
        elementActions.selectByValue(countryDropdown, country);
        elementActions.type(stateInput, state);
        elementActions.type(cityInput, city);
        elementActions.type(zipcodeInput, zip);
        elementActions.type(mobileNumberInput, mobile);
    }

    public AccountCreatedPage clickCreateAccount() {
        elementActions.click(createAccountButton);
        return new AccountCreatedPage(driver);
    }
}
