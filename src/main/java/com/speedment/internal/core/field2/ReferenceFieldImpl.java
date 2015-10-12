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
package com.speedment.internal.core.field2;

import com.speedment.field2.ReferenceField;
import com.speedment.field2.methods.FieldSetter;
import com.speedment.internal.core.field2.trait.FieldTraitImpl;
import com.speedment.internal.core.field2.trait.ReferenceFieldTraitImpl;
import com.speedment.field2.methods.Getter;
import com.speedment.field2.methods.Setter;
import java.util.function.Predicate;
import com.speedment.field2.trait.FieldTrait;
import com.speedment.field2.trait.ReferenceFieldTrait;
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

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, V> referenceField;

    public ReferenceFieldImpl(
        String columnName,
        Getter<ENTITY, V> getter,
        Setter<ENTITY, V> setter
    ) {
        field = new FieldTraitImpl(requireNonNull(columnName));
        referenceField = new ReferenceFieldTraitImpl<>(field, requireNonNull(getter), requireNonNull(setter));
    }

    @Override
    public String getColumnName() {
        return field.getColumnName();
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
    public FieldSetter<ENTITY, V> setTo(V value) {
        return referenceField.setTo(value);
    }

    @Override
    public Predicate<ENTITY> isNull() {
        return referenceField.isNull();
    }

    @Override
    public Predicate<ENTITY> isNotNull() {
        return referenceField.isNotNull();
    }

}
