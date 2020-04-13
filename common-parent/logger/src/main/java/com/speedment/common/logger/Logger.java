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
package com.speedment.common.logger;

/**
 * Inspiration from tengi, An Open Source Project under the Apache 2 License
 */
public interface Logger {

    /**
     * Returns the current log level.
     *
     * @return the current log level
     */
    Level getLevel();

    /**
     * Sets the current log level.
     *
     * @param level the new current log level
     */
    void setLevel(Level level);

    /**
     * Sets the formatter.
     *
     * @param formatter  the formatter
     */
    void setFormatter(LoggerFormatter formatter);

    /**
     * Returns the formatter.
     *
     * @return  the formatter
     */
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

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#TRACE}.
     *
     * @param message the non-null message to log
     */
    void trace(String message);

    /**
     * Logs a {@code throwable} at level
     * {@link com.speedment.common.logger.Level#TRACE}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(Throwable throwable);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     */
    void trace(String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#TRACE}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#TRACE}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(Throwable throwable, String message);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#TRACE}. The given
     * throwable will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(Throwable throwable, String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#TRACE}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(Throwable throwable, String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#TRACE}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#TRACE}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void trace(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#DEBUG}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(String message);

    /**
     * Logs a {@code throwable} at level
     * {@link com.speedment.common.logger.Level#DEBUG}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(Throwable throwable);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#DEBUG}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#DEBUG}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(Throwable throwable, String message);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#DEBUG}. The given
     * throwable will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(Throwable throwable, String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#DEBUG}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(Throwable throwable, String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#DEBUG}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#DEBUG}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void debug(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#INFO}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(String message);

    /**
     * Logs a {@code throwable} at level
     * {@link com.speedment.common.logger.Level#INFO}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(Throwable throwable);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#INFO}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#INFO}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#INFO}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#INFO}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#INFO}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(Throwable throwable, String message);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#INFO}. The given
     * throwable will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(Throwable throwable, String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#INFO}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(Throwable throwable, String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#INFO}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#INFO}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void info(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#WARN}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(String message);

    /**
     * Logs a {@code throwable} at level
     * {@link com.speedment.common.logger.Level#INFO}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(Throwable throwable);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#WARN}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#WARN}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#WARN}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#WARN}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#WARN}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(Throwable throwable, String message);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#WARN}. The given
     * throwable will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(Throwable throwable, String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#WARN}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(Throwable throwable, String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#WARN}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#WARN}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void warn(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#ERROR}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(String message);

    /**
     * Logs a {@code throwable} at level
     * {@link com.speedment.common.logger.Level#ERROR}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(Throwable throwable);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#ERROR}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#ERROR}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(Throwable throwable, String message);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#ERROR}. The given
     * throwable will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(Throwable throwable, String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#ERROR}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(Throwable throwable, String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#ERROR}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#ERROR}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void error(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#FATAL}.
     *
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(String message);

    /**
     * Logs a {@code throwable} at level
     * {@link com.speedment.common.logger.Level#FATAL}.
     *
     * @param throwable the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(Throwable throwable);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#FATAL}.
     *
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(String format, Object arg1, Object arg2, Object arg3, Object... args);

    /**
     * Logs a {@code message} at level
     * {@link com.speedment.common.logger.Level#FATAL}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param message the message to log
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(Throwable throwable, String message);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed {@code arg}
     * at level {@link com.speedment.common.logger.Level#FATAL}. The given
     * throwable will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg the argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(Throwable throwable, String format, Object arg);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#FATAL}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(Throwable throwable, String format, Object arg1, Object arg2);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#FATAL}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3);

    /**
     * Logs a message based on the given {@code format} and enriched with the
     * passed arguments at level
     * {@link com.speedment.common.logger.Level#FATAL}. The given throwable
     * will also be logged if configured but does not need to be set.
     *
     * @param throwable the {@code Throwable} to log, or null
     * @param format the format to log
     * @param arg1 the first argument to pass to the format
     * @param arg2 the second argument to pass to the format
     * @param arg3 the third argument to pass to the format
     * @param args additional parameters to pass to the format
     * @throws java.lang.NullPointerException whenever {@code level} or
     * {@code message} is null
     */
    void fatal(Throwable throwable, String format, Object arg1, Object arg2, Object arg3, Object... args);
}
