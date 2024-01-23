package com.Utilities;

public class WebElementsHelper {
    
    public String getXpathFromWebElementsIterator(String iteratorValue){
        String[] xpathString;
		System.out.println("Using ListIterator:\n");
        xpathString = iteratorValue.split("xpath:");
    	System.out.println(xpathString[1]);
		return xpathString[1];
    }
}
