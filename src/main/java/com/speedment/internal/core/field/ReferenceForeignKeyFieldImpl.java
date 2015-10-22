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

import com.speedment.field.ReferenceForeignKeyField;
import com.speedment.field.methods.FieldSetter;
import com.speedment.internal.core.field.trait.FieldTraitImpl;
import com.speedment.internal.core.field.trait.ReferenceFieldTraitImpl;
import com.speedment.internal.core.field.trait.ReferenceForeignKeyFieldTraitImpl;
import com.speedment.field.methods.Finder;
import com.speedment.field.methods.Getter;
import com.speedment.field.methods.Setter;
import com.speedment.field.predicate.SpeedmentPredicate;
import java.util.function.Predicate;
import com.speedment.field.trait.FieldTrait;
import com.speedment.field.trait.ReferenceFieldTrait;
import com.speedment.field.trait.ReferenceForeignKeyFieldTrait;
import static java.util.Objects.requireNonNull;

/**
 * This class represents a Reference Field. A Reference Field is something that
 * extends {@link Object}.
 *
 * @author pemi
 * @param <ENTITY> The entity type
 * @param <V> The value type
 */
public class ReferenceForeignKeyFieldImpl<ENTITY, V, FK> implements ReferenceForeignKeyField<ENTITY, V, FK> {

    private final FieldTrait field;
    private final ReferenceFieldTrait<ENTITY, V> referenceField;
    private final ReferenceForeignKeyFieldTrait<ENTITY, FK> referenceForeignKeyField;

    public ReferenceForeignKeyFieldImpl(
        String columnName,
        Getter<ENTITY, V> getter,
        Setter<ENTITY, V> setter,
        Finder<ENTITY, FK> finder
    ) {
        field = new FieldTraitImpl(requireNonNull(columnName));
        referenceField = new ReferenceFieldTraitImpl<>(field, requireNonNull(getter), requireNonNull(setter));
        referenceForeignKeyField = new ReferenceForeignKeyFieldTraitImpl<>(requireNonNull(finder));
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
    public SpeedmentPredicate<ENTITY, V> isNull() {
        return referenceField.isNull();
    }

    @Override
    public SpeedmentPredicate<ENTITY, V> isNotNull() {
        return referenceField.isNotNull();
    }

    @Override
    public Finder<ENTITY, FK> finder() {
        return referenceForeignKeyField.finder();
    }

}
