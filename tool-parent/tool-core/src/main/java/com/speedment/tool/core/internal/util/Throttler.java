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
package com.speedment.tool.core.internal.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Throttles requests so that they are only invoked at a maximum frequency. If
 * more calls are made, others will be dropped.
 * <p>
 * This class is concurrent.
 *
 * @author Emil Forslund
 * @since  3.0.16
 */
public final class Throttler {

    /**
     * Returns a {@link Throttler} that never executes the same request more
     * than once every {@code millis}.
     *
     * @param millis  minimum frequency in milliseconds
     * @return        the created throttler
     */
    public static Throttler limitToOnceEvery(long millis) {
        return new Throttler(millis);
    }

    private final ConcurrentHashMap<String, AtomicLong> timers;
    private final long millis;

    private Throttler(long millis) {
        this.millis   = millis;
        this.timers = new ConcurrentHashMap<>();
    }

    /**
     * Attempts to invoke the specified {@code runnable} if no other action with
     * the same specifier has been called recently. Otherwise, the method will
     * return with no effect.
     *
     * @param action    the action specifier
     * @param runnable  the callable action to invoke
     */
    public void call(String action, Runnable runnable) {
        final long now = now();
        final AtomicLong timer = timers.computeIfAbsent(action, a -> new AtomicLong(0));
        if (now == timer.updateAndGet(
            lastCall -> lastCall + millis < now
                ? now : lastCall
        )) runnable.run();
    }

    private static long now() {
        return System.currentTimeMillis();
    }
}
