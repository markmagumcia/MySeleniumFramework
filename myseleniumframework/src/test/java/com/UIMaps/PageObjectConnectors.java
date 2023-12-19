package com.UIMaps;

import com.UIMaps.POM.sauceDemoPOM;
import com.setup.BaseSelenium;

public class PageObjectConnectors{

    protected static BaseSelenium selenium = new BaseSelenium();
    protected static sauceDemoPOM sauceDemo = new sauceDemoPOM(selenium);
    
    
}
