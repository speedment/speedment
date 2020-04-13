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
import com.speedment.runtime.compute.internal.ToEnumImpl;
import com.speedment.runtime.compute.internal.expression.ComposedUtil;
import com.speedment.runtime.compute.internal.expression.MapperUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.HasMap;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Expression that given an entity returns a non-null {@code enum} value. The
 * expression also knows about the enum class and can therefore not be
 * implemented as a lambda like the other expressions in this module.
 *
 * @param <T> type to extract from
 * @param <E> enum type
 *
 * @see Function
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToEnum<T, E extends Enum<E>>
extends Expression<T>,
        Function<T, E>,
        HasMap<T, UnaryOperator<E>, ToEnum<T, E>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToEnum<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param <E> enum type
     * @param lambda to convert
     * @param enumClass class of the enum
     * @return a typed {@code ToEnum<T>} using the provided
     *         {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T, E extends Enum<E>> ToEnum<T, E>
    of(Class<E> enumClass, Function<T, E> lambda) {
        return new ToEnumImpl<>(enumClass, lambda);
    }

    /**
     * Returns the {@code class} of the enum that this expression returns.
     *
     * @return  the enum class
     */
    Class<E> enumClass();

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.ENUM;
    }

    @Override
    E apply(T t);

    /**
     * Returns an alternative expression that represents the
     * {@link Enum#ordinal()} of the enum that would otherwise have been
     * returned by this expression.
     *
     * @return  the ordinal expression
     */
    default ToInt<T> asOrdinal() {
        return t -> apply(t).ordinal();
    }

    /**
     * Returns an alternative expression that represents the {@link Enum#name()}
     * of the enum that would otherwise have been returned by this expression.
     *
     * @return  the name expression
     */
    default ToString<T> asName() {
        return t -> apply(t).name();
    }

    @Override
    default ToEnum<T, E> map(UnaryOperator<E> mapper) {
        return MapperUtil.mapEnum(this, mapper);
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
    default <V> ToEnumNullable<V, E> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToEnum(casted, this);
    }
}
