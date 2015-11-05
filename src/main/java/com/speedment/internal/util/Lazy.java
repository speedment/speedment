/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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

import java.util.concurrent.atomic.AtomicBoolean;
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
    private final AtomicBoolean initialized;

    public Lazy() {
        this.initialized = new AtomicBoolean();
    }

    public T getOrCompute(Supplier<T> supplier) {
        if (initialized.compareAndSet(false, true)) {
            value = supplier.get();
        }
        return value;
    }

}
