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
package com.speedment.core.field.ints;

import com.speedment.core.config.model.Column;
import com.speedment.core.field.Field;
import com.speedment.core.field.StandardBinaryOperator;
import com.speedment.core.field.StandardUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

/**
 *
 * @author pemi
 * @param <ENTITY> The entity field
 */
public class IntField<ENTITY> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final ToIntFunction<ENTITY> getter;

    public IntField(Supplier<Column> columnSupplier, ToIntFunction<ENTITY> getter) {
        this.getter = getter;
        this.columnSupplier = columnSupplier;
    }

    public IntBinaryPredicateBuilder<ENTITY> equal(int value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
    }

    public IntBinaryPredicateBuilder<ENTITY> notEqual(int value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
    }

    public IntBinaryPredicateBuilder<ENTITY> lessThan(int value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
    }

    public IntBinaryPredicateBuilder<ENTITY> lessOrEqual(int value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
    }

    public IntBinaryPredicateBuilder<ENTITY> greaterThan(int value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
    }

    public IntBinaryPredicateBuilder<ENTITY> greaterOrEqual(int value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public int getFrom(ENTITY entity) {
        return getter.applyAsInt(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public IntBinaryPredicateBuilder<ENTITY> newBinary(int value, StandardBinaryOperator binaryOperator) {
        return new IntBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    public IntUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new IntUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
