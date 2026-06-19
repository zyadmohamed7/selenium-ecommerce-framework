package com.seleniumframework.tests;

import com.seleniumframework.BaseTest;
import com.seleniumframework.pages.*;
import com.seleniumframework.utils.actions.AlertsActions;
import com.seleniumframework.utils.dataReaders.JsonReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.qameta.allure.*;

import java.util.Map;

public class E2ETests extends BaseTest {

    @DataProvider(name = "registrationData")
    public Object[][] getRegistrationData() {
        Map<String, Object> data = JsonReader.readToMap("src/test/resources/test-data/e2e_test_data.json");
        return new Object[][]{{data.get("registrationTest")}};
    }

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() {
        Map<String, Object> data = JsonReader.readToMap("src/test/resources/test-data/e2e_test_data.json");
        return new Object[][]{{data.get("checkoutTest")}};
    }

    @DataProvider(name = "contactUsData")
    public Object[][] getContactUsData() {
        Map<String, Object> data = JsonReader.readToMap("src/test/resources/test-data/e2e_test_data.json");
        return new Object[][]{{data.get("contactUsTest")}};
    }

    @DataProvider(name = "productReviewData")
    public Object[][] getProductReviewData() {
        Map<String, Object> data = JsonReader.readToMap("src/test/resources/test-data/e2e_test_data.json");
        return new Object[][]{{data.get("productReviewTest")}};
    }

    @DataProvider(name = "subscriptionData")
    public Object[][] getSubscriptionData() {
        Map<String, Object> data = JsonReader.readToMap("src/test/resources/test-data/e2e_test_data.json");
        return new Object[][]{{data.get("subscriptionTest")}};
    }

    @Epic("E-Commerce Website Automation")
    @Feature("User Management")
    @Story("User Registration & Deletion")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verify that a new user can successfully register and then delete their account.")
    @Test(dataProvider = "registrationData", priority = 1, description = "Test Case 1: Register User and then Delete Account")
    public void testUserRegistrationAndDeletion(Object dataObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> testData = (Map<String, Object>) dataObj;

        String uniqueEmail = "jane.doe." + System.currentTimeMillis() + "@autoexample.com";
        String name = (String) testData.get("name");

        HomePage homePage = new HomePage(getDriver());
        SignupLoginPage signupLoginPage = homePage.clickSignupLogin();

        RegisterPage registerPage = signupLoginPage.signup(name, uniqueEmail);

        registerPage.selectGender((String) testData.get("title"));
        registerPage.fillAccountInformation(
                (String) testData.get("password"),
                (String) testData.get("day"),
                (String) testData.get("month"),
                (String) testData.get("year"),
                (Boolean) testData.get("newsletter"),
                (Boolean) testData.get("optin")
        );

        registerPage.fillAddressInformation(
                (String) testData.get("firstName"),
                (String) testData.get("lastName"),
                (String) testData.get("company"),
                (String) testData.get("address1"),
                (String) testData.get("address2"),
                (String) testData.get("country"),
                (String) testData.get("state"),
                (String) testData.get("city"),
                (String) testData.get("zip"),
                (String) testData.get("mobile")
        );

        AccountCreatedPage accountCreatedPage = registerPage.clickCreateAccount();

        getVerifications().assertEquals(accountCreatedPage.getCreatedHeadingText(), "ACCOUNT CREATED!", "Verify account creation success banner text");

        homePage = accountCreatedPage.clickContinue();

        getVerifications().assertTrue(homePage.isLoggedInAs(name), "Verify header shows Logged in as " + name);

        AccountDeletedPage accountDeletedPage = homePage.clickDeleteAccount();

        getVerifications().assertEquals(accountDeletedPage.getDeletedHeadingText(), "ACCOUNT DELETED!", "Verify account deletion success banner text");

        accountDeletedPage.clickContinue();
    }

