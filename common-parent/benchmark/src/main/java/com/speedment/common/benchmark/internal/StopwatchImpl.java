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
package com.speedment.common.benchmark.internal;

import com.speedment.common.benchmark.Stopwatch;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.*;

/**
 *
 * @author pemi
 */
public final class StopwatchImpl implements Stopwatch {

    private long start;
    private long stop;

    public StopwatchImpl() {
        reset();
    }

    @Override
    public long elapsed(TimeUnit timeUnit) {
        return timeUnit.convert(elapsedNanos(), NANOSECONDS);
    }

    @Override
    public long elapsedMillis() {
        return elapsedNanos() / 1_000_000L;
    }

    @Override
    public long elapsedNanos() {
        final long now = System.nanoTime();
        final long elapsedNano;

        if (isStopped()) {
            elapsedNano = stop - start;
        } else {
            if (isStarted()) {
                elapsedNano = now - start;
            } else {
                elapsedNano = 0;
            }
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
        return String.format("%,.2f %s", value, unitShortText(unit));
    }

    private static TimeUnit timeUnitFor(long nanos) {
        if (SECONDS.convert(nanos, NANOSECONDS) > 1) {
            return SECONDS;
        }
        if (MILLISECONDS.convert(nanos, NANOSECONDS) > 1) {
            return MILLISECONDS;
        }
        if (MICROSECONDS.convert(nanos, NANOSECONDS) > 1) {
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
                return "us";
            case NANOSECONDS:
                return "ns";
            default:
                throw new AssertionError();
        }
    }
}
