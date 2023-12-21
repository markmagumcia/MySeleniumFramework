package com.setup;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseSelenium {
    public static Logger log = LogManager.getLogger();
	//private static String URL = "https://jupiter.cloud.planittesting.com/";
	static WebDriver driver;
	static WebDriverWait wait;

	public BaseSelenium(){

	}

    public static void launchBrowser(String URL) {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		log.info("Launching: {}", URL);
        driver.manage().window().fullscreen();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(URL);

	}
    	public static void closeBrowser(String URL) {
		log.info("Closing: {} ", URL);
		driver.quit();
	}

	public boolean isElementDisplayed(String xpath){
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			log.info("Element {} is displayed", xpath);
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	public WebElement findElement(By by){
		return driver.findElement(by);
	}

	public List<WebElement> findElements(String xpath){
		return driver.findElements(By.xpath(xpath));
	}

	public void verifyElementIsDisplayed(String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		assertTrue(driver.findElement(By.xpath(xpath)).isDisplayed());
	}

	public void verifyElementIsNotDisplayed(String xpath) {
		assertTrue(driver.findElements(By.xpath(xpath)).size() == 0);
	}



	public void enterText(String xpath, String text) {
		WebElement element = driver.findElement(By.xpath(xpath));
		element.clear();
		element.sendKeys(text);
	}

	public String getText(String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		return driver.findElement(By.xpath(xpath)).getText();
	}

	public void clickElement(String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		driver.findElement(By.xpath(xpath)).click();
	}


}
