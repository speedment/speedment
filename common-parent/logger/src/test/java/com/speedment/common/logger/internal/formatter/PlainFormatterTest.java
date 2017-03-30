/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
