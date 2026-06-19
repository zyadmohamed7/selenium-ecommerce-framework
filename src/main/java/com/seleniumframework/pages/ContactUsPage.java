package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ContactUsPage extends BasePage {

    private final By nameInput = By.xpath("//input[@data-qa='name']");
    private final By emailInput = By.xpath("//input[@data-qa='email']");
    private final By subjectInput = By.xpath("//input[@data-qa='subject']");
    private final By messageInput = By.xpath("//textarea[@data-qa='message']");
    private final By fileUploadInput = By.name("upload_file");
    private final By submitButton = By.xpath("//input[@data-qa='submit-button']");
    private final By successMessage = By.xpath("//div[contains(@class,'alert-success')]");

    public ContactUsPage(WebDriver driver) {
        super(driver);
    }

    public void fillContactForm(String name, String email, String subject, String message, String relativeFilePath) {
        elementActions.type(nameInput, name);
        elementActions.type(emailInput, email);
        elementActions.type(subjectInput, subject);
        elementActions.type(messageInput, message);
        if (relativeFilePath != null && !relativeFilePath.isEmpty()) {
            elementActions.uploadFile(fileUploadInput, relativeFilePath);
        }
    }

    public void clickSubmit() {
        elementActions.click(submitButton);
    }

    public String getSuccessMessageText() {
        return elementActions.getText(successMessage);
    }
}
