package com.Utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerLoad {
    private static Logger logger = LogManager.getLogger();
    public static void info(String message){
        logger.info(message);
    }
}
