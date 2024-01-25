package com.setup;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

public class SetupTeardown {
    public static String logDirectory ="";
    public static String runDirectory ="";

   
       
    @BeforeAll
    public void setup(TestInfo testInfo){
        String currentDirectory =  System.getProperty("user.dir") + "\\src\\test\\java\\com\\Reporting\\";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SetupTeardown.runDirectory = currentDirectory + "Test Run " + dateFormat.format(date);
        SetupTeardown.logDirectory = SetupTeardown.runDirectory +"\\" + dateFormat.format(date) + ".log";
        new File(SetupTeardown.runDirectory).mkdirs();
        BaseSelenium.setupFileLoggers(SetupTeardown.logDirectory);
        BaseSelenium.initializeExtentReport(testInfo, SetupTeardown.runDirectory);
        
    }

    @BeforeEach
    public void setupEach(TestInfo testInfo){
        BaseSelenium.logger = BaseSelenium.extent.createTest(testInfo.getDisplayName());
    }

    @AfterEach
    public void teardown(){
       BaseSelenium.driver.close();
       BaseSelenium.extent.flush();

    }

    // public String getDirectory(){
    //     return this.logDirectory;

    // }

}
