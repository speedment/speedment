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
package com.speedment.internal.core.field;

import com.speedment.config.db.mapper.TypeMapper;
import com.speedment.field.FieldIdentifier;
import com.speedment.field.ReferenceField;
import com.speedment.field.methods.FieldSetter;
import com.speedment.internal.core.field.trait.FieldTraitImpl;
import com.speedment.internal.core.field.trait.ReferenceFieldTraitImpl;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.predicate.SpeedmentPredicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import static com.speedment.util.NullUtil.requireNonNulls;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ReferenceFieldImpl<ENTITY, D, V> implements ReferenceField<ENTITY, D, V> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, D, V> referenceField;

    public ReferenceFieldImpl(
            FieldIdentifier identifier,
            Getter<ENTITY, V> getter,
            Setter<ENTITY, V> setter,
            TypeMapper<D, V> typeMapper,
            boolean unique
    ) {
        requireNonNulls(identifier, getter, setter, typeMapper);
        field = new FieldTraitImpl(identifier, unique);
        referenceField = new ReferenceFieldTraitImpl<>(field, getter, setter, typeMapper);
    }

    @Override
    public FieldIdentifier getIdentifier() {
        return field.getIdentifier();
    }
    
    @Override
    public boolean isUnique() {
        return field.isUnique();
    }

    @Override
    public Setter<ENTITY, V> setter() {
        return referenceField.setter();
    }

    @Override
    public Getter<ENTITY, V> getter() {
        return referenceField.getter();
    }

    @Override
    public TypeMapper<D, V> typeMapper() {
        return referenceField.typeMapper();
    }

    @Override
    public FieldSetter<ENTITY, V> setTo(V value) {
        return referenceField.setTo(value);
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, V> isNull() {
        return referenceField.isNull();
    }

    @Override
    public SpeedmentPredicate<ENTITY, D, V> isNotNull() {
        return referenceField.isNotNull();
    }

}
