package com.speedment.common.benchmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

final class StopwatchTest {

    private Stopwatch sw;

    @BeforeEach
    void setup() {
        sw = Stopwatch.create();
    }

    @Test
    void elapsed() throws InterruptedException {
        sw.start();
        sleep(10);
        assertTrue(sw.elapsed(TimeUnit.NANOSECONDS) > 0L);
    }

    @Test
    void elapsedMillis() throws InterruptedException {
        sw.start();
        sleep(10);
        assertTrue(sw.elapsedMillis() > 0L);
    }

    @Test
    void elapsedNanos() throws InterruptedException {
        assertEquals(0L, sw.elapsedNanos());
        sw.start();
        sleep(10);
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
    }

    @Test
    void stop() {
        assertThrows(IllegalStateException.class, () -> sw.stop());
        sw.start();
        sw.stop();
    }

    @Test
    void reset() throws InterruptedException {
        sw.start();
        sleep(10);
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
}