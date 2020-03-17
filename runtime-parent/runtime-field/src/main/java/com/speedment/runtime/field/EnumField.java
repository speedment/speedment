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

import com.speedment.runtime.compute.ToDoubleNullable;
import com.speedment.runtime.compute.ToEnumNullable;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.internal.EnumFieldImpl;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.predicate.FieldIsNotNullPredicate;
import com.speedment.runtime.field.predicate.FieldIsNullPredicate;
import com.speedment.runtime.field.predicate.Inclusion;
import com.speedment.runtime.field.trait.HasStringOperators;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.EnumSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

/**
 * A field representing an {@code Enum} value in the entity.
 *
 * @author Emil Forslund
 * @since  3.0.10
 *
 * @see  ComparableField
 */
public interface EnumField<ENTITY, D, E extends Enum<E>>
extends ComparableField<ENTITY, D, E>,
        HasStringOperators<ENTITY>,
        ToEnumNullable<ENTITY, E> {


    @Override
    EnumField<ENTITY, D, E> tableAlias(String tableAlias);

    /**
     * Returns the enum class of this field.
     *
     * @return  the enum class
     * @since   3.0.13
     */
    Class<E> enumClass();

    /**
     * Returns the set of possible values for this enum. The order will be the
     * ordinal order of the enum.
     * <p>
     * This method creates a copy of the internal storage structure so changes
     * to the returned collection are allowed.
     *
     * @return  the constants
     */
    EnumSet<E> constants();

    /**
     * A method that takes a {@code String} and converts it into an enum for
     * this field.
     * <p>
     * The function should return {@code null} if a {@code null} value is
     * specified as input and throw an exception if the value is invalid.
     *
     * @return  the string-to-enum mapper
     */
    Function<String, E> stringToEnum();

    /**
     * A method that takes an enum and converts it into a {@code String}.
     * <p>
     * The function should return {@code null} if a {@code null} value is
     * specified as input and throw an exception if the value is invalid.
     *
     * @return  the enum-to-string mapper
     */
    Function<E, String> enumToString();

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is equal to the specified value. The string
     * of the enum is determined using the {@link #enumToString()}-method.
     *
     * @param value  the argument
     * @return       the predicate
     */
    Predicate<ENTITY> equal(String value);

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is not equal to the specified value. The
     * string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param value  the argument
     * @return       the predicate
     */
    Predicate<ENTITY> notEqual(String value);

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is less than the specified value. The
     * string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param value  the argument
     * @return       the predicate
     */
    Predicate<ENTITY> lessThan(String value);

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is less or equal to the specified value. The
     * string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param value  the argument
     * @return       the predicate
     */
    Predicate<ENTITY> lessOrEqual(String value);

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is greater than the specified value. The
     * string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param value  the argument
     * @return       the predicate
     */
    Predicate<ENTITY> greaterThan(String value);

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is greater or equal to the specified value.
     * The string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param value  the argument
     * @return       the predicate
     */
    Predicate<ENTITY> greaterOrEqual(String value);

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is between the two specified values.
     * The string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param start  the start (inclusive)
     * @param end    the end (exclusive)
     * @return       the predicate
     */
    default Predicate<ENTITY> between(String start, String end) {
        return between(start, end, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    }

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is between the two specified values.
     * The string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param start      the start
     * @param end        the end
     * @param inclusion  if start and end are inclusive or exclusive
     * @return           the predicate
     */
    Predicate<ENTITY> between(String start, String end, Inclusion inclusion);

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is not between the two specified values.
     * The string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param start  the start (inclusive)
     * @param end    the end (exclusive)
     * @return       the predicate
     */
    default Predicate<ENTITY> notBetween(String start, String end) {
        return notBetween(start, end, Inclusion.START_INCLUSIVE_END_EXCLUSIVE);
    }

    /**
     * Returns a new predicate that evaluates if entities has a enum value for
     * this field with a value that is not between the two specified values.
     * The string of the enum is determined using the
     * {@link #enumToString()}-method.
     *
     * @param start      the start
     * @param end        the end
     * @param inclusion  if start and end are inclusive or exclusive
     * @return           the predicate
     */
    Predicate<ENTITY> notBetween(String start, String end, Inclusion inclusion);

    @Override
    FieldIsNullPredicate<ENTITY, E> isNull();

    @Override
    default FieldIsNotNullPredicate<ENTITY, E> isNotNull() {
        return isNull().negate();
    }

    @Override
    default E apply(ENTITY entity) {
        return get(entity);
    }

    @Override
    default ToDoubleNullable<ENTITY> mapToDoubleIfPresent(ToDoubleFunction<E> mapper) {
        return ComparableField.super.mapToDoubleIfPresent(mapper);
    }

    /**
     * Create a new instance of this interface using the default implementation.
     *
     * @param <ENTITY>      the entity type
     * @param <D>           the database type
     * @param <E>           the java enum type
     * @param identifier    the column that this field represents
     * @param getter        method reference to the getter in the entity
     * @param setter        method reference to the setter in the entity
     * @param typeMapper    the type mapper that is applied
     * @param enumToString  method to convert enum to a string
     * @param stringToEnum  method to convert a string to enum
     * @param enumClass     the enum class
     *
     * @return            the created field
     */
    static <ENTITY, D, E extends Enum<E>> EnumField<ENTITY, D, E> create(
            ColumnIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, E> getter,
            ReferenceSetter<ENTITY, E> setter,
            TypeMapper<D, E> typeMapper,
            Function<E, String> enumToString,
            Function<String, E> stringToEnum,
            Class<E> enumClass) {

        return new EnumFieldImpl<>(
            identifier, getter, setter, typeMapper,
            enumToString, stringToEnum, enumClass
        );
    }
}