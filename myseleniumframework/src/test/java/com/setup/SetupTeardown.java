package com.setup;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class SetupTeardown {
    public static String logDirectory ="";
    public static String runDirectory ="";
    @BeforeEach
    public void setup(){
        String currentDirectory =  System.getProperty("user.dir") + "\\src\\test\\java\\com\\Reporting\\";
        
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        SetupTeardown.runDirectory = currentDirectory + "Test Run " + dateFormat.format(date);
        SetupTeardown.logDirectory = SetupTeardown.runDirectory +"\\" + dateFormat.format(date) + ".log";
        new File(SetupTeardown.runDirectory).mkdirs();
        BaseSelenium.setupFileLoggers(SetupTeardown.logDirectory);
        BaseSelenium.initializeExtentReport("testName", SetupTeardown.runDirectory);
        
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
