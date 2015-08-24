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
package com.speedment.core.field.doubles;

import com.speedment.core.config.model.Column;
import com.speedment.core.field.Field;
import com.speedment.core.field.StandardBinaryOperator;
import com.speedment.core.field.StandardUnaryOperator;

import java.util.function.Supplier;

/**
 * This class represents a {@code double} Field.
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class DoubleField<ENTITY> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final DoubleGetter<ENTITY> getter;
    private final DoubleSetter<ENTITY> setter;

    public DoubleField(Supplier<Column> columnSupplier, DoubleGetter<ENTITY> getter, DoubleSetter<ENTITY> setter) {
        this.getter         = getter;
        this.setter         = setter;
        this.columnSupplier = columnSupplier;
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
    public DoubleBinaryPredicateBuilder<ENTITY> equal(double value) {
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
    public DoubleBinaryPredicateBuilder<ENTITY> notEqual(double value) {
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
    public DoubleBinaryPredicateBuilder<ENTITY> lessThan(double value) {
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
    public DoubleBinaryPredicateBuilder<ENTITY> lessOrEqual(double value) {
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
    public DoubleBinaryPredicateBuilder<ENTITY> greaterThan(double value) {
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
    public DoubleBinaryPredicateBuilder<ENTITY> greaterOrEqual(double value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    public DoubleFunctionBuilder<ENTITY> set(double value) {
        return new DoubleFunctionBuilder<>(this, value);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public double getFrom(ENTITY entity) {
        return getter.applyAsDouble(entity);
    }

    public ENTITY setIn(ENTITY entity, double value) {
        return setter.applyAsDouble(entity, value);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    protected DoubleBinaryPredicateBuilder<ENTITY> newBinary(double value, StandardBinaryOperator binaryOperator) {
        return new DoubleBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    protected DoubleUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new DoubleUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
