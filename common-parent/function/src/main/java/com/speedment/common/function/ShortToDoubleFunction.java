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
package com.speedment.common.function;

/**
 * Functional interface describing a method that given a {@code short} returns
 * a {@code double}.
 *
 * @author Emil Forslund
 * @since  1.0.5
 */
@FunctionalInterface
public interface ShortToDoubleFunction {

    /**
     * Applies this function on the specified {@code short}, returning a
     * {@code double}.
     *
     * @param value  the input value
     * @return       the output
     */
    double applyAsDouble(short value);
}
