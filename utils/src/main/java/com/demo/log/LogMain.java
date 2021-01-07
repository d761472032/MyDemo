package com.demo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMain {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(LogMain.class);
        logger.info("Hello World!");
        logger.error("error");
        logger.debug("debug");
        logger.warn("{}", "warn");
    }

}
