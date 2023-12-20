package com.UIMaps.POM;

import com.setup.BaseSelenium;
import com.UIMaps.xpaths.sauceDemoXpaths;

public class sauceDemoPOM {
    String sauceDemoURL = "https://www.saucedemo.com/";
    public BaseSelenium selenium;

    public sauceDemoPOM (BaseSelenium selenium) {
        this.selenium = selenium;

    }
    public void loadSauceDemoURL(){
        BaseSelenium.launchBrowser(sauceDemoURL);
    }
    public void loginToSauceDemo(String username, String password){
        selenium.enterText(sauceDemoXpaths.USERNAME, username);
        selenium.enterText(sauceDemoXpaths.PASSWORD, password);
        selenium.clickElement(sauceDemoXpaths.LOGIN_BUTTON);
    }

    public boolean isDashboardDisplayed(){
        return selenium.isElementDisplayed(sauceDemoXpaths.DASHBOARD);
   }

    public void getelements(){
        selenium.findElements(sauceDemoXpaths.INVENTORY_ITEMS);

    }
}
