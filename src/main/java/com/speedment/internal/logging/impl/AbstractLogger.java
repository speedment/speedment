/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.logging.impl;

import com.speedment.internal.logging.Level;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerEvent;
import com.speedment.internal.logging.LoggerEventListener;
import com.speedment.internal.logging.LoggerFormatter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 */
public abstract class AbstractLogger implements Logger {

    private final String name;
    private Level level;
    private LoggerFormatter formatter;
    private final Set<LoggerEventListener> listeners;

    protected AbstractLogger(final String name, final LoggerFormatter formatter) {
        this.name = requireNonNull(name);
        this.level = Level.defaultLevel();
        this.formatter = requireNonNull(formatter);
        listeners = Collections.newSetFromMap(new ConcurrentHashMap<LoggerEventListener, Boolean>());
    }

    protected abstract void output(String message);

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message) {
        log(level, throwable, () -> message);
    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg) {
        log(level, throwable, () -> String.format(message, arg));
    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg1, Object arg2) {
        log(level, throwable, () -> String.format(message, arg1, arg2));
    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg1, Object arg2, Object arg3) {
        log(level, throwable, () -> String.format(message, arg1, arg2, arg3));
    }

    @Override
    public void log(Level level, Optional<Throwable> throwable, String message, Object arg1, Object arg2, Object arg3, Object... args) {
        log(level, throwable, () -> {
            final Object[] params = new Object[args.length + 3];
            params[0] = arg1;
            params[1] = arg2;
            params[2] = arg3;
            System.arraycopy(args, 0, params, 3, args.length);
            return String.format(message, params);
        });
    }

    protected void log(Level msgLevel, Optional<Throwable> throwable, Supplier<String> supplier) {
        if (msgLevel.isEqualOrHigherThan(this.level)) {
            final String logMsg = supplier.get();
            final String outputMessage = fixMessage(msgLevel, logMsg, throwable);
            output(outputMessage);
            if (!listeners.isEmpty()) {
                final LoggerEvent loggerEvent = new LoggerEventImpl(msgLevel, name, outputMessage);
                listeners.stream().forEach(l -> l.accept(loggerEvent));
            }
        }
    }

    protected String fixMessage(Level level, String msg, Optional<Throwable> throwable) {
        final StringBuilder sb = new StringBuilder(formatter.apply(level, name, msg));
        throwable.ifPresent(t -> {
            final StringWriter writer = new StringWriter();
            final PrintWriter pipe = new PrintWriter(writer);
            t.printStackTrace(pipe);
            sb.append("\n").append(writer.toString());
        });
        return sb.toString();
    }

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

}
