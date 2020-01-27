package com.speedment.runtime.core.internal.db;

import com.speedment.common.logger.Level;
import com.speedment.common.logger.Logger;

import java.util.List;
import java.util.stream.Collectors;

public final class SqlQueryLoggerUtil {

    public SqlQueryLoggerUtil() {}

    public static void logOperation(Logger logger, final String sql, final List<?> values) {
        if (logger.getLevel().isEqualOrLowerThan(Level.DEBUG)) {
            final String text = sql + " " + values.stream()
                    .map(o -> o.getClass().getSimpleName() + " " + o.toString())
                    .collect(Collectors.joining(", ", "[", "]"));
            logger.debug(text);
        }
    }
}