package com.speedment.common.injector.internal;

import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

final class LogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return String.format(
            "%s: %s%n",
            record.getLevel(),
            record.getMessage()
        );
    }

    static Logger apply(Logger logger) {
        return logger;
        /*
        logger.setUseParentHandlers(false);
        final ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LogFormatter());
        logger.addHandler(handler);
        return logger;*/
    }

}

