package com.seleniumframework.utils.actions;

import com.seleniumframework.utils.Waits;
import org.openqa.selenium.*;

public class FrameActions {
    private final Waits wait;

    public FrameActions(WebDriver driver){
        this.wait = new Waits(driver);
    }

    public void switchToFrameByIndex(int index){
        wait.fluentWait().until(driver -> {
            try {
                driver.switchTo().frame(index);
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void switchToFrameByName(String nameOrId){
        wait.fluentWait().until(driver -> {
            try {
                driver.switchTo().frame(nameOrId);
                return true;
            }
            catch (Exception e) {
                return false;
            }
        });
    }

    public void switchToFrameByWebElementId(By frameElement){
        wait.fluentWait().until(driver -> {
            try {
                driver.switchTo().frame(driver.findElement(frameElement));
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

    public void switchToDefaultContent(){
        wait.fluentWait().until(driver -> {
            try {
                driver.switchTo().defaultContent();
                return true;
            } catch (Exception e) {
                return false;
            }
        });
    }

}
