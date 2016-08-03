package com.speedment.tool.internal.util;

import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;

/**
 *
 * @author Simon Jonasson
 * @author Emil Forslund
 * 
 * @since 3.0.0
 */
public final class CompletableFutureUtil {
    
    
    public static <T> CompletableFuture<T> allOf(
        T defaultValue,
        BinaryOperator<T> merger, 
        CompletableFuture<T>... futures) {
        
        @SuppressWarnings("unchecked")
        final CompletableFuture<Void>[] accumulators 
            = (CompletableFuture<Void>[]) new CompletableFuture[futures.length];
        
        final AtomicReference<T> result = new AtomicReference<>(defaultValue);

        for (int i = 0; i < futures.length; i++) {
            final CompletableFuture<T> future = futures[i];
            accumulators[i] = future.thenAcceptAsync(r -> result.accumulateAndGet(r, merger));
        }
        
        return CompletableFuture.allOf(accumulators)
            .thenApplyAsync(v -> result.get());
    }
    
    /**
     * This should never be called.
     */
    private CompletableFutureUtil() {
        instanceNotAllowed(getClass());
    }
}
