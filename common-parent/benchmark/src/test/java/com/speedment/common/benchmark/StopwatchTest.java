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
package com.speedment.common.benchmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

final class StopwatchTest {

    private Stopwatch sw;

    @BeforeEach
    void setup() {
        sw = Stopwatch.create();
    }

    @Test
    void elapsed() {
        sw.start();
        shortSleep();
        assertTrue(sw.elapsed(TimeUnit.NANOSECONDS) > 0L);
    }

    @Test
    void elapsedMillis() {
        sw.start();
        shortSleep();
        assertTrue(sw.elapsedMillis() > 0L);
    }

    @Test
    void elapsedNanos() {
        assertEquals(0L, sw.elapsedNanos());
        sw.start();
        shortSleep();
        assertTrue(sw.elapsedNanos() > 0L);
        sw.stop();
        assertTrue(sw.elapsedNanos() > 0L);
    }

    @Test
    void isStarted() {
        assertFalse(sw.isStarted());
        sw.start();
        assertTrue(sw.isStarted());
    }

    @Test
    void isStopped() {
        assertFalse(sw.isStopped());
        sw.start();
        assertFalse(sw.isStopped());
        sw.stop();
        assertTrue(sw.isStopped());
    }

    @Test
    void start() {
        sw.start();
        assertTrue(sw.isStarted());
    }

    @Test
    void startTwice() {
        sw.start();
        assertThrows(IllegalStateException.class, () -> sw.start());
    }

    @Test
    void stopBeforeStart() {
        assertThrows(IllegalStateException.class, () -> sw.stop());
    }

    @Test
    void stop() {
        assertDoesNotThrow(() -> {
            sw.start();
            sw.stop();
        });
    }

    @Test
    void stopTwice() {
        sw.start();
        sw.stop();
        assertThrows(IllegalStateException.class, () -> sw.stop());
    }


    @Test
    void reset() {
        sw.start();
        shortSleep();
        sw.stop();
        sw.reset();
        assertFalse(sw.isStarted());
        assertEquals(0L, sw.elapsedNanos());
    }

    @Test
    void createStarted() {
        final Stopwatch stopwatch = Stopwatch.createStarted();
        assertTrue(stopwatch.isStarted());
    }

    @Test
    void create() {
        final Stopwatch stopwatch = Stopwatch.create();
        assertFalse(stopwatch.isStarted());
    }

    @Test
    void toStringNotStarted() {
        final String actual = sw.toString();
        assertEquals("0.00 ns", actual);
    }

    @Test
    void toStringStarted() {
        sw.start();
        shortSleep();
        final String actual = sw.toString();
        assertNotEquals("0.00 ns", actual);
    }

    @Test
    void toStringNano() {
        sw.start();
        sleep(1);
        final String actual = sw.toString();
        assertNotNull(actual);
    }

    @Test
    void toStringMicro() {
        sw.start();
        sleep(1 * 2_000);
        final String actual = sw.toString();
        assertNotNull(actual);
    }

    @Test
    void toStringMilli() {
        sw.start();
        sleep(1 * 2_000_000);
        final String actual = sw.toString();
        assertNotNull(actual);
    }

    @Test
    void toStringSecond() {
        sw.start();
        sleep(1 * 2_000_000_000);
        final String actual = sw.toString();
        assertNotNull(actual);
    }



    private void shortSleep() {
        sleep(10 * 1_000_000);
    }

    private void sleep(long durationNs) {
        final long until = System.nanoTime() + durationNs;
        while (System.nanoTime() < until) {
            /* spin wait */
        }
    }

}