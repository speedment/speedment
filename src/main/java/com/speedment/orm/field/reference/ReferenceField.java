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
package com.speedment.orm.field.reference;

import com.speedment.orm.config.model.Column;
import com.speedment.orm.field.Field;
import com.speedment.orm.field.StandardUnaryOperator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author pemi
 * @param <ENTITY>
 * @param <V>
 */
public class ReferenceField<ENTITY, V> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final Function<ENTITY, V> getter;

    public ReferenceField(Supplier<Column> columnSupplier, Function<ENTITY, V> getter) {
        this.columnSupplier = Objects.requireNonNull(columnSupplier);
        this.getter = Objects.requireNonNull(getter);
    }

    public ReferenceUnaryPredicateBuilder<ENTITY, V> isNull() {
        return newUnary(StandardUnaryOperator.IS_NULL);
    }

    public ReferenceUnaryPredicateBuilder<ENTITY, V> isNotNull() {
        return newUnary(StandardUnaryOperator.IS_NOT_NULL);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return getFrom(entity) == null;
    }

    public V getFrom(ENTITY entity) {
        return getter.apply(entity);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    public ReferenceUnaryPredicateBuilder<ENTITY, V> newUnary(StandardUnaryOperator unaryOperator) {
        return new ReferenceUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
