/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.common.logger.internal;

import com.speedment.common.logger.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Per Minborg
 */
abstract class AbstractLogger implements Logger {

    private static final String NO_EXCEPTION_TEXT = "";

    private final String name;
    private Level level;
    private LoggerFormatter formatter;
    private final Set<LoggerEventListener> listeners;

    AbstractLogger(final String name, final LoggerFormatter formatter) {
        this.name = requireNonNull(name);
        this.level = Level.defaultLevel();
        this.formatter = requireNonNull(formatter);
        listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    protected abstract void output(String message);

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void setLevel(Level level) {
        this.level = requireNonNull(level);
    }

    public String getName() {
        return name;
    }

    @Override
    public void setFormatter(LoggerFormatter formatter) {
        this.formatter = requireNonNull(formatter);
    }

    @Override
    public LoggerFormatter getFormatter() {
        return formatter;
    }

    @Override
    public void addListener(LoggerEventListener listener) {
        requireNonNull(listener);
        listeners.add(listener);
    }

    @Override
    public void removeListener(LoggerEventListener listener) {
        requireNonNull(listener);
        listeners.remove(listener);
    }

    @Override
    public void trace(String message) {
        log(Level.TRACE, NO_THROWABLE, message);
    }

    @Override
    public void trace(Throwable throwable) {
        log(Level.TRACE, throwable, (throwable == NO_THROWABLE) ? NO_EXCEPTION_TEXT : throwable.getMessage());
    }

    @Override
    public void trace(String format, Object arg) {
        log(Level.TRACE, NO_THROWABLE, format, arg);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2) {
        log(Level.TRACE, NO_THROWABLE, format, arg1, arg2);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.TRACE, NO_THROWABLE, format, arg1, arg2, arg3);
    }

