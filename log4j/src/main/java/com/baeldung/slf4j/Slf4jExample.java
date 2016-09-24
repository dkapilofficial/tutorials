package com.baeldung.slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * To switch between logging frameworks you need only to uncomment needed framework dependencies in pom.xml
 */
public class SLF4JExample {
    private static Logger logger = LoggerFactory.getLogger(SLF4JExample.class);

    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");

        String variable = "Hello John";
        logger.debug("Printing variable value {} ", variable);
    }
}
