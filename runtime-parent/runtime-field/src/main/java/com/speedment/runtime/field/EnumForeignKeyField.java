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
import com.speedment.runtime.field.internal.EnumForeignKeyFieldImpl;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.trait.HasComparableOperators;
import com.speedment.runtime.field.trait.HasFinder;
import com.speedment.runtime.field.trait.HasNullableFinder;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.function.Function;

/**
 * A field representing an {@code Enum} value in the entity that has a finder to
 * another entity.
 *
 * @param <ENTITY>         the entity type
 * @param <D>              the database type
 * @param <E>              the java enum type
 * @param <FK>             the foreign key entity type
 *
 * @author Emil Forslund
 * @since  3.0.10
 *
 * @see  EnumField
 * @see  HasFinder
 */
public interface EnumForeignKeyField<ENTITY, D, E extends Enum<E>, FK>
extends EnumField<ENTITY, D, E>,
        HasNullableFinder<ENTITY, FK> {

    @Override
    EnumForeignKeyField<ENTITY, D, E, FK> tableAlias(String tableAlias);

    /**
     * A method that takes a {@code String} and converts it into an enum for
     * this field.
     * <p>
     * The function should return {@code null} if a {@code null} value is
     * specified as input and throw an exception if the value is invalid.
     *
     * @return  the string-to-enum mapper
     */
    @Override
    Function<String, E> stringToEnum();

    /**
     * A method that takes an enum and converts it into a {@code String}.
     * <p>
     * The function should return {@code null} if a {@code null} value is
     * specified as input and throw an exception if the value is invalid.
     *
     * @return  the enum-to-string mapper
     */
    @Override
    Function<E, String> enumToString();

    /**
     * Create a new instance of this interface using the default implementation.
     *
     * @param <ENTITY>      the entity type
     * @param <D>           the database type
     * @param <E>           the java enum type
     * @param <FK>          the foreign key entity type
     * @param identifier    the column that this field represents
     * @param getter        method reference to the getter in the entity
     * @param setter        method reference to the setter in the entity
     * @param typeMapper    the type mapper that is applied
     * @param referenced    the field in the foreign entity that is referenced
     * @param enumToString  method to convert enum to a string
     * @param stringToEnum  method to convert a string to enum
     * @param enumClass     the enum class
     *
     * @return            the created field
     */
    static <ENTITY, D, E extends Enum<E>, FK>
    EnumForeignKeyField<ENTITY, D, E, FK> create(
            ColumnIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, E> getter,
            ReferenceSetter<ENTITY, E> setter,
            TypeMapper<D, E> typeMapper,
            HasComparableOperators<FK, E> referenced,
            Function<E, String> enumToString,
            Function<String, E> stringToEnum,
            Class<E> enumClass) {

        return new EnumForeignKeyFieldImpl<>(
            identifier, getter, setter, typeMapper, referenced,
            enumToString, stringToEnum, enumClass
        );
    }
}