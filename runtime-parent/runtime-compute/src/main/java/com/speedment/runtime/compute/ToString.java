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
package com.speedment.runtime.compute;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.ExpressionType;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.HasCase;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMap;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Expression that given an entity returns a non-null {@code String} value. This
 * expression can be implemented using a lambda, or it can be a result of
 * another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see Function
 *
 * @author Emil Forslund
 * @since 3.1.0
 */
@FunctionalInterface
public interface ToString<T>
extends Expression<T>,
        Function<T, String>,
        HasMap<T, UnaryOperator<String>, ToString<T>>,
        HasCase<T, ToString<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    
    /**
     * Returns a typed {@code ToString<T>} using the provided {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToString<T>} using the provided {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToString<T> of(Function<T, String> lambda) {
        if (lambda instanceof ToString) {
            return (ToString<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    String apply(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.STRING;
    }

    @Override
    default ToString<T> map(UnaryOperator<String> mapper) {
        return MapperUtil.mapString(this, mapper);
    }

    @Override
    default ToString<T> toUpperCase() {
        return map(String::toUpperCase);
    }

    @Override
    default ToString<T> toLowerCase() {
        return map(String::toLowerCase);
    }

    @Override
    default long hash(T object) {
        return apply(object).hashCode();
    }

    @Override
    default int compare(T first, T second) {
        return apply(first).compareTo(apply(second));
    }

    @Override
    default <V> ToStringNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToString(casted, this);
    }
}
