package com.setup;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TakesScreenshot;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;



public class BaseSelenium {
	public static Logger log = LogManager.getLogger();
	static String currentDirectory =  System.getProperty("user.dir") + "\\src\\test\\logs\\";
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ms");
	//private static String URL = "https://jupiter.cloud.planittesting.com/";
	public static WebDriver driver;
	public static WebDriverWait wait;

	public BaseSelenium(){

	}

    public static void launchBrowser(String URL) {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		log.info("Launching: {}", URL);
        driver.manage().window().maximize();
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

	 public static void setupFileLoggers(String currentDirectory) {
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n")
                .build();
        FileAppender appender = FileAppender.newBuilder().setConfiguration(config).withFileName(currentDirectory)
                .withAppend(false).withLocking(false).setName("File").withAdvertise(false).setLayout(layout).build();
        appender.start();
        config.getRootLogger().removeAppender("File");
        config.addAppender(appender);
        AppenderRef.createAppenderRef("File", null, null);
        config.getRootLogger().addAppender(appender, Level.DEBUG, null);
        context.updateLoggers();
    }
	
	public void takeScreenshot(String directory) throws IOException{
		String screenshotFileName ="";
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		screenshotFileName = "Screenshot" + dateFormat.format(date);
		
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File DestFile = new File(directory+"\\"+ screenshotFileName +".jpg");
		FileUtils.copyFile(scrFile,DestFile);
	}

	public void takeFullScreenshot (String directory) throws IOException {
		String screenshotFileName ="";
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		screenshotFileName = "Full Screenshot " + dateFormat.format(date);
		File DestFile = new File(directory+"\\"+ screenshotFileName +".jpg");
		Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
		ImageIO.write(s.getImage(),"JPG",DestFile);
	}

	public static void initializeExtentReport(String testName, String directory){
		directory = directory + ".html";
		System.out.println(directory);



	}

}
