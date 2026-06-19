package com.seleniumframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage extends BasePage {

    private final By searchBar = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By firstProductAddToCart = By.xpath("(//div[@class='productinfo text-center']//a[contains(text(),'Add to cart')])[1]");
    private final By viewProductFirst = By.xpath("(//a[contains(text(),'View Product')])[1]");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void searchProduct(String productName) {
        elementActions.type(searchBar, productName);
        elementActions.click(searchButton);
    }

    public void addFirstProductToCart() {
        elementActions.click(firstProductAddToCart);
    }

    public ProductDetailsPage viewFirstProductDetails() {
        elementActions.click(viewProductFirst);
        return new ProductDetailsPage(driver);
    }
}
