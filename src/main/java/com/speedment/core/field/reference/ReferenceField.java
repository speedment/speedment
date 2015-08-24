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
package com.speedment.core.field.reference;

import com.speedment.core.config.model.Column;
import com.speedment.core.field.Field;
import com.speedment.core.field.StandardUnaryOperator;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ReferenceField<ENTITY, V> implements Field<ENTITY> {

    private final Supplier<Column> columnSupplier;
    private final Getter<ENTITY, V> getter;
    private final Setter<ENTITY, V> setter;

    public ReferenceField(Supplier<Column> columnSupplier, Getter<ENTITY, V> getter, Setter<ENTITY, V> setter) {
        this.columnSupplier = requireNonNull(columnSupplier);
        this.getter         = requireNonNull(getter);
        this.setter         = requireNonNull(setter);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is {@code null}.
     *
     * @return  a Predicate that will evaluate to {@code true}, if and only if
     *          this Field is {@code null}
     */
    public ReferenceUnaryPredicateBuilder<ENTITY, V> isNull() {
        return newUnary(StandardUnaryOperator.IS_NULL);
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not</em> {@code null}.
     *
     * @return  a Predicate that will evaluate to {@code true}, if and only if
     *          this Field is <em>not</em> {@code null}
     */
    public ReferenceUnaryPredicateBuilder<ENTITY, V> isNotNull() {
        return newUnary(StandardUnaryOperator.IS_NOT_NULL);
    }

    public ReferenceFunctionBuilder<ENTITY, V> set(V newValue) {
        return new ReferenceFunctionBuilder<>(this, newValue);
    }

    @Override
    public boolean isNullIn(ENTITY entity) {
        return getFrom(entity) == null;
    }

    public V getFrom(ENTITY entity) {
        return getter.apply(entity);
    }

    public ENTITY setIn(ENTITY entity, V value) {
        return setter.apply(entity, value);
    }

    @Override
    public Column getColumn() {
        return columnSupplier.get();
    }

    protected ReferenceUnaryPredicateBuilder<ENTITY, V> newUnary(StandardUnaryOperator unaryOperator) {
        return new ReferenceUnaryPredicateBuilder<>(this, unaryOperator);
    }

}
