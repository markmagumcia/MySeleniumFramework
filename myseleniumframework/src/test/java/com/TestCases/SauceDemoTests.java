package com.TestCases;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

import com.UIMaps.PageObjectConnectors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SauceDemoTests extends PageObjectConnectors{
    
    @Test
    @Order(2)
    @DisplayName("Verify if Dashboard is Loading after Login")
    public void VerifyDashboardIsLoading(){
        
        sauceDemo.loadSauceDemoURL();
        sauceDemo.loginToSauceDemo("standard_user","secret_sauce");
        sauceDemo.assertDashboardDisplayed();
        
    }
    
    @Test
    @Order(1)
    @DisplayName("Verify if Sauce Demo Login Page is loading")
    public void SauceDemoLoginPageVerification(TestInfo testInfo){
        sauceDemo.loadSauceDemoURL();
        sauceDemo.asserLoginPageIsLoading();
    
    }
    
}
