package com.setup;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class SetupTeardown {
    public static String logDirectory ="";
    @BeforeEach
    public void setup(){
        String currentDirectory =  System.getProperty("user.dir") + "\\src\\test\\java\\com\\Reporting\\";
        String runDirectory;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        runDirectory = currentDirectory + "Test Run " + dateFormat.format(date);
        SetupTeardown.logDirectory = runDirectory +"\\" + dateFormat.format(date) + ".log";
        new File(runDirectory).mkdirs();
        BaseSelenium.setupFileLoggers(SetupTeardown.logDirectory);
        
    }
    @AfterEach
    public void teardown(){
       BaseSelenium.driver.close();

    }

    // public String getDirectory(){
    //     return this.logDirectory;

    // }

}
