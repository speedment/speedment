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
package com.speedment.runtime.field.internal.expression;

import com.speedment.runtime.compute.ToEnum;
import com.speedment.runtime.field.ReferenceField;
import com.speedment.runtime.field.expression.FieldToEnum;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of {@link FieldToEnum}.
 *
 * @author Emil Forslund
 * @since  3.1.0
 */
public final class FieldToEnumImpl<ENTITY, V, E extends Enum<E>>
extends AbstractFieldMapper<ENTITY, V, E, ToEnum<ENTITY, E>, Function<V, E>>
implements FieldToEnum<ENTITY, V, E> {

    private final Class<E> enumClass;

    public FieldToEnumImpl(ReferenceField<ENTITY, ?, V> field,
                           Function<V, E> mapper,
                           Class<E> enumClass) {
        super(field, mapper);
        this.enumClass = requireNonNull(enumClass);
    }

    @Override
    public Class<E> enumClass() {
        return enumClass;
    }

    @Override
    public E apply(ENTITY entity) {
        final V value = field.get(entity);
        if (value == null) return null;
        else return mapper.apply(value);
    }
}