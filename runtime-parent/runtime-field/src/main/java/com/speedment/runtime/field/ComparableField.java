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
import com.speedment.runtime.field.internal.ComparableFieldImpl;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A field that represents an object value that implements {@code Comparable}.
 *
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * @param <V>       the field value type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 * 
 * @see    ReferenceField
 */
public interface ComparableField<ENTITY, D, V extends Comparable<? super V>> 
extends ReferenceField<ENTITY, D, V>,
        HasComparableOperators<ENTITY, V> {

    /**
     * Creates a new {@link ComparableField} using the default implementation. 
     * 
     * @param <ENTITY>    the entity type
     * @param <D>         the database type
     * @param <V>         the field value type
     * @param identifier  the column that this field represents
     * @param getter      method reference to the getter in the entity
     * @param setter      method reference to the setter in the entity
     * @param typeMapper  the type mapper that is applied
     * @param unique      represented column only contains unique values
     * 
     * @return            the created field
     */
    static <ENTITY, D, V extends Comparable<? super V>> 
    ComparableField<ENTITY, D, V> create(
            ColumnIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, V> getter,
            ReferenceSetter<ENTITY, V> setter,
            TypeMapper<D, V> typeMapper,
            boolean unique) {
        
        return new ComparableFieldImpl<>(
            identifier, getter, setter, typeMapper, unique
        );
    }

    @Override
    ComparableField<ENTITY, D, V>  tableAlias(String tableAlias);

}