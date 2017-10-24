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
