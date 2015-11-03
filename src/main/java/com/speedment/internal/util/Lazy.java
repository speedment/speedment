package com.speedment.internal.util;

import java.util.concurrent.ConcurrentMap;
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
    private boolean initialized;

    ConcurrentMap m;

    public T getOrCompute(Supplier<T> supplier) {
        if (initialized) {
            return value;
        } else {
            initialized = true;
            return value = supplier.get();
        }

    }

}
