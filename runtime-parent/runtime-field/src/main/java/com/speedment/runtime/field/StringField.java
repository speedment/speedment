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


import com.speedment.runtime.compute.ToStringNullable;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.internal.StringFieldImpl;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.predicate.FieldIsNotNullPredicate;
import com.speedment.runtime.field.predicate.FieldIsNullPredicate;
import com.speedment.runtime.field.trait.HasStringOperators;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A field that represents a string column.
 * 
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 * 
 * @see  ComparableField
 * @see  HasStringOperators
 */
public interface StringField<ENTITY, D> extends
    ComparableField<ENTITY, D, String>, 
    HasStringOperators<ENTITY>,
    ToStringNullable<ENTITY> {

    /**
     * Creates a new {@link StringField} using the default implementation. 
     * 
     * @param <ENTITY>    the entity type
     * @param <D>         the database type
     * @param identifier  the column that this field represents
     * @param getter      method reference to the getter in the entity
     * @param setter      method reference to the setter in the entity
     * @param typeMapper  the type mapper that is applied
     * @param unique      represented column only contains unique values
     * 
     * @return            the created field
     */
    static <ENTITY, D> StringField<ENTITY, D> create(
            ColumnIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, String> getter,
            ReferenceSetter<ENTITY, String> setter,
            TypeMapper<D, String> typeMapper,
            boolean unique) {
        
        return new StringFieldImpl<>(
            identifier, getter, setter, typeMapper, unique
        );
    }

    @Override
    StringField<ENTITY, D> tableAlias(String tableAlias);

    @Override
    FieldIsNullPredicate<ENTITY, String> isNull();

    @Override
    default FieldIsNotNullPredicate<ENTITY, String> isNotNull() {
        return isNull().negate();
    }

    @Override
    default String apply(ENTITY object) {
        return get(object);
    }
}