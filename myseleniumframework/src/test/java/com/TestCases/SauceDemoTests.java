package com.TestCases;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestInstance;

import com.UIMaps.PageObjectConnectors;

import com.setup.SetupTeardown;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SauceDemoTests extends PageObjectConnectors{
    
    @Test
    @Order(2)
    @DisplayName("Verify if Dashboard is Loading after Login")
    public void VerifyDashboardIsLoading(){
        
        sauceDemo.loadSauceDemoURL();
        sauceDemo.loginToSauceDemo("standard_usere","secret_sauce");
        sauceDemo.assertDashboardDisplayed();
        //  try {
        //     selenium.takeScreenshot(SetupTeardown.runDirectory);
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
    }
    
    @Test
    @Order(1)
    @DisplayName("Verify if Sauce Demo Login Page is loading")
    @Tag("my-tag")
    public void SauceDemoLoginPageVerification(TestInfo testInfo){
        sauceDemo.loadSauceDemoURL();
        sauceDemo.asserLoginPageIsLoading();
        System.out.println(testInfo.getDisplayName());
        System.out.println(testInfo.getTags());
        System.out.println(testInfo.getTestClass());
        System.out.println(testInfo.getTestMethod());
    }
    
}
