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
package com.speedment.runtime.compute.expression.orelse;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.NonNullableExpression;

/**
 * Specialization of {@link NonNullableExpression} that has a
 * {@link #defaultValueGetter()} method.
 *
 * @param <T>        the input entity type
 * @param <INNER>    the wrapped nullable expression
 * @param <DEFAULT>  the getter expression for the default value
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface OrElseGetExpression
    <T, INNER extends Expression<T>, DEFAULT extends Expression<T>>
extends NonNullableExpression<T, INNER> {

    /**
     * Returns the default value used when {@link #innerNullable()} would
     * have returned {@code null}.
     *
     * @return  the default value
     */
    DEFAULT defaultValueGetter();

    @Override
    default NullStrategy nullStrategy() {
        return NullStrategy.APPLY_DEFAULT_METHOD;
    }
}
