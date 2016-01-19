package com.speedment.internal.util;

import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 * String lazy initialization class. The supplier must produce a non-null value.
 *
 * This class is thread safe. The Supplier is guaranteed to be called exactly
 * one time following one or several calls to 
 * {@link  #getOrCompute(java.util.function.Supplier) } by any number of
 * threads.
 *
 * @author pemi
 */
public final class LazyString {

    private volatile String value;

    private LazyString() {
    }

    public String getOrCompute(Supplier<String> supplier) {
        // With this local variable, we only need to do one volatile read most of the times
        final String result = value;
        return result == null ? maybeCompute(supplier) : result;
    }

    private synchronized String maybeCompute(Supplier<String> supplier) {
        if (value == null) {
            value = requireNonNull(supplier.get());
        }
        return value;
    }

    public static LazyString create() {
        return new LazyString();
    }
}
