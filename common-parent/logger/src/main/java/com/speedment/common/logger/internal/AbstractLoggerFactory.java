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
package com.speedment.common.logger.internal;

import com.speedment.common.logger.*;
import com.speedment.common.logger.internal.formatter.StandardFormatters;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public abstract class AbstractLoggerFactory implements LoggerFactory {

    private final Map<String, Logger> loggers;
    private final Set<LoggerEventListener> listeners;
    private LoggerFormatter formatter;

    AbstractLoggerFactory() {
        loggers = new ConcurrentHashMap<>();
        formatter = StandardFormatters.PLAIN_FORMATTER;
        listeners = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public Logger create(Class<?> binding) {
        requireNonNull(binding);
        return acquireLogger(makeNameFrom(binding));
    }

    protected String makeNameFrom(Class<?> binding) {
        requireNonNull(binding);
        final String[] tokens = binding.getName().split("\\.");
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            if (i == tokens.length - 1) {
                sb.append(tokens[i]);
            } else {
                sb.append(tokens[i].charAt(0)).append('.');
            }
        }
        return sb.toString();
    }

    @Override
    public Logger create(String binding) {
        requireNonNull(binding);
        return acquireLogger(binding);
    }

    @Override
    public synchronized void setFormatter(LoggerFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter);
        forEachLogger(l -> l.setFormatter(formatter));
    }

    @Override
    public synchronized LoggerFormatter getFormatter() {
        return formatter;
    }

    protected Logger acquireLogger(String binding) {
        requireNonNull(binding);
        return loggers.computeIfAbsent(binding, b -> {
            final Logger log = make(b, formatter);
            listeners.forEach(log::addListener);
            return log;
        });
    }

    public abstract Logger make(String binding, LoggerFormatter formatter);

    @Override
    public void addListener(LoggerEventListener listener) {
        requireNonNull(listener);
        if (listeners.add(listener)) {
            forEachLogger(l -> l.addListener(listener));
        }
    }

    @Override
    public void removeListener(LoggerEventListener listener) {
        requireNonNull(listener);
        if (listeners.remove(listener)) {
            forEachLogger(l -> l.removeListener(listener));
        }
    }

    private void forEachLogger(Consumer<Logger> consumer) {
        requireNonNull(consumer);
        loggers().map(Entry::getValue).forEach(consumer);
    }

    @Override
    public Stream<LoggerEventListener> listeners() {
        return listeners.stream();
    }

    @Override
    public Stream<Entry<String, Logger>> loggers() {
        return loggers.entrySet().stream();
    }

    @Override
    public void setLevel(String path, Level level) {
        requireNonNull(path);
        requireNonNull(level);
        loggers()
            .filter(e
                -> e.getKey().startsWith(path)
            )
            .map(Entry::getValue).forEach((Logger l)
            -> l.setLevel(level)
        );
    }

    @Override
    public void setLevel(Class<?> binding, Level level) {
        requireNonNull(binding);
        requireNonNull(level);
        setLevel(makeNameFrom(binding), level);
    }

}
