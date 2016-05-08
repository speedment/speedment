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

import com.speedment.runtime.field.method.FieldSetter;
import com.speedment.runtime.field.method.Setter;
import com.speedment.runtime.field.trait.FieldTrait;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 * @param <ENTITY> entity type
 * @param <V> value type
 */
public final class FieldSetterImpl<ENTITY, V> implements FieldSetter<ENTITY, V> {

    private final FieldTrait field;
    private final Setter<ENTITY, V> setter;
    private final V newValue;

    public FieldSetterImpl(FieldTrait field, Setter<ENTITY, V> setter, V newValue) {
        this.field = requireNonNull(field);
        this.setter = setter;
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
        return setter.apply(entity, newValue);
    }

}
