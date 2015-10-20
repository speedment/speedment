package com.speedment.internal.util.testing;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author pemi
 */
public interface Stopwatch {

    /**
     * Returns the number of timeUnits elapsed since this Stopwatch was started.
     * The result is returned using the selected time unit. The result will be
     * rounded down. If the Stopwatch was not started, -1 is returned.
     *
     * @param timeUnit to use for returned result
     * @return the number of timeUnits elapsed since this Stopwatch was started.
     */
    long elapsed(TimeUnit timeUnit);

    /**
     * Returns the number of milliseconds elapsed since this Stopwatch was
     * started. The result will be rounded down. If the Stopwatch was not
     * started, -1 is returned.
     *
     * @return the number of milliseconds elapsed since this Stopwatch was
     * started.
     */
    long elapsedMillis();

    /**
     * Returns the number of nanoseconds elapsed since this Stopwatch was
     * started. The result will be rounded down. If the Stopwatch was not
     * started, -1 is returned.
     *
     * @return the number of nanoseconds elapsed since this Stopwatch was
     * started.
     */
    long elapsedNanos();

    /**
     * Returns if this Stopwatch is started.
     *
     * @return if this Stopwatch is started
     */
    boolean isStarted();

    /**
     * Returns if this Stopwatch is stopped.
     *
     * @return if this Stopwatch is stopped
     */
    boolean isStopped();

    /**
     * Starts this Stopwatch.
     *
     * @return this Stopwatch
     */
    Stopwatch start();

    /**
     * Stops this Stopwatch.
     *
     * @return this Stopwatch
     */
    Stopwatch stop();

    /**
     * Resets this Stopwatch as it was never started.
     *
     * @return this Stopwatch
     */
    Stopwatch reset();

    default Stopwatch createStarted() {
        return create().start();
    }

    default Stopwatch create() {
        return new StopwatchImpl();
    }

}
