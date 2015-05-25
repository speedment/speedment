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

import com.speedment.core.config.model.Column;
import com.speedment.core.field.Field;
import com.speedment.core.field.StandardBinaryOperator;
import com.speedment.core.field.StandardUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToLongFunction;

/**
 *
 * @author pemi
 * @param <ENTITY> The entity type
 */
public class LongField<ENTITY> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final ToLongFunction<ENTITY> getter;

    public LongField(Supplier<Column> columnSupplier, ToLongFunction<ENTITY> getter) {
        this.getter = getter;
        this.columnSupplier = columnSupplier;
    }

    public LongBinaryPredicateBuilder<ENTITY> equal(long value) {
        return newBinary(value, StandardBinaryOperator.EQUAL);
    }

    public LongBinaryPredicateBuilder<ENTITY> notEqual(long value) {
        return newBinary(value, StandardBinaryOperator.NOT_EQUAL);
    }

    public LongBinaryPredicateBuilder<ENTITY> lessThan(long value) {
        return newBinary(value, StandardBinaryOperator.LESS_THAN);
    }

    public LongBinaryPredicateBuilder<ENTITY> lessOrEqual(long value) {
        return newBinary(value, StandardBinaryOperator.LESS_OR_EQUAL);
    }

    public LongBinaryPredicateBuilder<ENTITY> greaterThan(long value) {
        return newBinary(value, StandardBinaryOperator.GREATER_THAN);
    }

    public LongBinaryPredicateBuilder<ENTITY> greaterOrEqual(long value) {
        return newBinary(value, StandardBinaryOperator.GREATER_OR_EQUAL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return false;
    }

    public long getFrom(ENTITY entity) {
        return getter.applyAsLong(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public LongBinaryPredicateBuilder<ENTITY> newBinary(long value, StandardBinaryOperator binaryOperator) {
        return new LongBinaryPredicateBuilder<>(this, value, binaryOperator);
    }

    public LongUnaryPredicateBuilder<ENTITY> newUnary(StandardUnaryOperator unaryOperator) {
        return new LongUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
