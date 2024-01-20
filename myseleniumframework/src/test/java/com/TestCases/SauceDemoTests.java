package com.TestCases;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.UIMaps.PageObjectConnectors;
import com.setup.BaseSelenium;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SauceDemoTests extends PageObjectConnectors{

    @Test
    public void test1(){

        sauceDemo.loadSauceDemoURL();
        sauceDemo.loginToSauceDemo("standard_user","secret_sauce");
        assertTrue(sauceDemo.isDashboardDisplayed());
        //sauceDemo.makeInventoryJSON();
        


    }
    @Test
    public void test2(){
        BaseSelenium.setupFileLoggers();
        sauceDemo.loadSauceDemoURL();
       


    }
}
