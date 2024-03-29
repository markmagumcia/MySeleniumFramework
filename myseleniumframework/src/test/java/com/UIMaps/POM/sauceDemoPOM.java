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

    /**
     * Assigns Selenium instance to this Page Object Model
     * @param selenium
     */
    public sauceDemoPOM(BaseSelenium selenium) {
        this.selenium = selenium;
    }

    /**
     * Loads the page described by the URL
     */
    public void loadSauceDemoURL() {
        BaseSelenium.launchBrowser(sauceDemoURL);
        selenium.verifyCondition("Verify Sauce Demo Website Loads",
                selenium.isElementDisplayed(sauceDemoXpaths.USERNAME));
    }
    /**
     * Logs in to Sauce Demo Website
     * @param username
     * @param password
     */

    public void loginToSauceDemo(String username, String password) {
        selenium.enterText(sauceDemoXpaths.USERNAME, username);
        selenium.enterText(sauceDemoXpaths.PASSWORD, password);
        selenium.clickElement(sauceDemoXpaths.LOGIN_BUTTON);
        selenium.verifyCondition("Verify User is Logged in and Dashboard is displayed",
                selenium.isElementDisplayed(sauceDemoXpaths.DASHBOARD));
    }
    /**
     * Asserts Dashboard is displayed
     */
    public void assertDashboardDisplayed() {
        selenium.assertElementIsVisible(sauceDemoXpaths.DASHBOARD);
    }

    /**
     * Assert if Login Page loads
     */
    public void asserLoginPageIsLoading() {
        selenium.assertElementIsVisible(sauceDemoXpaths.LOGIN_BUTTON);
    }

    public boolean isDashboardDisplayed() {
        return selenium.isElementDisplayed(sauceDemoXpaths.DASHBOARD);
    }

    /**
     * Special function to output a JSON of the items in the dashboard
     */
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
            json.addFieldsToChild(childNode, "itemName", itemName);
            json.addFieldsToChild(childNode, "itemDesc", itemDesc);
            json.addFieldsToChild(childNode, "itemPrice", itemPrice);
            json.addChildToRoot("Test Data#" + i, resultJSON, childNode);
            i++;

        }
        try {
            System.out.println(json.outputRoot(resultJSON));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
