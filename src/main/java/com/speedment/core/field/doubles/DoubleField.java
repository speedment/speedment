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
import java.util.function.ToDoubleFunction;

/**
 *
 * @author pemi
 * @param <ENTITY> the entity type
 */
public class DoubleField<ENTITY> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final ToDoubleFunction<ENTITY> getter;

    public DoubleField(Supplier<Column> columnSupplier, ToDoubleFunction<ENTITY> getter) {
        this.getter = getter;
        this.columnSupplier = columnSupplier;
    }

    public DoubleBinaryPredicateBuilder<ENTITY> equal(double value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> notEqual(double value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> lessThan(double value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> lessOrEqual(double value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> greaterThan(double value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
    }

    public DoubleBinaryPredicateBuilder<ENTITY> greaterOrEqual(double value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public double getFrom(ENTITY entity) {
        return getter.applyAsDouble(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public DoubleBinaryPredicateBuilder<ENTITY> newBinary(double value, StandardBinaryOperator binaryOperator) {
        return new DoubleBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    public DoubleUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new DoubleUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
