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
package com.speedment.runtime.compute.expression;

/**
 * Specialized {@link Expression} that always returns the same value, regardless
 * of the input.
 * <p>
 * Equality is determined by looking at the {@link #value()}.
 *
 * @param <T>  the input entity type
 * @param <V>  the resulting type
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ConstantExpression<T, V> extends Expression<T> {

    /**
     * Returns the constant value of this expression. The constant value is the
     * result of the expression, regardless of what the input it.
     *
     * @return  the constant value
     */
    V value();

}
