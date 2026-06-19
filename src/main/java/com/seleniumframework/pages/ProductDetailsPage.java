package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {

    private final By quantityInput = By.id("quantity");
    private final By addToCartButton = By.xpath("//button[contains(@class,'cart')]");
    
    private final By reviewNameInput = By.id("name");
    private final By reviewEmailInput = By.id("email");
    private final By reviewTextInput = By.id("review");
    private final By submitReviewButton = By.id("button-review");
    private final By reviewSuccessAlert = By.xpath("//span[contains(text(),'Thank you for your review.')]");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public void setQuantity(int quantity) {
        elementActions.type(quantityInput, String.valueOf(quantity));
    }

    public void clickAddToCart() {
        elementActions.click(addToCartButton);
    }

    public void submitReview(String name, String email, String reviewText) {
        elementActions.type(reviewNameInput, name);
        elementActions.type(reviewEmailInput, email);
        elementActions.type(reviewTextInput, reviewText);
        elementActions.click(submitReviewButton);
    }

    public String getReviewSuccessMessageText() {
        return elementActions.getText(reviewSuccessAlert);
    }
}
