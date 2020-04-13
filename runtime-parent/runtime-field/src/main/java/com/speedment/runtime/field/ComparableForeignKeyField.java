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
package com.speedment.runtime.field;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.internal.ComparableForeignKeyFieldImpl;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasFinder;
import com.speedment.runtime.field.trait.HasNullableFinder;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A field that represents an object value that implements {@code Comparable}
 * and that references another field using a foreign key.
 * 
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * @param <V>       the field value type
 * @param <FK>      the foreign entity type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 * 
 * @see  ComparableField
 * @see  HasFinder
 */
public interface ComparableForeignKeyField<ENTITY, D, V extends Comparable<? super V>, FK> 
extends ComparableField<ENTITY, D, V>,
        HasNullableFinder<ENTITY, FK> {

    /**
     * Creates a new {@link ComparableField} using the default implementation. 
     * 
     * @param <ENTITY>    entity type
     * @param <D>         database type
     * @param <V>         field value type
     * @param <FK>        foreign entity type
     * @param identifier  column that this field represents
     * @param getter      method reference to the getter in the entity
     * @param setter      method reference to the setter in the entity
     * @param referenced  field in the foreign entity that is referenced
     * @param typeMapper  type mapper that is applied
     * @param unique      represented column only contains unique values
     * 
     * @return            the created field
     */
    static <ENTITY, D, V extends Comparable<? super V>, FK> 
    ComparableForeignKeyField<ENTITY, D, V, FK> create(
            ColumnIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, V> getter,
            ReferenceSetter<ENTITY, V> setter,
            HasComparableOperators<FK, V> referenced,
            TypeMapper<D, V> typeMapper,
            boolean unique) {
        
        return new ComparableForeignKeyFieldImpl<>(
            identifier, getter, setter, referenced, typeMapper, unique
        );
    }

    @Override
    ComparableForeignKeyField<ENTITY, D, V, FK> tableAlias(String tableAlias);

}