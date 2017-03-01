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

import java.util.function.DoubleSupplier;

import static java.util.Objects.requireNonNull;

/**
 * Lazy initialization class for {@link double}.
 *
 * This class is thread safe. The Supplier is guaranteed to be called exactly
 * one time following one or several calls to 
 * {@link  #getOrCompute(java.util.function.DoubleSupplier) } by any number of
 * threads.
 *
 * @author Per Minborg
 */
public final class LazyDouble {

    private volatile double value;
    private volatile boolean initialized;

    private LazyDouble() {
    }

    public double getOrCompute(DoubleSupplier supplier) {
        return initialized ? value : maybeCompute(supplier);
    }

    private synchronized double maybeCompute(DoubleSupplier supplier) {
        if (!initialized) {
            value = requireNonNull(supplier.getAsDouble());
            initialized = true;
        }
        return value;
    }

    public static LazyDouble create() {
        return new LazyDouble();
    }
}
