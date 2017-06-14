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
package com.speedment.common.lazy.specialized;


import com.speedment.common.lazy.Lazy;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Specialized Lazy initialization class for Number types.
 *
 * @author Per Minborg
 */
public final class LazyNumber implements Lazy<Number> {

    private volatile Number value;

    private LazyNumber() {
    }
    
    @Override
    public Number getOrCompute(Supplier<Number> supplier) {
        // With this local variable, we only need to do one volatile read
        final Number result = value;
        return result == null ? maybeCompute(supplier) : result;
    }

    private synchronized Number maybeCompute(Supplier<Number> supplier) {
        if (value == null) {
            value = requireNonNull(supplier.get());
        }
        return value;
    }

    public static LazyNumber create() {
        return new LazyNumber();
    }
}
