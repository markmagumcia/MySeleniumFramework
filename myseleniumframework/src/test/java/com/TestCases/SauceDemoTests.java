package com.TestCases;

import org.junit.jupiter.api.Test;

import com.UIMaps.PageObjectConnectors;

public class SauceDemoTests extends PageObjectConnectors{

    @Test
    public void test1(){
        sauceDemo.loadSauceDemoURL();
        sauceDemo.loginToSauceDemo("standard_user","secret_sauce");
    }
    
}
