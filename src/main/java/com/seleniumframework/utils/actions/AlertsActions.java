package com.seleniumframework.utils.actions;

import com.seleniumframework.utils.Waits;
import org.openqa.selenium.WebDriver;

public class AlertsActions {

    private final Waits waits;

    public AlertsActions(WebDriver driver) {
        this.waits = new Waits(driver);
    }

    public void acceptAlert() {
        waits.fluentWait().until(driver -> {
            try {
                driver.switchTo().alert().accept();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void dismissAlert() {
        waits.fluentWait().until(driver -> {
            try {
                driver.switchTo().alert().dismiss();
                return true;
            }
            catch (Exception e) {
                return false;
            }
        });
    }

    public String getAlertText() {
        return waits.fluentWait().until(driver -> {
            try {
                String text =driver.switchTo().alert().getText();
                return !text.isEmpty() ? text : null;
            } catch (Exception e) {
                return null;
            }
        });
    }

    public void sendKeysToAlert(String text) {
        waits.fluentWait().until(driver -> {
            try {
                driver.switchTo().alert().sendKeys(text);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }
}
