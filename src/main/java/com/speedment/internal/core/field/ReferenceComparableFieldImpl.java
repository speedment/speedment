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
package com.speedment.internal.core.field;

import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.operators.StandardComparableOperator;
import com.speedment.field.ReferenceComparableField;
import com.speedment.internal.core.field.builders.ComparablePredicateBuilderImpl;
import java.util.Comparator;
import com.speedment.field.builders.ComparablePredicateBuilder;

/**
 * This class represents a Comparable Reference Field. A Reference Field is
 * something that extends {@link Comparable}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ReferenceComparableFieldImpl<ENTITY, V extends Comparable<? super V>> 
    extends ReferenceFieldImpl<ENTITY, V> 
    implements ReferenceComparableField<ENTITY, V> {

    public ReferenceComparableFieldImpl(String columnName, Getter<ENTITY, V> getter, Setter<ENTITY, V> setter) {
        super(columnName, getter, setter);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value
     */
    @Override
    public ComparablePredicateBuilder<ENTITY, V> equal(V value) {
        return newBinary(value, StandardComparableOperator.EQUAL);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value
     */
    @Override
    public ComparablePredicateBuilder<ENTITY, V> notEqual(V value) {
        return newBinary(value, StandardComparableOperator.NOT_EQUAL);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>less than</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>less than</em> the given value
     */
    @Override
    public ComparablePredicateBuilder<ENTITY, V> lessThan(V value) {
        return newBinary(value, StandardComparableOperator.LESS_THAN);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>less than or equal</em>
     * to the given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>less than or equal</em> to the given value
     */
    @Override
    public ComparablePredicateBuilder<ENTITY, V> lessOrEqual(V value) {
        return newBinary(value, StandardComparableOperator.LESS_OR_EQUAL);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>greater than</em>
     * the given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>greater than</em> the given value
     */
    @Override
    public ComparablePredicateBuilder<ENTITY, V> greaterThan(V value) {
        return newBinary(value, StandardComparableOperator.GREATER_THAN);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>greater than or equal</em>
     * to the given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>greater than or equal</em> to the given value
     */
    @Override
    public ComparablePredicateBuilder<ENTITY, V> greaterOrEqual(V value) {
        return newBinary(value, StandardComparableOperator.GREATER_OR_EQUAL);
    }

    protected ComparablePredicateBuilder<ENTITY, V> newBinary(V value, StandardComparableOperator binaryOperator) {
        return new ComparablePredicateBuilderImpl<>(this, value, binaryOperator, Comparator.naturalOrder());
    }
}