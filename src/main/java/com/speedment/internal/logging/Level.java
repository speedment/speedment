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

/**
 * This <tt>Level</tt> enum defines the supported levels for the logging
 * framework.
 *
 * @author pemi
 */
public enum Level {

    /**
     * The <tt>Trace</tt> Level designates finer-grained informational events
     * than the <tt>Debug</tt> level.
     * <p>
     * This is the lowest level of logging
     */
    TRACE("TRACE"),
    /**
     * The <tt>Debug</tt> Level designates fine-grained informational events
     * that are most useful for debugging an application.
     * <p>
     * This level is higher than:
     * <ul>
     * <li>{@link TRACE}</li>
     * </ul>
     */
    DEBUG("DEBUG"),
    /**
     * The <tt>Info</tt> level designates informational messages that highlight
     * the progress of the application at coarse-grained level.
     * <p>
     * This level is higher than:
     * <ul>
     * <li>{@link TRACE}</li>
     * <li>{@link DEBUG}</li>
     * </ul>
     */
    INFO("INFO "),
    /**
     * The <tt>Warning</tt> level designates potentially harmful situations.
     * <p>
     * This level is higher than:
     * <ul>
     * <li>{@link TRACE}</li>
     * <li>{@link DEBUG}</li>
     * <li>{@link INFO}</li>
     * </ul>
     */
    WARN("WARN "),
    /**
     * The <tt>Warning</tt> level designates potentially harmful situations.
     * <p>
     * This level is higher than:
     * <ul>
     * <li>{@link TRACE}</li>
     * <li>{@link DEBUG}</li>
     * <li>{@link INFO}</li>
     * <li>{@link WARN}</li>
     * </ul>
     */
    ERROR("ERROR"),
    /**
     * The <tt>Fatal</tt> level designates a severe application error event that
     * will most probably lead the application to abort.
     * <p>
     * This level is the highest log level and is higher than:
     * <ul>
     * <li>{@link TRACE}</li>
     * <li>{@link DEBUG}</li>
     * <li>{@link INFO}</li>
     * <li>{@link WARN}</li>
     * <li>{@link ERROR}</li>
     * </ul>
     */
    FATAL("FATAL");

    private final String text;

    private Level(String text) {
        this.text = text;
    }

    /**
     * Returns the default level for the Logging framework.
     *
     * @return the default level for the Logging framework
     */
    public static Level defaultLevel() {
        return INFO;
    }

    /**
     * Returns if this level is equal or lower than the provided level.
     *
     * @param otherLevel to compare to
     * @return if this level is equal or lower than the provided level
     */
    public boolean isEqualOrLowerThan(Level otherLevel) {
        return ordinal() <= otherLevel.ordinal();
    }

    /**
     * Returns if this level is equal or higher than the provided level.
     *
     * @param otherLevel to compare to
     * @return if this level is equal or higher than the provided level
     */
    public boolean isEqualOrHigherThan(Level otherLevel) {
        return ordinal() >= otherLevel.ordinal();
    }

    /**
     * Returns the text to use in the output logger for this level.
     *
     * @return the text to use in the output logger for this level
     */
    public String toText() {
        return text;
    }

}