    @Epic("E-Commerce Website Automation")
    @Feature("Checkout & Order placement")
    @Story("Registration during Checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a guest user can search products, add them to cart, register an account during checkout, and place an order.")
    @Test(dataProvider = "checkoutData", priority = 2, description = "Test Case 2: Product checkout with user registration during the process")
    public void testSearchProductsAndCheckout(Object dataObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> testData = (Map<String, Object>) dataObj;

        String productName = (String) testData.get("productToSearch");
        int quantity = (Integer) testData.get("quantity");
        String uniqueEmail = "buyer." + System.currentTimeMillis() + "@autoexample.com";
        String name = (String) testData.get("name");

        HomePage homePage = new HomePage(getDriver());
        ProductsPage productsPage = homePage.clickProducts();

        productsPage.searchProduct(productName);

        ProductDetailsPage productDetailsPage = productsPage.viewFirstProductDetails();
        productDetailsPage.setQuantity(quantity);
        productDetailsPage.clickAddToCart();
        CartPage cartPage = productDetailsPage.clickViewCartFromModal();

        getVerifications().assertTrue(cartPage.isProductInCart(productName), "Verify searched product is added to cart");

        cartPage.clickProceedToCheckout();
        SignupLoginPage signupLoginPage = cartPage.clickRegisterLoginFromModal();

        RegisterPage registerPage = signupLoginPage.signup(name, uniqueEmail);
        registerPage.selectGender((String) testData.get("title"));
        registerPage.fillAccountInformation(
                (String) testData.get("password"),
                (String) testData.get("day"),
                (String) testData.get("month"),
                (String) testData.get("year"),
                false,
                false
        );
        registerPage.fillAddressInformation(
                (String) testData.get("firstName"),
                (String) testData.get("lastName"),
                (String) testData.get("company"),
                (String) testData.get("address1"),
                (String) testData.get("address2"),
                (String) testData.get("country"),
                (String) testData.get("state"),
                (String) testData.get("city"),
                (String) testData.get("zip"),
                (String) testData.get("mobile")
        );

        AccountCreatedPage accountCreatedPage = registerPage.clickCreateAccount();
        getVerifications().assertEquals(accountCreatedPage.getCreatedHeadingText(), "ACCOUNT CREATED!", "Verify registration during checkout is successful");

        homePage = accountCreatedPage.clickContinue();
        getVerifications().assertTrue(homePage.isLoggedInAs(name), "Verify user is logged in after checkout registration");

        cartPage = homePage.clickCart();
        CheckoutPage checkoutPage = cartPage.proceedToCheckoutLoggedIn();

        String deliveryAddrText = checkoutPage.getDeliveryAddressText();
        String billingAddrText = checkoutPage.getBillingAddressText();
        getVerifications().assertTrue(deliveryAddrText.contains((String) testData.get("city")), "Verify delivery address contains city name");
        getVerifications().assertTrue(billingAddrText.contains((String) testData.get("city")), "Verify billing address contains city name");

        checkoutPage.enterComment((String) testData.get("comment"));
        PaymentPage paymentPage = checkoutPage.clickPlaceOrder();

        PaymentSuccessPage successPage = paymentPage.payAndConfirmOrder(
                (String) testData.get("cardName"),
                (String) testData.get("cardNumber"),
                (String) testData.get("cvc"),
                (String) testData.get("cardMonth"),
                (String) testData.get("cardYear")
        );

        getVerifications().assertEquals(successPage.getOrderPlacedHeadingText(), "ORDER PLACED!", "Verify order is successfully placed");
        getVerifications().assertTrue(successPage.getSuccessMessageText().contains("confirmed"), "Verify order confirmation sub-text");

        AccountDeletedPage accountDeletedPage = successPage.clickDeleteAccount();
        getVerifications().assertEquals(accountDeletedPage.getDeletedHeadingText(), "ACCOUNT DELETED!", "Verify account is deleted successfully at end of checkout test");
        accountDeletedPage.clickContinue();
    }

    @Epic("E-Commerce Website Automation")
    @Feature("Support & Feedback")
    @Story("Contact Us Form")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a user can fill and successfully submit the contact us form with an attachment.")
    @Test(dataProvider = "contactUsData", priority = 3, description = "Test Case 3: Contact Us Form Submission and file upload")
    public void testContactUsForm(Object dataObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> testData = (Map<String, Object>) dataObj;

        HomePage homePage = new HomePage(getDriver());
        ContactUsPage contactUsPage = homePage.clickContactUs();

        contactUsPage.fillContactForm(
                (String) testData.get("name"),
                (String) testData.get("email"),
                (String) testData.get("subject"),
                (String) testData.get("message"),
                (String) testData.get("uploadFilePath")
        );

        contactUsPage.clickSubmit();

        AlertsActions alertsActions = new AlertsActions(getDriver());
        alertsActions.acceptAlert();

        String expectedMessage = "Success! Your details have been submitted successfully.";
        getVerifications().assertEquals(contactUsPage.getSuccessMessageText(), expectedMessage, "Verify Contact Us form submit success message");
    }

    @Epic("E-Commerce Website Automation")
    @Feature("Product Actions")
    @Story("Submit Reviews")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that a user can submit a review for a specific product successfully.")
    @Test(dataProvider = "productReviewData", priority = 4, description = "Test Case 4: Submit Review for Product")
    public void testProductReview(Object dataObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> testData = (Map<String, Object>) dataObj;

        HomePage homePage = new HomePage(getDriver());
        ProductsPage productsPage = homePage.clickProducts();

        ProductDetailsPage productDetailsPage = productsPage.viewFirstProductDetails();

        productDetailsPage.submitReview(
                (String) testData.get("name"),
                (String) testData.get("email"),
                (String) testData.get("reviewText")
        );

        getVerifications().assertEquals(productDetailsPage.getReviewSuccessMessageText(), "Thank you for your review.", "Verify review success message");
    }

    @Epic("E-Commerce Website Automation")
    @Feature("Marketing & Newsletter")
    @Story("Homepage Subscription")
    @Severity(SeverityLevel.MINOR)
    @Description("Verify that a user can subscribe to the newsletter from the homepage successfully.")
    @Test(dataProvider = "subscriptionData", priority = 5, description = "Test Case 5: Verify Homepage Subscription")
    public void testCartSubscription(Object dataObj) {
        @SuppressWarnings("unchecked")
        Map<String, Object> testData = (Map<String, Object>) dataObj;

        HomePage homePage = new HomePage(getDriver());
        getBrowserActions().scrollToBottom();

        homePage.enterSubscriptionEmail((String) testData.get("email"));
        homePage.clickSubscribe();

        String expectedMessage = "You have been successfully subscribed!";
        getVerifications().assertEquals(homePage.getSubscriptionSuccessText(), expectedMessage, "Verify subscription success message");
    }
}
