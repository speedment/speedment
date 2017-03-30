/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.logger.internal.formatter;

import com.speedment.common.logger.Level;
import java.time.Instant;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Per Minborg
 */
public class PlainFormatterTest {

    PlainFormatter instance;

    @Before
    public void setUp() {
        instance = new PlainFormatter();
    }

    @Test
    public void Java9TestInstantNowToString() {
        // Java 9 test
        System.out.println("Instant.now().toString():" + Instant.now().toString());
    }

    @Test
    public void testApply() {
        final String actual = instance.apply(Level.DEBUG, "LOGGER_NAME", "Some message");
        assertTrue(actual.contains("DEBUG"));
        assertTrue(actual.contains("LOGGER_NAME"));
        assertTrue(actual.contains("Some message"));
        assertTrue(actual.contains("-"));
        assertTrue(actual.contains(":"));
        assertTrue(actual.contains("T"));
        assertTrue(actual.contains("Z"));
    }

    @Test
    public void testHighResolutionTime() {
        final String actual = instance.formatInstance("2017-03-30T02:02:42.988290Z");
        System.out.println("Hi resolution:" + actual);
    }

    @Test
    public void testFormatInstanceCorrectLength() {
        final String expected = "2017-03-29T21:55:39.169Z";
        final String actual = instance.formatInstance("2017-03-29T21:55:39.169Z");
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatInstance1Missing() {
        final String expected = "2017-03-29T21:55:39.160Z";
        final String actual = instance.formatInstance("2017-03-29T21:55:39.16Z");
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatInstance2Missing() {
        final String expected = "2017-03-29T21:55:39.100Z";
        final String actual = instance.formatInstance("2017-03-29T21:55:39.1Z");
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatInstance3Missing() {
        final String expected = "2017-03-29T21:55:39.000Z";
        final String actual = instance.formatInstance("2017-03-29T21:55:39.Z");
        System.out.println(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testFormatInstance4Missing() {
        final String expected = "2017-03-29T21:55:39.000Z";
        final String actual = instance.formatInstance("2017-03-29T21:55:39Z");
        System.out.println(actual);
        assertEquals(expected, actual);
    }

}
