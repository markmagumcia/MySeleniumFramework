package com.UIMaps.POM;

import com.setup.BaseSelenium;
import com.UIMaps.xpaths.sauceDemoXpaths;

public class sauceDemoPOM {
    
    public BaseSelenium selenium;

    public sauceDemoPOM (BaseSelenium selenium) {
        this.selenium = selenium;

    }

    public void loginToSauceDemo(String username, String password){
        selenium.enterText(sauceDemoXpaths.USERNAME, username);
        selenium.enterText(sauceDemoXpaths.PASSWORD, password);
        selenium.clickElement(sauceDemoXpaths.LOGIN_BUTTON);
    }
}
