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

import com.speedment.common.logger.Level;
import com.speedment.common.logger.LoggerEvent;
import com.speedment.common.logger.LoggerEventListener;
import com.speedment.common.logger.LoggerFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

final class AbstractLoggerTest {

    private static final String LOGGER_NAME = "c.s.Foo";
    private static final LoggerFormatter LOGGER_FORMATTER = (level, name, message) -> level.toText() + "|" + name + "|" + message;
    //
    private static final Level LEVEL = Level.TRACE;
    private static final String TEXT = "Bar";
    private static final String MESSAGE = "Bazz";
    private static final String MESSAGE2 = "Two";
    private static final String MESSAGE3 = "Three";
    private static final String MESSAGE4 = "Four";
    private static final String FORMATTING_MESSAGE_PREFIX = "aGye#2";
    private static final String FORMATTING_MESSAGE = FORMATTING_MESSAGE_PREFIX + "|%s";
    private static final String FORMATTING_MESSAGE2 = FORMATTING_MESSAGE_PREFIX + "|%s|%s";
    private static final String FORMATTING_MESSAGE3 = FORMATTING_MESSAGE_PREFIX + "|%s|%s|%s";
    private static final String FORMATTING_MESSAGE4 = FORMATTING_MESSAGE_PREFIX + "|%s|%s|%s|%s";
    //
    private static final Exception EXCEPTION = new RuntimeException("SomeError");

    private AbstractLogger instance;
    private String output;

    @BeforeEach
    void setup() {
        instance = new AbstractLogger(LOGGER_NAME, LOGGER_FORMATTER) {
            @Override
            protected void output(String message) {
                output = message;
            }
        };
        instance.setLevel(LEVEL);
    }

    @Test
    void output() {
        instance.output(TEXT);
        assertEquals(TEXT, output);
    }

    @Test
    void getLevel() {
        instance.setLevel(LEVEL);
        assertEquals(LEVEL, instance.getLevel());
    }

    @Test
    void setLevel() {
        instance.setLevel(Level.INFO);
        assertEquals(Level.INFO, instance.getLevel());
        instance.setLevel(LEVEL);
    }

    @Test
    void setLevelAndCheckNoOutput() {
        instance.setLevel(Level.FATAL);
        instance.warn("SomeText");
        assertNull(output);
        instance.setLevel(LEVEL);
    }

    @Test
    void getName() {
        assertEquals(LOGGER_NAME, instance.getName());
    }

    @Test
    void setFormatter() {
        final LoggerFormatter newFormatter = (level, name, message) -> null;
        instance.setFormatter(newFormatter);
        assertSame(newFormatter, instance.getFormatter());
    }

    @Test
    void getFormatter() {
        assertEquals(LOGGER_FORMATTER, instance.getFormatter());
    }

    @Test
    void addListener() {
        final AtomicReference<LoggerEvent> event = new AtomicReference<>();
        final LoggerEventListener listener = event::set;
        instance.addListener(listener);
        instance.fatal("Olle");
        assertNotNull(event.get());
        // Add again
        instance.addListener(listener);
    }

    @Test
    void removeListener() {
        final AtomicReference<LoggerEvent> event = new AtomicReference<>();
        final LoggerEventListener listener = event::set;
        instance.addListener(listener);
        instance.removeListener(listener);
        instance.fatal("Olle");
        assertNull(event.get());
        // Remove again
        instance.removeListener(listener);
    }

    // Trace

    @Test
    void trace() {
        instance.trace(MESSAGE);
        assertEquals(LOGGER_FORMATTER.apply(Level.TRACE, LOGGER_NAME, MESSAGE), output);
    }

