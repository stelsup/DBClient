package com.maximus.dbclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiagnosticMessage {

    public enum LoggerType {
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE
    }
    public static void logging (String massage, Throwable throwable, Class<?> inputClass, LoggerType type) {
        final Logger LOGGER = LoggerFactory.getLogger(inputClass);

        switch (type) {

            case ERROR :
                if (throwable == null) {
                    LOGGER.error(massage);
                } else {
                    LOGGER.error(massage, throwable);
                }
                break;
            case WARN :
                if (throwable == null) {
                    LOGGER.warn(massage);
                } else {
                    LOGGER.warn(massage, throwable);
                }
                break;
            case INFO :
                if (throwable == null) {
                    LOGGER.info(massage);
                } else {
                    LOGGER.info(massage, throwable);
                }
                break;
            case DEBUG :
                if (throwable == null) {
                    LOGGER.debug(massage);
                } else {
                    LOGGER.debug(massage, throwable);
                }
                break;
            case TRACE :
                if (throwable == null) {
                    LOGGER.trace(massage);
                } else {
                    LOGGER.trace(massage, throwable);
                }
        }

    }
}
