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

import com.speedment.common.benchmark.internal.StopwatchImpl;
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

    static Stopwatch createStarted() {
        return create().start();
    }

    static Stopwatch create() {
        return new StopwatchImpl();
    }

}
