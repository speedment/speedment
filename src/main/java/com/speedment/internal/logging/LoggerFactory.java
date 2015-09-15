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
package com.speedment.internal.logging;

import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 */
public interface LoggerFactory {

    /**
     * Creates a new <tt>Logger</tt> bound to the given <tt>binding</tt> type.
     *
     * @param binding the <tt>java.lang.Class</tt> to bind to
     * @return the <b>new</b> <tt>Logger</tt> instance
     */
    Logger create(Class<?> binding);

    /**
     * Creates a new <tt>Logger</tt> bound to the given <tt>binding</tt> string.
     *
     * @param binding the <tt>java.lang.String</tt> to bind to
     * @return the <b>new</b> <tt>Logger</tt> instance
     */
    Logger create(String binding);

    /**
     * Returns the type of the <tt>Logger</tt> that is created by this factory
     * instance.
     *
     * @return the type of the created <tt>Logger</tt>s
     */
    Class<? extends Logger> loggerClass();

    /**
     * Sets the LoggerFormatter for all unique Loggers that this factory has
     * ever produced.
     *
     * @param formatter to use
     */
    void setFormatter(LoggerFormatter formatter);

    /**
     * Returns the formatter used by this LoggerFactory and all its Loggers.
     *
     * @return the formatter used by this LoggerFactory and all its Loggers
     */
    LoggerFormatter getFormatter();

    /**
     * Adds a LoggerEventListener to this LoggerFactory an all its Loggers.
     *
     * @param listener to add
     */
    void addListener(LoggerEventListener listener);

    /**
     * Removes a LoggerEventListener to this LoggerFactory an all its Loggers if
     * it was previously registered.
     *
     * @param listener to remove
     */
    void removeListener(LoggerEventListener listener);

    /**
     * Returns a Stream of Entry with all unique Loggers that has ever been
     * produced by this LoggerFactory.
     *
     * @return a Stream of Entry with all unique Loggers that has ever been
     * produced by this LoggerFactory
     */
    public Stream<Map.Entry<String, Logger>> loggers();

    /**
     * Returns a Stream of LoggerEventListener registered with this
     * LoggerFactory.
     *
     * @return a Stream of LoggerEventListener registered with this
     * LoggerFactory
     */
    public Stream<LoggerEventListener> listeners();

    /**
     * Sets the log level for Loggers that fits the initial path.
     *
     * @param path the start of the logger name that is to be changed
     * @param level the new log level
     */
    void setLevel(String path, Level level);

    /**
     * Sets the log level for the specified class logger.
     *
     * @param clazz of the class logger
     * @param level the new log level
     */
    void setLevel(Class<?> clazz, Level level);

}
