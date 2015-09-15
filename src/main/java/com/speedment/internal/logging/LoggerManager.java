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

import com.speedment.internal.logging.impl.SystemOutLoggerFactory;

/**
 *
 * @author pemi
 */
public interface LoggerManager {

    enum Holder {
        INST;
        private LoggerFactory defaultFactory = new SystemOutLoggerFactory();
    }

    static void setFactory(LoggerFactory newFactory) {
        Holder.INST.defaultFactory = newFactory;
    }

    static LoggerFactory gettFactory() {
        return Holder.INST.defaultFactory;
    }

    /**
     * Creates and returns a new <tt>Logger</tt> bound to the given
     * <tt>binding</tt> type using
     *
     * @param binding the <tt>java.lang.Class</tt> to bind to
     * @return the <b>new</b> <tt>Logger</tt> instance
     */
    static Logger getLogger(Class<?> binding) {
        return Holder.INST.defaultFactory.create(binding);
    }

    /**
     * Creates and returns a new <tt>Logger</tt> bound to the given
     * <tt>binding</tt> string using Platform's LoggerFactoryComponent.
     *
     * @param binding the <tt>java.lang.String</tt> to bind to
     * @return the <b>new</b> <tt>Logger</tt> instance
     */
    static Logger getLogger(String binding) {
        return Holder.INST.defaultFactory.create(binding);
    }

    /**
     * Sets the log level for Loggers that fits the initial path. Note that
     * class name are in the form "c.s.i.c.p.c.i.ConnectionPoolComponentImpl"
     *
     * @param path the start of the logger name that is to be changed
     * @param level the new log level
     */
    static void setLevel(String path, Level level) {
        Holder.INST.defaultFactory.setLevel(path, level);
    }

    /**
     * Sets the log level for the specified class logger.
     *
     * @param clazz of the class logger
     * @param level the new log level
     */
    static void setLevel(Class<?> clazz, Level level) {
        Holder.INST.defaultFactory.setLevel(clazz, level);
    }

}
