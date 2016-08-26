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
package com.speedment.common.lazy;

import java.util.function.Supplier;

/**
 * Generic lazy initialization interface.
 *
 * @author pemi
 * @param <T> the type of the lazy initialized value
 */
@FunctionalInterface
public interface Lazy<T> {

    /**
     * Returns the value of the Lazy instance. If the Lazy instance is not
     * initialized, the supplier will be called before the value is returned.
     * The method guarantees that the supplier is called exactly one time
     * following one or several calls to 
     * {@link  #getOrCompute(java.util.function.Supplier) } by any number of
     * threads. Thus, the method is thread safe.
     *
     * @param supplier to use for supplying a new value. Must never return null.
     * @return the value of the Lazy instance
     * @throws NullPointerException if the supplier returns a {@code null} value
     * or if the supplier itself is null.
     */
    T getOrCompute(Supplier<T> supplier);

}
