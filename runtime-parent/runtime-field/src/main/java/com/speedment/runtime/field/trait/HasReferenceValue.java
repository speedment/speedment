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
package com.speedment.runtime.field.trait;

import com.speedment.runtime.field.Field;
import com.speedment.runtime.field.internal.method.SetToReferenceImpl;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.method.SetToReference;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A representation of an Entity field that is a reference type (eg 
 * {@code Integer} and not {@code int}).
 *
 * @param <ENTITY>  the entity type
 * @param <D>       the database value type
 * @param <V>       the field value type
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */
public interface HasReferenceValue<ENTITY, D, V> extends Field<ENTITY> {

    @Override
    ReferenceSetter<ENTITY, V> setter();

    @Override
    ReferenceGetter<ENTITY, V> getter();

    @Override
    TypeMapper<D, V> typeMapper();

    /**
     * Gets the value form the Entity field.
     *
     * @param e entity
     * @return the field value
     */
    default V get(ENTITY e) {
        return getter().apply(e);
    }

    /**
     * Sets the value in the given Entity
     *
     * @param e entity
     * @param value to set
     * @return the entity itself
     */
    default ENTITY set(ENTITY e, V value) {
        setter().accept(e, value);
        return e;
    }

    /**
     * Creates and returns a SetToReference with a given value.
     *
     * @param value  to set
     * @return       a SetToReference with a given value
     */
    default SetToReference<ENTITY, D, V> setTo(V value) {
        return new SetToReferenceImpl<>(this, value);
    }
}