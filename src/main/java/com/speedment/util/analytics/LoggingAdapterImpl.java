package com.speedment.util.analytics;

import com.speedment.core.code.model.java.MainGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author pemi
 */
public class LoggingAdapterImpl implements LoggingAdapter {

    private final static Logger LOGGER = LogManager.getLogger(MainGenerator.class);

    @Override
    public void logError(String errorMessage) {
        LOGGER.error(errorMessage);
    }

    @Override
    public void logMessage(String message) {
        LOGGER.info(message);
    }

}
