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
package com.speedment.runtime.compute.internal;

import com.speedment.runtime.compute.ToDouble;
import com.speedment.runtime.compute.expression.ConstantExpression;

/**
 * Default implementation of {@link ConstantExpression} used for {@code double}
 * values.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class ConstantDoubleImpl<T>
implements ToDouble<T>, ConstantExpression<T, Double> {

    private final double constant;

    public ConstantDoubleImpl(double constant) {
        this.constant = constant;
    }

    @Override
    public double applyAsDouble(T object) {
        return constant;
    }

    @Override
    public Double value() {
        return constant;
    }
}
