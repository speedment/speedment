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
package com.speedment.core.field.longs;

import com.speedment.core.field.Field;
import com.speedment.core.field.StandardBinaryOperator;
import com.speedment.core.field.StandardUnaryOperator;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a {@code long} Field.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 */
public class LongField<ENTITY> implements Field<ENTITY> {

    private final String columnName;
    private final LongGetter<ENTITY> getter;
    private final LongSetter<ENTITY> setter;

    public LongField(String columnName, LongGetter<ENTITY> getter, LongSetter<ENTITY> setter) {
        this.getter     = requireNonNull(getter);
        this.setter     = requireNonNull(setter);
        this.columnName = columnName;
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
    public LongBinaryPredicateBuilder<ENTITY> equal(long value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
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
    public LongBinaryPredicateBuilder<ENTITY> notEqual(long value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
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
    public LongBinaryPredicateBuilder<ENTITY> lessThan(long value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
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
    public LongBinaryPredicateBuilder<ENTITY> lessOrEqual(long value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
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
    public LongBinaryPredicateBuilder<ENTITY> greaterThan(long value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
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
    public LongBinaryPredicateBuilder<ENTITY> greaterOrEqual(long value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    public LongFunctionBuilder<ENTITY> set(long value) {
        return new LongFunctionBuilder<>(this, value);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public long getFrom(ENTITY entity) {
        return getter.applyAsLong(entity);
    }

    public ENTITY setIn(ENTITY entity, long value) {
        return setter.applyAsLong(entity, value);
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    protected LongBinaryPredicateBuilder<ENTITY> newBinary(long value, StandardBinaryOperator binaryOperator) {
        return new LongBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    protected LongUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new LongUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
