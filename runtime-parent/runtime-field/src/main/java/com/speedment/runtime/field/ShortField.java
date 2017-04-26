/**
 * 
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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

import com.speedment.common.annotation.GeneratedCode;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.internal.ShortFieldImpl;
import com.speedment.runtime.field.method.ShortGetter;
import com.speedment.runtime.field.method.ShortSetter;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasShortValue;
import com.speedment.runtime.typemapper.TypeMapper;

/**
 * A field that represents a primitive {@code short} value.
 * 
 * @param <ENTITY> entity type
 * @param <D>      database type
 * 
 * @author Emil Forslund
 * @since  3.0.0
 * 
 * @see ReferenceField
 */
@GeneratedCode(value = "Speedment")
public interface ShortField<ENTITY, D> extends Field<ENTITY>, HasShortValue<ENTITY, D>, HasComparableOperators<ENTITY, Short> {
    
    /**
     * Creates a new {@link ShortField} using the default implementation.
     * 
     * @param <ENTITY>   entity type
     * @param <D>        database type
     * @param identifier column that this field represents
     * @param getter     method reference to getter in entity
     * @param setter     method reference to setter in entity
     * @param typeMapper type mapper that is applied
     * @param unique     if column only contains unique values
     * @return           the created field
     */
    static <ENTITY, D> ShortField<ENTITY, D> create(
    ColumnIdentifier<ENTITY> identifier,
            ShortGetter<ENTITY> getter,
            ShortSetter<ENTITY> setter,
            TypeMapper<D, Short> typeMapper,
            boolean unique) {
        return new ShortFieldImpl<>(
            identifier, getter, setter, typeMapper, unique
        );
    }
}