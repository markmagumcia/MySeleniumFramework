package com.UIMaps.POM;

import com.setup.BaseSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.UIMaps.xpaths.sauceDemoXpaths;
import com.Utilities.JsonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class sauceDemoPOM {
    String sauceDemoURL = "https://www.saucedemo.com/";
    public BaseSelenium selenium;

    public sauceDemoPOM (BaseSelenium selenium) {
        this.selenium = selenium;

    }
    public void loadSauceDemoURL(){
        BaseSelenium.launchBrowser(sauceDemoURL);
    }
    public void loginToSauceDemo(String username, String password){
        selenium.enterText(sauceDemoXpaths.USERNAME, username);
        selenium.enterText(sauceDemoXpaths.PASSWORD, password);
        selenium.clickElement(sauceDemoXpaths.LOGIN_BUTTON);
    }

    public boolean isDashboardDisplayed(){
        return selenium.isElementDisplayed(sauceDemoXpaths.DASHBOARD);
   }

    public void makeInventoryJSON(){
        String xpathString,itemName,itemDesc,itemPrice;
        JsonHelper json = new JsonHelper();
        ObjectNode resultJSON = json.createParentJSON();
        WebElement element;
        int dashboardItemsCount;
        dashboardItemsCount= selenium.findElements(sauceDemoXpaths.INVENTORY_ITEMS).size();

        
        for (int i = 1; i<=dashboardItemsCount; i++){
            ObjectNode childNode=json.createChildJSON();
            String xpath = "//div[@class='inventory_item']["+i+"]";
            element = selenium.findElement(By.xpath(xpath));
            itemName = element.findElement(By.xpath("//div[@class='inventory_item_name ']")).getText();
            itemDesc = element.findElement(By.xpath("//div[@class='inventory_item_desc']")).getText();
            itemPrice = element.findElement(By.xpath("//div[@class='inventory_item_price']")).getText();
            json.addFieldsToChild(childNode,"itemName", itemName);
            json.addFieldsToChild(childNode,"itemDesc", itemDesc);
            json.addFieldsToChild(childNode,"itemPrice", itemPrice);
            json.addChildToRoot("Test Data#"+i, resultJSON, childNode);
            try {
                System.out.println(json.outputRoot(resultJSON));
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
    }
}
