package com.TestCases;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.UIMaps.PageObjectConnectors;

import com.setup.SetupTeardown;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SauceDemoTests extends PageObjectConnectors{
    
    @Test
    public void test1(){
        
        sauceDemo.loadSauceDemoURL();
        sauceDemo.loginToSauceDemo("standard_user","secret_sauce");
        assertTrue(sauceDemo.isDashboardDisplayed());
         try {
            selenium.takeScreenshot(SetupTeardown.runDirectory);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // try {
        //     selenium.takeFullScreenshot(SetupTeardown.runDirectory);
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        //sauceDemo.makeInventoryJSON();
        


    }
    @Test
    public void test2(){
     //   BaseSelenium.setupFileLoggers();
        sauceDemo.loadSauceDemoURL();
       


    }
}
