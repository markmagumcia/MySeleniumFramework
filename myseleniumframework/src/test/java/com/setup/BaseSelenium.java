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
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

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

	public static ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	public static String screenshotFileName;
	public static String screenshotPath;

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
		
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		BaseSelenium.screenshotFileName = "Screenshot " + dateFormat.format(date)+".png";
		BaseSelenium.screenshotPath = directory+"\\"+BaseSelenium.screenshotFileName;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File DestFile = new File(BaseSelenium.screenshotPath);
		FileUtils.copyFile(scrFile,DestFile );
	}

	public void takeFullScreenshot (String directory) throws IOException {
		
		Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		screenshotFileName = "Full Screenshot " + dateFormat.format(date);
		File DestFile = new File(directory+"\\"+ screenshotFileName +".png");
		Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(2000)).takeScreenshot(driver);
		ImageIO.write(s.getImage(),"JPG",DestFile);
	}

	public static void initializeExtentReport(TestInfo testInfo, String directory){
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
		//logger = extent.createTest(testInfo.getDisplayName());
		// logger.createNode("create node1 ");
		// logger.createNode ("create Node 2");
		

		// //ExtentSparkReporter reporter = new ExtentSparkReporter(directory);
		// String css = "img {width: 400%;} div.test-list{display: none;} div[class=\"test-wrapper row view test-view\"]{flex:auto; width: 140%; max-width:150%;}";
        // reporter.config().setCss(css);
        // reporter.config().setTimelineEnabled(true);
        // reporter.config().setEncoding("utf-8");
        // reporter.config().setJs("js-string");
        // reporter.config().setProtocol(Protocol.HTTPS);
        // reporter.config().setReporter(reporter);
        // extent.attachReporter(reporter);
        // logger = extent.createTest(testName);

	}

	public void verifyCondition(String condition){

	}

	public void assertElementIsVisible(String xpath) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		assertTrue(isElementDisplayed(xpath));
		//String fullPath = SetupTeardown.runDirectory+ ".jpg";
		
		try {
			takeScreenshot(SetupTeardown.runDirectory);
			String fullPath = BaseSelenium.screenshotPath;
			log.info("Assert Element {} visible: PASSED", xpath);
			logger.pass("test PASSED", MediaEntityBuilder.createScreenCaptureFromPath(fullPath).build());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			String fullPath = BaseSelenium.screenshotPath + ".png";
			log.info("Assert Element {} visible: FAILED", xpath);
			logger.fail("test FAILED", MediaEntityBuilder.createScreenCaptureFromPath(fullPath).build());
			e.printStackTrace();
		}
		
	}


}
