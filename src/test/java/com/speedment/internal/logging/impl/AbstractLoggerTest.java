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
import static com.speedment.internal.logging.Level.DEBUG;
import static com.speedment.internal.logging.Level.ERROR;
import static com.speedment.internal.logging.Level.FATAL;
import static com.speedment.internal.logging.Level.INFO;
import static com.speedment.internal.logging.Level.TRACE;
import static com.speedment.internal.logging.Level.WARN;
import com.speedment.internal.logging.LoggerEventListener;
import com.speedment.internal.logging.LoggerFormatter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author pemi
 */
public class AbstractLoggerTest {

    private AbstractLoggerTestImpl instance;

    public AbstractLoggerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new AbstractLoggerTestImpl();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of output method, of class AbstractLogger.
     */
    @Test
    public void testOutput() {
        System.out.println("output");
        String message = "Hi";
        instance.output(message);
        // TODO review the generated test code and remove the default call to fail.
        List<String> output = instance.getOutput();
        assertEquals(1, output.size());
        assertTrue(output.get(0).contains("Hi"));
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    public void testLog_3args_1() {
        System.out.println("log");
        Level level = Level.ERROR;
        Optional<Throwable> throwable = Optional.of(new IllegalArgumentException("Olle"));
        String message = "Stor Stina";
        instance.log(level, throwable, message);

        List<String> output = instance.getOutput();
        assertEquals(1, output.size());
        assertTrue(output.get(0).contains("Olle"));
        assertTrue(output.get(0).contains(AbstractLoggerTest.class.getName())); // Check that there is a stack trace.
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    public void reproduceThrowableProblem() {
        System.out.println("reproduceProblem");
        Level level = Level.ERROR;
        Throwable throwable = new SQLException("Unable to get connection");
        String message = "Stor Stina";
        instance.error(throwable, message);

        List<String> output = instance.getOutput();
        assertEquals(1, output.size());
        assertTrue(output.get(0).contains("connection"));
        assertTrue(output.get(0).contains(AbstractLoggerTest.class.getName())); // Check that there is a stack trace.
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    public void reproduceSetLevelProblem() {

        instance.setLevel(TRACE);

        instance.trace(TRACE.toString().toLowerCase());
        instance.debug(DEBUG.toString().toLowerCase());
        instance.info(INFO.toString().toLowerCase());
        instance.warn(WARN.toString().toLowerCase());
        instance.error(ERROR.toString().toLowerCase());
        instance.fatal(FATAL.toString().toLowerCase());

        List<String> output = instance.getOutput();
        assertEquals(6, output.size());

        String text = output.stream().collect(Collectors.joining(", "));

        for (Level level : Level.values()) {
            assertTrue(text.contains(level.toString())); // Is the level as such in the text
            assertTrue(text.contains(level.toString().toLowerCase())); // Is the message in the text
        }
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testLog_4args() {
        System.out.println("log");
        Level level = null;
        Optional<Throwable> throwable = null;
        String message = "";
        Object arg = null;
        AbstractLogger instance = null;
        instance.log(level, throwable, message, arg);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testLog_5args() {
        System.out.println("log");
        Level level = null;
        Optional<Throwable> throwable = null;
        String message = "";
        Object arg1 = null;
        Object arg2 = null;
        AbstractLogger instance = null;
        instance.log(level, throwable, message, arg1, arg2);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testLog_6args() {
        System.out.println("log");
        Level level = null;
        Optional<Throwable> throwable = null;
        String message = "";
        Object arg1 = null;
        Object arg2 = null;
        Object arg3 = null;
        AbstractLogger instance = null;
        instance.log(level, throwable, message, arg1, arg2, arg3);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testLog_7args() {
        System.out.println("log");
        Level level = null;
        Optional<Throwable> throwable = null;
        String message = "";
        Object arg1 = null;
        Object arg2 = null;
        Object arg3 = null;
        Object[] args = null;
        AbstractLogger instance = null;
        instance.log(level, throwable, message, arg1, arg2, arg3, args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of log method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testLog_3args_2() {
        System.out.println("log");
        Level msgLevel = null;
        Optional<Throwable> throwable = null;
        Supplier<String> supplier = null;
        AbstractLogger instance = null;
        //instance.log(msgLevel, throwable, supplier);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fixMessage method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testFixMessage() {
        System.out.println("fixMessage");
        Level level = null;
        String msg = "";
        Optional<Throwable> throwable = null;
        AbstractLogger instance = null;
        String expResult = "";
        //String result = instance.fixMessage(level, msg, throwable);
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLevel method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testGetLevel() {
        System.out.println("getLevel");
        AbstractLogger instance = null;
        Level expResult = null;
        Level result = instance.getLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLevel method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testSetLevel() {
        System.out.println("setLevel");
        Level level = null;
        AbstractLogger instance = null;
        instance.setLevel(level);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testGetName() {
        System.out.println("getName");
        AbstractLogger instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFormatter method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testSetFormatter() {
        System.out.println("setFormatter");
        LoggerFormatter formatter = null;
        AbstractLogger instance = null;
        instance.setFormatter(formatter);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFormatter method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testGetFormatter() {
        System.out.println("getFormatter");
        AbstractLogger instance = null;
        LoggerFormatter expResult = null;
        LoggerFormatter result = instance.getFormatter();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addListener method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testAddListener() {
        System.out.println("addListener");
        LoggerEventListener listener = null;
        AbstractLogger instance = null;
        instance.addListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeListener method, of class AbstractLogger.
     */
    @Test
    @Ignore
    public void testRemoveListener() {
        System.out.println("removeListener");
        LoggerEventListener listener = null;
        AbstractLogger instance = null;
        instance.removeListener(listener);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    private static final LoggerFormatter FORMATTER = (Level level1, String name1, String message) -> level1 + "::" + name1 + "::" + message;

    public class AbstractLoggerTestImpl extends AbstractLogger {

        private final List<String> output;

        public AbstractLoggerTestImpl(String name) {
            super(name, FORMATTER);
            output = new ArrayList<>();
        }

        public AbstractLoggerTestImpl() {
            this(AbstractLogger.class.getName());
        }

        @Override
        public void output(String message) {
            getOutput().add(message);
        }

        public List<String> getOutput() {
            return output;
        }
    }

}
