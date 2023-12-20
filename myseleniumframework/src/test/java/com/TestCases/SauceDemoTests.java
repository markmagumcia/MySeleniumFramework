package com.TestCases;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.UIMaps.PageObjectConnectors;

public class SauceDemoTests extends PageObjectConnectors{

    @Test
    public void test1(){
        sauceDemo.loadSauceDemoURL();
        sauceDemo.loginToSauceDemo("standard_user","secret_sauce");
        assertTrue(sauceDemo.isDashboardDisplayed());
        sauceDemo.getelements();


    }
    
}
