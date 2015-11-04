package com.speedment.internal.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * Generic lazy initialization class.
 *
 * This class is not thread safe.
 *
 * @author pemi
 * @param <T> the type of the lazy initialized value
 */
public class Lazy<T> {

    private T value;
    private volatile boolean initialized;

    public T getOrCompute(Supplier<T> supplier) {
        if (!initialized) {
            synchronized (this) { // Make sure we are alone
                if (!initialized) { // Make sure that no one else got here before we did
                    initialized = true;
                }
                return value = supplier.get();
            }
        }
        return value;
    }

}
