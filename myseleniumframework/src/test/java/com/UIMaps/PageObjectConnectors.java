package com.UIMaps;

import com.UIMaps.POM.sauceDemoPOM;
import com.setup.BaseSelenium;
import com.setup.SetupTeardown;



public class PageObjectConnectors extends SetupTeardown{

    protected static BaseSelenium selenium = new BaseSelenium();
    protected static sauceDemoPOM sauceDemo = new sauceDemoPOM(selenium);
    
    
}
