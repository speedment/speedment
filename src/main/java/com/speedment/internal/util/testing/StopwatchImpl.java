package com.speedment.internal.util.testing;

import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 *
 * @author pemi
 */
public final class StopwatchImpl implements Stopwatch {

    private long start;
    private long stop;

    StopwatchImpl() {
        reset();
    }

    @Override
    public long elapsed(TimeUnit timeUnit) {
        return timeUnit.convert(elapsedNanos(), NANOSECONDS);
    }

    @Override
    public long elapsedMillis() {
        return elapsedNanos() / 1_000_000l;
    }

    @Override
    public long elapsedNanos() {
        final long now = System.nanoTime();
        final long elapsedNano;
        if (isStopped()) {
            elapsedNano = stop - start;
        } else {
            elapsedNano = now - start;
        }
        return elapsedNano;
    }

    @Override
    public boolean isStarted() {
        return start > 0;
    }

    @Override
    public boolean isStopped() {
        return stop > 0;
    }

    @Override
    public Stopwatch start() {
        if (isStarted()) {
            throw new IllegalStateException(Stopwatch.class.getName() + " already started.");
        }
        start = System.nanoTime();
        return this;
    }

    @Override
    public Stopwatch stop() {
        long now = System.nanoTime();
        if (isStopped()) {
            throw new IllegalStateException(Stopwatch.class.getName() + " already stopped.");
        }
        if (!isStarted()) {
            throw new IllegalStateException(Stopwatch.class.getName() + " not started.");
        }
        stop = now;
        return this;
    }

    @Override
    public Stopwatch reset() {
        start = -1;
        stop = -1;
        return this;
    }

    @Override
    public String toString() {
        final long nanos = elapsedNanos();
        final TimeUnit unit = timeUnitFor(nanos);
        final double value = (double) nanos / NANOSECONDS.convert(1, unit);
        return String.format("%.4g %s", value, unitShortText(unit));
    }

    private static TimeUnit timeUnitFor(long nanos) {
        if (SECONDS.convert(nanos, NANOSECONDS) > 10) {
            return SECONDS;
        }
        if (MILLISECONDS.convert(nanos, NANOSECONDS) > 10) {
            return MILLISECONDS;
        }
        if (MICROSECONDS.convert(nanos, NANOSECONDS) > 10) {
            return MICROSECONDS;
        }
        return NANOSECONDS;
    }

    private static String unitShortText(TimeUnit unit) {
        switch (unit) {
            case SECONDS:
                return "s";
            case MILLISECONDS:
                return "ms";
            case MICROSECONDS:
                return "μs";
            case NANOSECONDS:
                return "ns";
            default:
                throw new AssertionError();
        }
    }
}
