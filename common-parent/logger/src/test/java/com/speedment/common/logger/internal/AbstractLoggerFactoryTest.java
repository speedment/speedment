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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class AbstractLoggerFactoryTest {

    private static final String FORMATTER_TEXT = "CustomFormatter";
    private static final LoggerFormatter FORMATTER = (Level level, String name, String message) -> level.toText() + "|" + name + "|" + message + "|" + FORMATTER_TEXT;

    private AbstractLoggerFactory instance;
    private String output;
    private LoggerEventListener loggerEventListener;
    private LoggerEvent loggerEvent;

    private final class MyLogger extends AbstractLogger {

        public MyLogger(String name, LoggerFormatter formatter) {
            super(name, formatter);
        }

        @Override
        protected void output(String message) {
            output = message;
        }
    }

    @BeforeEach
    void setup() {
        instance = new AbstractLoggerFactory() {
            @Override
            public Logger make(String binding, LoggerFormatter formatter) {
                return new MyLogger(binding, formatter);
            }

            @Override
            public Class<? extends Logger> loggerClass() {
                return MyLogger.class;
            }
        };
        loggerEventListener = le -> loggerEvent = le;
    }

    @Test
    void create() {
        final Logger logger = instance.create(AbstractLoggerFactoryTest.class);
        assertEquals(MyLogger.class, logger.getClass());
        logger.fatal("Foo");
        assertTrue(output.contains(AbstractLoggerFactoryTest.class.getSimpleName()));
    }

    @Test
    void makeNameFrom() {
        assertEquals("c.s.c.l.i.AbstractLoggerFactoryTest", instance.makeNameFrom(AbstractLoggerFactoryTest.class));
    }

    @Test
    void testCreate() {
        final String text = "SomeText";
        final Logger logger = instance.create("SomeBinding");
        assertEquals(MyLogger.class, logger.getClass());
        logger.fatal(text);
        assertTrue(output.contains(text));
    }

    @Test
    void setFormatter() {
        instance.setFormatter(FORMATTER);
        assertEquals(FORMATTER, instance.getFormatter());
        final Logger logger = instance.create("SetFormatterBinding");
        logger.fatal("A");
        assertTrue(output.contains(FORMATTER_TEXT));
    }

    @Test
    void getFormatter() {
        assertNotNull(instance.getFormatter());
        instance.setFormatter(FORMATTER);
        assertEquals(FORMATTER, instance.getFormatter());
    }

    @Test
    void addListener() {
        instance.addListener(loggerEventListener);
        final Logger logger = instance.create(AbstractLoggerFactory.class);
        logger.fatal("A");
        assertNotNull(loggerEvent);
    }

    @Test
    void removeListener() {
        instance.addListener(loggerEventListener);
        instance.removeListener(loggerEventListener);
        final Logger logger = instance.create(AbstractLoggerFactory.class);
        logger.fatal("A");
        assertNull(loggerEvent);
    }

    @Test
    void listeners() {
        assertEquals(0, instance.listeners().count());
        instance.addListener(loggerEventListener);
        assertEquals(loggerEventListener, instance.listeners().findFirst().orElseThrow(NoSuchElementException::new));
    }

    @Test
    void loggers() {
        assertEquals(0, instance.loggers().count());
        instance.create("A");
        assertEquals(1, instance.loggers().count());
    }

    @Test
    void setLevel() {
        final String binding = "A";
        final Logger logger = instance.create(binding);
        instance.setLevel(binding, Level.FATAL);
        assertEquals(Level.FATAL, logger.getLevel());
    }

    @Test
    void testSetLevel() {
        final Logger logger = instance.create(AbstractLoggerFactoryTest.class);
        instance.setLevel(AbstractLoggerFactoryTest.class, Level.FATAL);
        assertEquals(Level.FATAL, logger.getLevel());
    }
}