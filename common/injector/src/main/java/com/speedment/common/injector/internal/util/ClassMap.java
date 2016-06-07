package com.speedment.common.injector.internal.util;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 *
 * @author     Emil Forslund
 * @param <T>  the common super type of the keys
 */
public final class ClassMap<T> {
    
    private final Map<Class<? extends T>, T> inner;
    
    public static <T> ClassMap<T> of(Class<T> type) {
        return new ClassMap<>();
    }
    
    private ClassMap() {
        this.inner = new ConcurrentHashMap<>();
    }
    
    public <R extends T> R computeIfAbsent(Class<R> type, Supplier<R> supplier) {
        @SuppressWarnings("unchecked")
        final R result = (R) inner.computeIfAbsent(type, t -> supplier.get());
        return result;
    }
    
    public <R extends T> Optional<R> get(Class<R> type) {
        @SuppressWarnings("unchecked")
        final R result = (R) inner.get(type);
        return Optional.ofNullable(result);
    }
    
    public <R extends T> Optional<T> put(Class<R> type, R value) {
        return Optional.ofNullable(inner.put(type, value));
    }
}
