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
package com.speedment.internal.core.field.trait;

import com.speedment.field.methods.FieldSetter;
import com.speedment.internal.core.field.predicate.impl.reference.IsNotNullPredicate;
import com.speedment.internal.core.field.predicate.impl.reference.IsNullPredicate;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.internal.core.field.FieldSetterImpl;
import static java.util.Objects.requireNonNull;

/**
 * @param <ENTITY> the entity type
 * @param <V> the field value type
 * @author pemi
 */
public class ReferenceFieldTraitImpl<ENTITY, V> implements ReferenceFieldTrait<ENTITY, V> {

    private final FieldTrait field;
    private final Getter<ENTITY, V> getter;
    private final Setter<ENTITY, V> setter;

    public ReferenceFieldTraitImpl(FieldTrait field, Getter<ENTITY, V> getter, Setter<ENTITY, V> setter) {
        this.field = requireNonNull(field);
        this.getter = requireNonNull(getter);
        this.setter = requireNonNull(setter);
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
    public FieldSetter<ENTITY, V> setTo(V value) {
        return new FieldSetterImpl<>(field, setter, value);
    }

    @Override
    public SpeedmentPredicate<ENTITY, V> isNull() {
        // Must create a new object each time because users may invoke .negate()
        return new IsNullPredicate<>(field, this);
    }

    @Override
    public SpeedmentPredicate<ENTITY, V> isNotNull() {
        return new IsNotNullPredicate<>(field, this);
    }

}