    @Test
    void testTrace() {
        instance.trace(EXCEPTION);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(Level.TRACE.toText()));
    }

    @Test
    void testTraceThrowableNull() {
        instance.trace((Throwable) null);
        assertTrue(output.contains(Level.TRACE.toText()));
    }

    @Test
    void testTrace1() {
        instance.trace(EXCEPTION, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testTrace2() {
        instance.trace(EXCEPTION, FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testTrace3() {
        instance.trace(FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE));
    }

    @Test
    void testTrace4() {
        instance.trace(FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testTrace5() {
        instance.trace(FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testTrace6() {
        instance.trace(FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    @Test
    void testTrace7() {
        instance.trace(EXCEPTION, FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testTrace8() {
        instance.trace(EXCEPTION, FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testTrace9() {
        instance.trace(EXCEPTION, FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    // Debug

    @Test
    void debug() {
        instance.debug(MESSAGE);
        assertEquals(LOGGER_FORMATTER.apply(Level.DEBUG, LOGGER_NAME, MESSAGE), output);
    }

    @Test
    void testDebug() {
        instance.debug(EXCEPTION);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(Level.DEBUG.toText()));
    }

    @Test
    void testDebugThrowableNull() {
        instance.debug((Throwable) null);
        assertTrue(output.contains(Level.DEBUG.toText()));
    }

    @Test
    void testDebug1() {
        instance.debug(EXCEPTION, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testDebug2() {
        instance.debug(EXCEPTION, FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testDebug3() {
        instance.debug(FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE));
    }

    @Test
    void testDebug4() {
        instance.debug(FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testDebug5() {
        instance.debug(FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testDebug6() {
        instance.debug(FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    @Test
    void testDebug7() {
        instance.debug(EXCEPTION, FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testDebug8() {
        instance.debug(EXCEPTION, FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testDebug9() {
        instance.debug(EXCEPTION, FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    // Info

    @Test
    void info() {
        instance.info(MESSAGE);
        assertEquals(LOGGER_FORMATTER.apply(Level.INFO, LOGGER_NAME, MESSAGE), output);
    }

    @Test
    void testInfo() {
        instance.info(EXCEPTION);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(Level.INFO.toText()), output);
    }

    @Test
    void testInfoThrowableNull() {
        instance.info((Throwable) null);
        assertTrue(output.contains(Level.INFO.toText()));
    }

    @Test
    void testInfo1() {
        instance.info(EXCEPTION, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testInfo2() {
        instance.info(EXCEPTION, FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testInfo3() {
        instance.info(FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE));
    }

    @Test
    void testInfo4() {
        instance.info(FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testInfo5() {
        instance.info(FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testInfo6() {
        instance.info(FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    @Test
    void testInfo7() {
        instance.info(EXCEPTION, FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testInfo8() {
        instance.info(EXCEPTION, FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testInfo9() {
        instance.info(EXCEPTION, FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    // Warn

    @Test
    void warn() {
        instance.warn(MESSAGE);
        assertEquals(LOGGER_FORMATTER.apply(Level.WARN, LOGGER_NAME, MESSAGE), output);
    }

    @Test
    void testWarn() {
        instance.warn(EXCEPTION);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(Level.WARN.toText()));
    }

    @Test
    void testWarnThrowableNull() {
        instance.warn((Throwable) null);
        assertTrue(output.contains(Level.WARN.toText()));
    }

    @Test
    void testWarn1() {
        instance.warn(EXCEPTION, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testWarn2() {
        instance.warn(EXCEPTION, FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testWarn3() {
        instance.warn(FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE));
    }

    @Test
    void testWarn4() {
        instance.warn(FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testWarn5() {
        instance.warn(FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testWarn6() {
        instance.warn(FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    @Test
    void testWarn7() {
        instance.warn(EXCEPTION, FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testWarn8() {
        instance.warn(EXCEPTION, FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testWarn9() {
        instance.warn(EXCEPTION, FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    // Error

    @Test
    void error() {
        instance.error(MESSAGE);
        assertEquals(LOGGER_FORMATTER.apply(Level.ERROR, LOGGER_NAME, MESSAGE), output);
    }

    @Test
    void testError() {
        instance.error(EXCEPTION);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(Level.ERROR.toText()));
    }

    @Test
    void testErrorThrowableNull() {
        instance.error((Throwable) null);
        assertTrue(output.contains(Level.ERROR.toText()));
    }

    @Test
    void testError1() {
        instance.error(EXCEPTION, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testError2() {
        instance.error(EXCEPTION, FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testError3() {
        instance.error(FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE));
    }

    @Test
    void testError4() {
        instance.error(FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testError5() {
        instance.error(FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testError6() {
        instance.error(FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    @Test
    void testError7() {
        instance.error(EXCEPTION, FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testError8() {
        instance.error(EXCEPTION, FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testError9() {
        instance.error(EXCEPTION, FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }
    
    // Fatal

    @Test
    void fatal() {
        instance.fatal(MESSAGE);
        assertEquals(LOGGER_FORMATTER.apply(Level.FATAL, LOGGER_NAME, MESSAGE), output);
    }

    @Test
    void testFatal() {
        instance.fatal(EXCEPTION);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(Level.FATAL.toText()));
    }

    @Test
    void testFatalThrowableNull() {
        instance.fatal((Throwable) null);
        assertTrue(output.contains(Level.FATAL.toText()));
    }

    @Test
    void testFatal1() {
        instance.fatal(EXCEPTION, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testFatal2() {
        instance.fatal(EXCEPTION, FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(MESSAGE));
    }

    @Test
    void testFatal3() {
        instance.fatal(FORMATTING_MESSAGE, MESSAGE);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE));
    }

    @Test
    void testFatal4() {
        instance.fatal(FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testFatal5() {
        instance.fatal(FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testFatal6() {
        instance.fatal(FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }

    @Test
    void testFatal7() {
        instance.fatal(EXCEPTION, FORMATTING_MESSAGE2, MESSAGE, MESSAGE2);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2));
    }

    @Test
    void testFatal8() {
        instance.fatal(EXCEPTION, FORMATTING_MESSAGE3, MESSAGE, MESSAGE2, MESSAGE3);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3));
    }

    @Test
    void testFatal9() {
        instance.fatal(EXCEPTION, FORMATTING_MESSAGE4, MESSAGE, MESSAGE2, MESSAGE3, MESSAGE4);
        assertTrue(output.contains(RuntimeException.class.getName()));
        assertTrue(output.contains(FORMATTING_MESSAGE_PREFIX + "|" + MESSAGE + "|" + MESSAGE2 + "|" + MESSAGE3 + "|" + MESSAGE4));
    }
}