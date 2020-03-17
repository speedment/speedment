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

import java.lang.reflect.Array;
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

    private CompletableFutureUtil() {}

    @SafeVarargs
    public static <T> CompletableFuture<T> allOf(
        T defaultValue,
        BinaryOperator<T> merger, 
        CompletableFuture<T>... futures) {
        
        @SuppressWarnings("unchecked")
        final CompletableFuture<Void>[] accumulators 
            = (CompletableFuture<Void>[]) Array.newInstance(
                CompletableFuture.class, 
                futures.length
            );

        final AtomicReference<T> result = new AtomicReference<>(defaultValue);

        for (int i = 0; i < futures.length; i++) {
            final CompletableFuture<T> future = futures[i];
            accumulators[i] = future.thenAcceptAsync(r -> result.accumulateAndGet(r, merger));
        }
        
        return CompletableFuture.allOf(accumulators)
            .thenApplyAsync(v -> result.get());
    }

}
