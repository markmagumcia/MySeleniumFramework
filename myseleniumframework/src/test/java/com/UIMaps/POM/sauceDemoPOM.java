package com.UIMaps.POM;

import com.setup.BaseSelenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.UIMaps.xpaths.sauceDemoXpaths;
import com.Utilities.JsonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class sauceDemoPOM {
    String sauceDemoURL = "https://www.saucedemo.com/";
    public BaseSelenium selenium;

    public sauceDemoPOM(BaseSelenium selenium) {
        this.selenium = selenium;

    }

    public void loadSauceDemoURL() {
        BaseSelenium.launchBrowser(sauceDemoURL);
    }

    public void loginToSauceDemo(String username, String password) {
        selenium.enterText(sauceDemoXpaths.USERNAME, username);
        selenium.enterText(sauceDemoXpaths.PASSWORD, password);
        selenium.clickElement(sauceDemoXpaths.LOGIN_BUTTON);
    }

    public void assertDashboardDisplayed(){
        selenium.assertElementIsVisible(sauceDemoXpaths.DASHBOARD);
    }

    public void asserLoginPageIsLoading(){
        selenium.assertElementIsVisible(sauceDemoXpaths.LOGIN_BUTTON);
    }

    public boolean isDashboardDisplayed() {
        return selenium.isElementDisplayed(sauceDemoXpaths.DASHBOARD);
    }

    public void makeInventoryJSON() {
        String elementID, itemName, itemDesc, itemPrice;
        String[] split;
        JsonHelper json = new JsonHelper();
        ObjectNode resultJSON = json.createParentJSON();
       
        int i = 1;
        List<WebElement> listOfElements = selenium.findElements("//div[@class='inventory_item']/div/a");
        for (WebElement e : listOfElements) {
            ObjectNode childNode = json.createChildJSON();
            WebElement itemDescElement;
            elementID = e.getAttribute("id");
            split = elementID.split("_");
            String itemDescXpath = "//a[contains(@id,'item_" + split[1]
                    + "_')]/parent::div[@class='inventory_item_label']";
            String itemPriceXpath = "//a[contains(@id,'item_" + split[1]
                    + "_')]/../..//div[@class='inventory_item_price']";
            itemDescElement = selenium.findElement(By.xpath(itemDescXpath));
            itemName = itemDescElement.findElement(By.xpath(".//div[@class='inventory_item_name ']")).getText();
            itemDesc = itemDescElement.findElement(By.xpath(".//div[@class='inventory_item_desc']")).getText();
            itemPrice = selenium.findElement(By.xpath(itemPriceXpath)).getText();
            // System.out.println(itemName);
            // System.out.println(itemDesc);
            // System.out.println(itemPrice);
            json.addFieldsToChild(childNode, "itemName", itemName);
            json.addFieldsToChild(childNode, "itemDesc", itemDesc);
            json.addFieldsToChild(childNode, "itemPrice", itemPrice);
            json.addChildToRoot("Test Data#" + i, resultJSON, childNode);
            i++;

        }
        try {
            System.out.println(json.outputRoot(resultJSON));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
   

}
