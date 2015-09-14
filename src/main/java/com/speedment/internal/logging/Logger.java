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

import java.util.Optional;

/**
 * Inspiration from tengi, An Open Source Project under the Apache 2 License
 */
public interface Logger {

    static final String NO_EXCEPTION_TEXT = "";

    /**
     * Returns the current log level.
     *
     * @return the current log level
     */
    public Level getLevel();

    /**
     * Sets the current log level.
     *
     * @param level the new current log level
     */
    public void setLevel(Level level);

    /**
     * Logs a <tt>message</tt> at the given
     * {@link com.speedment.internal.logging.Level}. A present {@link Throwable}
     * will also be logged.
     *
     * @param level the non-null {@link com.speedment.internal.logging.Level} to
     * use
     * @param throwable the {@link Throwable} to log (if any)
     * @param message the non-null message to log
     */
    void log(Level level, Optional<Throwable> throwable, String message);

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at the given <tt>level</tt>. A present <tt>throwable</tt> will also be
     * logged.
     *
     * @param level the <tt>Level</tt> to log to
     * @param throwable the <tt>Throwable</tt> to log (if any)
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>format</tt> is null
     */
    void log(Level level, Optional<Throwable> throwable, String format, Object arg);

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at the given <tt>level</tt>. A present
     * <tt>throwable</tt> will also be logged.
     *
     * @param level the <tt>Level</tt> to log to
     * @param throwable the <tt>Throwable</tt> to log (if any)
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>format</tt> is null
     */
    void log(Level level, Optional<Throwable> throwable, String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at the given <tt>level</tt>. A present
     * <tt>throwable</tt> will also be logged.
     *
     * @param level the <tt>Level</tt> to log to
     * @param throwable the <tt>Throwable</tt> to log (if any)
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>format</tt> is null
     */
    void log(Level level, Optional<Throwable> throwable, String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at the given <tt>level</tt>. A present
     * <tt>throwable</tt> will also be logged.
     *
     * @param level the <tt>Level</tt> to log to
     * @param throwable the <tt>Throwable</tt> to log (if any)
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>format</tt> is null
     */
    void log(Level level, Optional<Throwable> throwable, String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#TRACE}.
     *
     * @param message the non-null message to log
     */
    default void trace(String message) {
        log(Level.TRACE, Optional.empty(), message);
    }

    /**
     * Logs a <tt>throwable</tt> at level
     * {@link com.speedment.logging.Level#TRACE}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(Throwable throwable) {
        final Optional<Throwable> oThrowable = Optional.ofNullable(throwable);
        log(Level.TRACE, oThrowable, oThrowable.map(Throwable::getMessage).orElse(NO_EXCEPTION_TEXT));
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     */
    default void trace(String format, Object arg) {
        log(Level.TRACE, Optional.empty(), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(String format, Object arg1, Object arg2) {
        log(Level.TRACE, Optional.empty(), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.TRACE, Optional.empty(), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.TRACE, Optional.empty(), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#TRACE}. The given throwable will also
     * be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(Throwable throwable, String message) {
        log(Level.TRACE, Optional.ofNullable(throwable), message);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#TRACE}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(Throwable throwable, String format, Object arg) {
        log(Level.TRACE, Optional.ofNullable(throwable), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#TRACE}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.TRACE, Optional.ofNullable(throwable), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#TRACE}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.TRACE, Optional.ofNullable(throwable), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#TRACE}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.TRACE, Optional.ofNullable(throwable), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#DEBUG}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(String message) {
        log(Level.DEBUG, Optional.empty(), message);
    }

    /**
     * Logs a <tt>throwable</tt> at level
     * {@link com.speedment.logging.Level#DEBUG}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(Throwable throwable) {
        final Optional<Throwable> oThrowable = Optional.ofNullable(throwable);
        log(Level.DEBUG, oThrowable, oThrowable.map(Throwable::getMessage).orElse(NO_EXCEPTION_TEXT));
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(String format, Object arg) {
        log(Level.DEBUG, Optional.empty(), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(String format, Object arg1, Object arg2) {
        log(Level.DEBUG, Optional.empty(), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.DEBUG, Optional.empty(), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.DEBUG, Optional.empty(), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#DEBUG}. The given throwable will also
     * be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(Throwable throwable, String message) {
        log(Level.DEBUG, Optional.ofNullable(throwable), message);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#DEBUG}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(Throwable throwable, String format, Object arg) {
        log(Level.DEBUG, Optional.ofNullable(throwable), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#DEBUG}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.DEBUG, Optional.ofNullable(throwable), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#DEBUG}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.DEBUG, Optional.ofNullable(throwable), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#DEBUG}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.DEBUG, Optional.ofNullable(throwable), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#INFO}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(String message) {
        log(Level.INFO, Optional.empty(), message);
    }

    /**
     * Logs a <tt>throwable</tt> at level
     * {@link com.speedment.logging.Level#INFO}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(Throwable throwable) {
        final Optional<Throwable> oThrowable = Optional.ofNullable(throwable);
        log(Level.INFO, oThrowable, oThrowable.map(Throwable::getMessage).orElse(NO_EXCEPTION_TEXT));
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#INFO}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(String format, Object arg) {
        log(Level.INFO, Optional.empty(), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#INFO}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(String format, Object arg1, Object arg2) {
        log(Level.INFO, Optional.empty(), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#INFO}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.INFO, Optional.empty(), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#INFO}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.INFO, Optional.empty(), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#INFO}. The given throwable will also
     * be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(Throwable throwable, String message) {
        log(Level.INFO, Optional.ofNullable(throwable), message);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#INFO}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(Throwable throwable, String format, Object arg) {
        log(Level.INFO, Optional.ofNullable(throwable), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#INFO}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.INFO, Optional.ofNullable(throwable), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#INFO}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.INFO, Optional.ofNullable(throwable), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#INFO}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.INFO, Optional.ofNullable(throwable), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#WARN}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(String message) {
        log(Level.WARN, Optional.empty(), message);
    }

    /**
     * Logs a <tt>throwable</tt> at level
     * {@link com.speedment.logging.Level#INFO}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(Throwable throwable) {
        final Optional<Throwable> oThrowable = Optional.ofNullable(throwable);
        log(Level.WARN, oThrowable, oThrowable.map(Throwable::getMessage).orElse(NO_EXCEPTION_TEXT));
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#WARN}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(String format, Object arg) {
        log(Level.WARN, Optional.empty(), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#WARN}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(String format, Object arg1, Object arg2) {
        log(Level.WARN, Optional.empty(), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#WARN}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.WARN, Optional.empty(), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#WARN}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.WARN, Optional.empty(), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#WARN}. The given throwable will also
     * be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(Throwable throwable, String message) {
        log(Level.WARN, Optional.ofNullable(throwable), message);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#WARN}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(Throwable throwable, String format, Object arg) {
        log(Level.WARN, Optional.ofNullable(throwable), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#WARN}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.WARN, Optional.ofNullable(throwable), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#WARN}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.WARN, Optional.ofNullable(throwable), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#WARN}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.WARN, Optional.ofNullable(throwable), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#ERROR}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(String message) {
        log(Level.ERROR, Optional.empty(), message);
    }

    /**
     * Logs a <tt>throwable</tt> at level
     * {@link com.speedment.logging.Level#ERROR}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(Throwable throwable) {
        final Optional<Throwable> oThrowable = Optional.ofNullable(throwable);
        log(Level.ERROR, oThrowable, oThrowable.map(Throwable::getMessage).orElse(NO_EXCEPTION_TEXT));
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(String format, Object arg) {
        log(Level.ERROR, Optional.empty(), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(String format, Object arg1, Object arg2) {
        log(Level.ERROR, Optional.empty(), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.ERROR, Optional.empty(), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.ERROR, Optional.empty(), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#ERROR}. The given throwable will also
     * be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(Throwable throwable, String message) {
        log(Level.ERROR, Optional.ofNullable(throwable), message);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#ERROR}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(Throwable throwable, String format, Object arg) {
        log(Level.ERROR, Optional.ofNullable(throwable), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#ERROR}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.ERROR, Optional.ofNullable(throwable), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#ERROR}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.ERROR, Optional.ofNullable(throwable), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#ERROR}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.ERROR, Optional.ofNullable(throwable), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#FATAL}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(String message) {
        log(Level.FATAL, Optional.empty(), message);
    }

    /**
     * Logs a <tt>throwable</tt> at level
     * {@link com.speedment.logging.Level#FATAL}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(Throwable throwable) {
        final Optional<Throwable> oThrowable = Optional.ofNullable(throwable);
        log(Level.FATAL, oThrowable, oThrowable.map(Throwable::getMessage).orElse(NO_EXCEPTION_TEXT));
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(String format, Object arg) {
        log(Level.FATAL, Optional.empty(), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(String format, Object arg1, Object arg2) {
        log(Level.FATAL, Optional.empty(), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(String format, Object arg1, Object arg2, Object arg3) {
        log(Level.FATAL, Optional.empty(), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.FATAL, Optional.empty(), format, arg1, arg2, arg3, args);
    }

    /**
     * Logs a <tt>message</tt> at level
     * {@link com.speedment.logging.Level#FATAL}. The given throwable will also
     * be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(Throwable throwable, String message) {
        log(Level.FATAL, Optional.ofNullable(throwable), message);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed <tt>arg</tt>
     * at level {@link com.speedment.logging.Level#FATAL}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(Throwable throwable, String format, Object arg) {
        log(Level.FATAL, Optional.ofNullable(throwable), format, arg);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#FATAL}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(Throwable throwable, String format, Object arg1, Object arg2) {
        log(Level.FATAL, Optional.ofNullable(throwable), format, arg1, arg2);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#FATAL}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3) {
        log(Level.FATAL, Optional.ofNullable(throwable), format, arg1, arg2, arg3);
    }

    /**
     * Logs a message based on the given <tt>format</tt> and enriched with the
     * passed arguments at level {@link com.speedment.logging.Level#FATAL}. The
     * given throwable will also be logged if configured but does not need to be
     * set.
     *
     * @param throwable the <tt>Throwable</tt> to log, or null
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever <tt>level</tt> or
     * <tt>message</tt> is null
     */
    default void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args) {
        log(Level.FATAL, Optional.ofNullable(throwable), format, arg1, arg2, arg3, args);
    }

    void setFormatter(LoggerFormatter formatter);

    LoggerFormatter getFormatter();

    /**
     * Adds a LoggerEventListener to this Logger.
     *
     * @param listener to add
     */
    void addListener(LoggerEventListener listener);

    /**
     * Removes a LoggerEventListener to this Logger if it was previously
     * registered.
     *
     * @param listener to remove
     */
    void removeListener(LoggerEventListener listener);

}
