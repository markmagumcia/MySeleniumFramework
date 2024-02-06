package com.setup;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;

public class BaseSelenium {
	public static Logger log = LogManager.getLogger();
	static String currentDirectory = System.getProperty("user.dir") + "\\src\\test\\logs\\";
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-ms");
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static String screenshotFileName;
	public static String screenshotPath;
	
	public BaseSelenium() {

	}

	/**
	 * Opens a browser and launches the url Specified
	 * @param URL
	 */
	public static void launchBrowser(String URL) {
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		log.info("Launching: {}", URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(URL);

	}

	/**
	 * Closes the browser 
	 * @param URL
	 */
	public static void closeBrowser(String URL) {
		log.info("Closing: {} ", URL);
		driver.quit();
	}

	/**
	 * Checks for an Element if it is displayed or not based on the given xpath
	 * @param xpath - xpath of the element
	 * @return - true if element is visible; false if element is not visible
	 */
	public boolean isElementDisplayed(String xpath) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			log.info("Element {} is displayed", xpath);
			return driver.findElement(By.xpath(xpath)).isDisplayed();
		} catch (TimeoutException e) {
			log.info("Element {} is not displayed", xpath);
			return false;
		}

	}

	/**
	 * Returns web element 
	 * @param by
	 * @return
	 */
	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	/**
	 * Returns List of Web Elements 
	 * @param xpath
	 * @return
	 */
	public List<WebElement> findElements(String xpath) {
		return driver.findElements(By.xpath(xpath));
	}

	/**
	 * Verify if Element is not displayed. 
	 * @param xpath
	 */
	public void verifyElementIsNotDisplayed(String xpath) {
		assertTrue(driver.findElements(By.xpath(xpath)).size() == 0);
	}

	/**
	 * Enters text to specified xpath
	 * @param xpath
	 * @param text
	 */
	public void enterText(String xpath, String text) {
		WebElement element = driver.findElement(By.xpath(xpath));
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * Gets the text based on the described element by the xpath
	 * @param xpath
	 * @return
	 */
	public String getText(String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		return driver.findElement(By.xpath(xpath)).getText();
	}

	/**
	 * Performs Click on the described element by the xpath
	 * @param xpath
	 */
	public void clickElement(String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		driver.findElement(By.xpath(xpath)).click();
	}

	/**
	 * This method initializes the test loggers
	 * @param currentDirectory
	 */

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

	/**
	 * Performs screenshot
	 * @param directory
	 */
	public void takeScreenshot(String directory) {

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

		try {
			BaseSelenium.screenshotFileName = "Screenshot " + dateFormat.format(date) + ".png";
			BaseSelenium.screenshotPath = directory + "\\" + BaseSelenium.screenshotFileName;
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File DestFile = new File(BaseSelenium.screenshotPath);
			FileUtils.copyFile(scrFile, DestFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initializes Extents report
	 * @param testInfo - the @Test Info in the tests class
	 * @param directory - run directory that was initiated
	 */

	public static void initializeExtentReport(TestInfo testInfo, String directory) {
		directory = directory + ".html";
		System.out.println(directory);

		extent = new ExtentReports();
		spark = new ExtentSparkReporter(directory);
		extent.attachReporter(spark);
		extent.setSystemInfo("Host Name", "Test trial");
		extent.setSystemInfo("Environment", "TestEnv");
		extent.setSystemInfo("User Name", "saucedemoUserName");
		spark.config().setDocumentTitle(testInfo.getTestClass().toString());
		spark.config().setReportName("this is report name");
		spark.config().setTheme(Theme.STANDARD);
		
	}

	/**
	 * Verifies a step. Adds a step entry to the reporting file generated
	 * @param stepDescription
	 * @param stepResult
	 */

	public void verifyCondition(String stepDescription, Boolean stepResult) {
		ExtentTest node;
		node = BaseSelenium.logger.createNode(stepDescription);
		String fullPath = "";

		takeScreenshot(SetupTeardown.runDirectory);
		fullPath = BaseSelenium.screenshotPath;

		if (stepResult) {
			node.getModel().setStatus(Status.PASS);
			node.pass("Verification PASSED", MediaEntityBuilder.createScreenCaptureFromPath(fullPath).build());

		} else {
			node.getModel().setStatus(Status.FAIL);
			node.fail("Verification FAILED", MediaEntityBuilder.createScreenCaptureFromPath(fullPath).build());
		}

	}

	/**
	 * Triggers JUNIT Assert. This asserts if Element described by xpat is visible. 
	 * @param xpath
	 */
	public void assertElementIsVisible(String xpath) {
		String fullPath = "";
		boolean elementVisible;
		
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
			elementVisible = true;
		} catch (TimeoutException e) {
			elementVisible = false;
		}
		takeScreenshot(SetupTeardown.runDirectory);
		fullPath = BaseSelenium.screenshotPath;
		if (elementVisible) {
			
			log.info("Assert Element {} visible: PASSED", xpath);
			BaseSelenium.logger.pass("Test PASSED", MediaEntityBuilder.createScreenCaptureFromPath(fullPath).build());
			assertTrue(elementVisible, "Element Visible");
		} else {
			log.info("Assert Element {} visible: FAILED", xpath);
			BaseSelenium.logger.fail("Test FAILED", MediaEntityBuilder.createScreenCaptureFromPath(fullPath).build());
			assertTrue(elementVisible, "Element NOT Visible");
		}

	}

}
