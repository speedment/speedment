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
package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.expression.Expression;
import com.speedment.runtime.compute.expression.predicate.IsNotNull;
import com.speedment.runtime.compute.expression.predicate.IsNull;
import com.speedment.runtime.compute.internal.predicate.IsNotNullImpl;
import com.speedment.runtime.compute.internal.predicate.IsNullImpl;

import java.util.function.Function;

/**
 * Trait for expressions that result in a nullable value. Those expressions have
 * additional methods for checking for {@code null}-values.
 *
 * @param <T>  type to extract from
 * @param <R>  return type
 * @param <NON_NULLABLE>  the expression type obtained if the nullability of
 *                        this expression is handled
 * 
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface ToNullable<T, R, NON_NULLABLE extends Expression<T>>
extends Function<T, R>, Expression<T> {

    /**
     * Returns a {@code Predicate} that is {@code true} for any values that
     * would make this expression return {@code null} and {@code false} for any
     * values that would make this expression return a result.
     *
     * @return  a predicate that tests if this expression would return
     *          {@code null}
     */
    default IsNull<T, R> isNull() {
        return new IsNullImpl<>(this);
    }

    /**
     * Returns a {@code Predicate} that is {@code false} for any values that
     * would make this expression return {@code null} and {@code true} for any
     * values that would make this expression return a result.
     *
     * @return  a predicate that tests if this expression would return something
     *          other than {@code null}
     */
    default IsNotNull<T, R> isNotNull() {
        return new IsNotNullImpl<>(this);
    }

    /**
     * Returns {@code true} if the specified object would cause this methods
     * {@link #apply(Object)}-method to return {@code null}, and otherwise
     * {@code false}.
     *
     * @param object  the incoming entity to test on
     * @return        {@code true} if the expression would return {@code null},
     *                else {@code false}
     */
    default boolean isNull(T object) {
        return apply(object) == null;
    }

    /**
     * Returns {@code false} if the specified object would cause this methods
     * {@link #apply(Object)}-method to return {@code null}, and otherwise
     * {@code true}.
     *
     * @param object  the incoming entity to test on
     * @return        {@code false} if the expression would return {@code null},
     *                else {@code true}
     */
    default boolean isNotNull(T object) {
        return apply(object) != null;
    }

    /**
     * Returns an equivalent expression as this, except that it will throw a
     * {@code NullPointerException} if given an argument that would cause this
     * expression to return {@code null}.
     *
     * @return the expression if present
     */
    NON_NULLABLE orThrow();

    /**
     * Returns an equivalent expression as this, except that it will return
     * the {@code getter} expression if given an argument that would cause this
     * expression to return {@code null}.
     *
     * @param getter to apply if null
     *
     * @return the expression if present, otherwise return {@code getter}
     */
    NON_NULLABLE orElseGet(NON_NULLABLE getter);

    /**
     * Returns an equivalent expression as this, except that it will return
     * the {@code value} if given an argument that would cause this
     * expression to return {@code null}.
     *
     * @param value value to use if null
     *
     * @return the expression if present, otherwise return {@code value}
     */
    NON_NULLABLE orElse(R value);

}