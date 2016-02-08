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
package com.speedment.internal.util;

import static java.util.Objects.requireNonNull;
import java.util.function.Supplier;

/**
 * Generic lazy initialization class. The supplier must produce a non-null
 * value.
 *
 * This class is thread safe. The Supplier is guaranteed to be called exactly
 * one time following one or several calls to 
 * {@link  #getOrCompute(java.util.function.Supplier) } by any number of
 * threads.
 *
 * @author pemi
 * @param <T> the type of the lazy initialized value
 */
public final class Lazy<T> {

    private volatile T value;

    private Lazy() {
    }
    
    public T getOrCompute(Supplier<T> supplier) {
        // With this local variable, we only need to do one volatile read most of the times
        final T result = value;  
        return result == null ? maybeCompute(supplier) : result;
    }

    private synchronized T maybeCompute(Supplier<T> supplier) {
        if (value == null) {
            value = requireNonNull(supplier.get());
        }
        return value;
    }

    public static <T> Lazy<T> create() {
        return new Lazy<>();
    }
}
