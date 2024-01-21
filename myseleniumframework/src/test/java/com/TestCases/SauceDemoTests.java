package com.TestCases;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.UIMaps.PageObjectConnectors;
import com.setup.BaseSelenium;
import com.setup.SetupTeardown;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SauceDemoTests extends PageObjectConnectors{
    String logDirectory;
    @Test
    public void test1(){
        
        sauceDemo.loadSauceDemoURL();
        sauceDemo.loginToSauceDemo("standard_user","secret_sauce");
        assertTrue(sauceDemo.isDashboardDisplayed());
        logDirectory = SetupTeardown.logDirectory;
        try {
            selenium.takeScreenshot(logDirectory);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //sauceDemo.makeInventoryJSON();
        


    }
    @Test
    public void test2(){
     //   BaseSelenium.setupFileLoggers();
        sauceDemo.loadSauceDemoURL();
       


    }
}
