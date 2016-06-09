/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
