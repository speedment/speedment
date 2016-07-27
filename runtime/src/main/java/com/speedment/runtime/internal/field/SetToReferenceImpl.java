/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.field;

import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.method.SetToReference;
import com.speedment.runtime.field.method.ReferenceSetter;
import static java.util.Objects.requireNonNull;

/**
 *
 * @param <ENTITY>  the entity type
 * @param <V>       the value type
 * 
 * @author  Per Minborg
 * @since   2.2.0
 */
public final class SetToReferenceImpl<ENTITY, V> implements SetToReference<ENTITY, V> {

    private final FieldTrait field;
    private final V newValue;

    public SetToReferenceImpl(FieldTrait field, V newValue) {
        this.field = requireNonNull(field);
        this.newValue = requireNonNull(newValue);
    }

    @Override
    public FieldTrait getField() {
        return field;
    }

    @Override
    public V getValue() {
        return newValue;
    }

    @Override
    public ENTITY apply(ENTITY entity) {
        requireNonNull(entity);
        
        @SuppressWarnings("unchecked")
        final ReferenceSetter<ENTITY, V> setter =
            (ReferenceSetter<ENTITY, V>) field.setter();
        
        return setter.apply(entity, newValue);
    }
}