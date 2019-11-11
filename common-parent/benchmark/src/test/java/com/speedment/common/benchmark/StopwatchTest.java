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
    void stop() {
        assertThrows(IllegalStateException.class, () -> sw.stop());
        sw.start();
        sw.stop();
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

    private void shortSleep() {
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}