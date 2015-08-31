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
import com.speedment.field.ReferenceField;
import com.speedment.field.operators.StandardUnaryOperator;
import com.speedment.field.builders.UnaryPredicateBuilder;
import com.speedment.internal.core.field.builders.SetterBuilderImpl;
import com.speedment.internal.core.field.builders.UnaryPredicateBuilderImpl;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;
import com.speedment.field.builders.SetterBuilder;
import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNull;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ReferenceFieldImpl<ENTITY, V> implements ReferenceField<ENTITY, V> {

    private final String columnName;
    private final Getter<ENTITY, V> getter;
    private final Setter<ENTITY, V> setter;

    public ReferenceFieldImpl(String columnName, Getter<ENTITY, V> getter, Setter<ENTITY, V> setter) {
        this.columnName = requireNonNull(columnName);
        this.getter = requireNonNull(getter);
        this.setter = requireNonNull(setter);
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public UnaryPredicateBuilder<ENTITY> isNull() {
        return new UnaryPredicateBuilderImpl<>(this, StandardUnaryOperator.IS_NULL);
    }

    @Override
    public UnaryPredicateBuilder<ENTITY> isNotNull() {
        return new UnaryPredicateBuilderImpl<>(this, StandardUnaryOperator.IS_NOT_NULL);
    }

    @Override
    public Setter<ENTITY, V> setter() {
        return setter;
    }

    @Override
    public Getter<ENTITY, V> getter() {
        return getter;
    }

    @Override
    public SetterBuilder<ENTITY, V> set(V newValue) {
        return new SetterBuilderImpl<>(this, newValue);
    }

    @Override
    public V get(ENTITY entity) {
        requireNonNull(entity);
        return getter().apply(entity);
    }
}
