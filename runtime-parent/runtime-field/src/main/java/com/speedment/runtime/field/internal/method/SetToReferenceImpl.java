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
package com.speedment.runtime.field.internal.method;

import com.speedment.runtime.field.method.SetToReference;
import com.speedment.runtime.field.trait.HasReferenceValue;

import static java.util.Objects.requireNonNull;

/**
 * A {@code set} operation that will apply a value {@link #getValue()} 
 * to the field {@link #getField()} of any instance passed to it.
 * <p>
 * This particular implementation is generic so that any non-primitive
 * value type can be used.
 * 
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class SetToReferenceImpl<ENTITY, D, V> implements SetToReference<ENTITY, D, V> {

    private final HasReferenceValue<ENTITY, D, V> field;
    private final V newValue;

    public SetToReferenceImpl(HasReferenceValue<ENTITY, D, V> field, V newValue) {
        this.field = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }

    @Override
    public HasReferenceValue<ENTITY, D, V> getField() {
        return field;
    }

    @Override
    public V getValue() {
        return newValue;
    }

    @Override
    public ENTITY apply(ENTITY entity) {
        requireNonNull(entity);
        field.setter().accept(entity, newValue);
        return entity;
    }
}