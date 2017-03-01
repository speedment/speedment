/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.lazy;

import java.util.function.IntSupplier;

import static java.util.Objects.requireNonNull;

/**
 * Lazy initialization class for {@link int}.
 *
 * This class is thread safe. The Supplier is guaranteed to be called exactly
 * one time following one or several calls to 
 * {@link  #getOrCompute(java.util.function.IntSupplier) } by any number of
 * threads.
 *
 * @author Per Minborg
 */
public final class LazyInt {

    private volatile int value;
    private volatile boolean initialized;

    private LazyInt() {
    }

    public int getOrCompute(IntSupplier supplier) {
        return initialized ? value : maybeCompute(supplier);
    }

    private synchronized int maybeCompute(IntSupplier supplier) {
        if (!initialized) {
            value = requireNonNull(supplier.getAsInt());
            initialized = true;
        }
        return value;
    }

    public static LazyInt create() {
        return new LazyInt();
    }
}
