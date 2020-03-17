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
import com.speedment.runtime.compute.internal.expression.OrElseGetUtil;
import com.speedment.runtime.compute.internal.expression.OrElseThrowUtil;
import com.speedment.runtime.compute.internal.expression.OrElseUtil;
import com.speedment.runtime.compute.trait.HasCompare;
import com.speedment.runtime.compute.trait.HasCompose;
import com.speedment.runtime.compute.trait.HasHash;
import com.speedment.runtime.compute.trait.ToNullable;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Expression that given an entity returns a nullable {@code BigDecimal} value.
 * This expression can be implemented using a lambda, or it can be a result of
 * another operation. It has additional methods for operating on it.
 *
 * @param <T> type to extract from
 *
 * @see Function
 *
 * @author Per Minborg
 * @since  3.1.0
 */
@FunctionalInterface
public interface ToBigDecimalNullable<T>
extends Expression<T>,
        ToNullable<T, BigDecimal, ToBigDecimal<T>>,
        HasHash<T>,
        HasCompare<T>,
        HasCompose<T> {

    /**
     * Returns a typed {@code ToBigDecimalNullable<T>} using the provided
     * {@code lambda}.
     *
     * @param <T> type to extract from
     * @param lambda to convert
     * @return a typed {@code ToBigDecimalNullable<T>} using the provided
     * {@code lambda}
     *
     * @throws NullPointerException if the provided {@code lambda} is
     * {@code null}
     */
    static <T> ToBigDecimalNullable<T> of(Function<T, BigDecimal> lambda) {
        if (lambda instanceof ToBigDecimalNullable) {
            return (ToBigDecimalNullable<T>) lambda;
        } else {
            return lambda::apply;
        }
    }
    
    @Override
    BigDecimal apply(T object);

    @Override
    default ExpressionType expressionType() {
        return ExpressionType.BIG_DECIMAL_NULLABLE;
    }

    @Override
    default ToBigDecimal<T> orThrow() {
        return OrElseThrowUtil.bigDecimalOrElseThrow(this);
    }

    @Override
    default ToBigDecimal<T> orElseGet(ToBigDecimal<T> getter) {
        return OrElseGetUtil.bigDecimalOrElseGet(this, getter);
    }

    @Override
    default ToBigDecimal<T> orElse(BigDecimal value) {
        return OrElseUtil.bigDecimalOrElse(this, value);
    }

    @Override
    default long hash(T object) {
        return isNull(object) ? 0 : apply(object).hashCode();
    }

    @Override
    default int compare(T first, T second) {
        if (isNull(first)) {
            return isNull(second) ? 0 : 1;
        } else if (isNull(second)) {
            return -1;
        } else {
            return apply(first).compareTo(apply(second));
        }
    }

    @Override
    default <V> ToBigDecimalNullable<V> compose(Function<? super V, ? extends T> before) {
        @SuppressWarnings("unchecked")
        final Function<V, T> casted = (Function<V, T>) before;
        return ComposedUtil.composeToBigDecimalNullable(casted, this);
    }
}