    @Override
    public void trace(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.TRACE, NO_THROWABLE, format, arg1, arg2, arg3, args);
    }

    @Override
    public void trace(Throwable throwable, String message) {
        log(Level.TRACE, throwable, message);
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg) {
        log(Level.TRACE, throwable, format, arg);
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.TRACE, throwable, format, arg1, arg2);
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.TRACE, throwable, format, arg1, arg2, arg3);
    }

    @Override
    public void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.TRACE, throwable, format, arg1, arg2, arg3, args);
    }

    @Override
    public void debug(String message) {
        log(Level.DEBUG, NO_THROWABLE, message);
    }

    @Override
    public void debug(Throwable throwable) {
        log(Level.DEBUG, throwable, (throwable == NO_THROWABLE) ? NO_EXCEPTION_TEXT : throwable.getMessage());
    }

    @Override
    public void debug(String format, Object arg) {
        log(Level.DEBUG, NO_THROWABLE, format, arg);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2) {
        log(Level.DEBUG, NO_THROWABLE, format, arg1, arg2);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.DEBUG, NO_THROWABLE, format, arg1, arg2, arg3);
    }

    @Override
    public void debug(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.DEBUG, NO_THROWABLE, format, arg1, arg2, arg3, args);
    }

    @Override
    public void debug(Throwable throwable, String message) {
        log(Level.DEBUG, throwable, message);
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg) {
        log(Level.DEBUG, throwable, format, arg);
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.DEBUG, throwable, format, arg1, arg2);
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.DEBUG, throwable, format, arg1, arg2, arg3);
    }

    @Override
    public void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.DEBUG, throwable, format, arg1, arg2, arg3, args);
    }

    @Override
    public void info(String message) {
        log(Level.INFO, NO_THROWABLE, message);
    }

    @Override
    public void info(Throwable throwable) {
        log(Level.INFO, throwable, (throwable == NO_THROWABLE) ? NO_EXCEPTION_TEXT : throwable.getMessage());
    }

    @Override
    public void info(String format, Object arg) {
        log(Level.INFO, NO_THROWABLE, format, arg);
    }

    @Override
    public void info(String format, Object arg1, Object arg2) {
        log(Level.INFO, NO_THROWABLE, format, arg1, arg2);
    }

    @Override
    public void info(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.INFO, NO_THROWABLE, format, arg1, arg2, arg3);
    }

    @Override
    public void info(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.INFO, NO_THROWABLE, format, arg1, arg2, arg3, args);
    }

    @Override
    public void info(Throwable throwable, String message) {
        log(Level.INFO, throwable, message);
    }

    @Override
    public void info(Throwable throwable, String format, Object arg) {
        log(Level.INFO, throwable, format, arg);
    }

    @Override
    public void info(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.INFO, throwable, format, arg1, arg2);
    }

    @Override
    public void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.INFO, throwable, format, arg1, arg2, arg3);
    }

    @Override
    public void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.INFO, throwable, format, arg1, arg2, arg3, args);
    }

    @Override
    public void warn(String message) {
        log(Level.WARN, NO_THROWABLE, message);
    }

    @Override
    public void warn(Throwable throwable) {
        log(Level.WARN, throwable, (throwable == NO_THROWABLE) ? NO_EXCEPTION_TEXT : throwable.getMessage());
    }

    @Override
    public void warn(String format, Object arg) {
        log(Level.WARN, NO_THROWABLE, format, arg);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2) {
        log(Level.WARN, NO_THROWABLE, format, arg1, arg2);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.WARN, NO_THROWABLE, format, arg1, arg2, arg3);
    }

    @Override
    public void warn(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.WARN, NO_THROWABLE, format, arg1, arg2, arg3, args);
    }

    @Override
    public void warn(Throwable throwable, String message) {
        log(Level.WARN, throwable, message);
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg) {
        log(Level.WARN, throwable, format, arg);
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.WARN, throwable, format, arg1, arg2);
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.WARN, throwable, format, arg1, arg2, arg3);
    }

    @Override
    public void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.WARN, throwable, format, arg1, arg2, arg3, args);
    }

    @Override
    public void error(String message) {
        log(Level.ERROR, NO_THROWABLE, message);
    }

    @Override
    public void error(Throwable throwable) {
        log(Level.ERROR, throwable, (throwable == NO_THROWABLE) ? NO_EXCEPTION_TEXT : throwable.getMessage());
    }

    @Override
    public void error(String format, Object arg) {
        log(Level.ERROR, NO_THROWABLE, format, arg);
    }

    @Override
    public void error(String format, Object arg1, Object arg2) {
        log(Level.ERROR, NO_THROWABLE, format, arg1, arg2);
    }

    @Override
    public void error(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.ERROR, NO_THROWABLE, format, arg1, arg2, arg3);
    }

    @Override
    public void error(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.ERROR, NO_THROWABLE, format, arg1, arg2, arg3, args);
    }

    @Override
    public void error(Throwable throwable, String message) {
        log(Level.ERROR, throwable, message);
    }

    @Override
    public void error(Throwable throwable, String format, Object arg) {
        log(Level.ERROR, throwable, format, arg);
    }

    @Override
    public void error(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.ERROR, throwable, format, arg1, arg2);
    }

    @Override
    public void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.ERROR, throwable, format, arg1, arg2, arg3);
    }

    @Override
    public void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.ERROR, throwable, format, arg1, arg2, arg3, args);
    }

    @Override
    public void fatal(String message) {
        log(Level.FATAL, NO_THROWABLE, message);
    }

    @Override
    public void fatal(Throwable throwable) {
        log(Level.FATAL, throwable, (throwable == NO_THROWABLE) ? NO_EXCEPTION_TEXT : throwable.getMessage());
    }

    @Override
    public void fatal(String format, Object arg) {
        log(Level.FATAL, NO_THROWABLE, format, arg);
    }

    @Override
    public void fatal(String format, Object arg1, Object arg2) {
        log(Level.FATAL, NO_THROWABLE, format, arg1, arg2);
    }

    @Override
    public void fatal(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.FATAL, NO_THROWABLE, format, arg1, arg2, arg3);
    }

    @Override
    public void fatal(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.FATAL, NO_THROWABLE, format, arg1, arg2, arg3, args);
    }

    @Override
    public void fatal(Throwable throwable, String message) {
        log(Level.FATAL, throwable, message);
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.FATAL, throwable, format, arg1, arg2);
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg) {
        log(Level.FATAL, throwable, format, arg);
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.FATAL, throwable, format, arg1, arg2, arg3);
    }

    @Override
    public void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.FATAL, throwable, format, arg1, arg2, arg3, args);
    }

    protected void log(Level level, Throwable throwable, String message) {
        log(level, throwable, () -> message);
    }

    protected void log(Level level, Throwable throwable, String message, Object arg) {
        log(level, throwable, () -> String.format(message, arg));
    }

    protected void log(Level level, Throwable throwable, String message, Object arg1, Object arg2) {
        log(level, throwable, () -> String.format(message, arg1, arg2));
    }

    protected void log(Level level, Throwable throwable, String message, Object arg1, Object arg2, Object arg3) {
        log(level, throwable, () -> String.format(message, arg1, arg2, arg3));
    }

    protected void log(Level level, Throwable throwable, String message, Object arg1, Object arg2, Object arg3, Object... args) {
        log(level, throwable, () -> {
            final Object[] params = new Object[args.length + 3];
            params[0] = arg1;
            params[1] = arg2;
            params[2] = arg3;
            System.arraycopy(args, 0, params, 3, args.length);
            return String.format(message, params);
        });
    }

    protected void log(Level msgLevel, Throwable throwable, Supplier<String> supplier) {
        if (msgLevel.isEqualOrHigherThan(this.level)) {
            final String logMsg = supplier.get();
            final String outputMessage = fixMessage(msgLevel, logMsg, throwable);
            output(outputMessage);
            if (!listeners.isEmpty()) {
                final LoggerEvent loggerEvent = new LoggerEventImpl(msgLevel, name, outputMessage);
                listeners.forEach(l -> l.accept(loggerEvent));
            }
        }
    }

    private String fixMessage(Level level, String msg, Throwable throwable) {
        final StringBuilder sb = new StringBuilder(formatter.apply(level, name, msg));

        if (NO_THROWABLE != throwable) {
            final StringWriter writer = new StringWriter();
            final PrintWriter pipe = new PrintWriter(writer);
            throwable.printStackTrace(pipe);
            sb.append("\n").append(writer.toString());
        }

        return sb.toString();
    }

    private static final Throwable NO_THROWABLE = null;
}
