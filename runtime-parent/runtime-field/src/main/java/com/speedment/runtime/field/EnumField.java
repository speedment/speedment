package com.speedment.runtime.field;

import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.internal.EnumFieldImpl;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.trait.HasStringOperators;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.function.Function;

/**
 * A field representing an {@code Enum} value in the entity.
 *
 * @author Emil Forslund
 * @since  3.0.10
 */
public interface EnumField<ENTITY, D, E extends Enum<E>>
extends ComparableField<ENTITY, D, E>,
        HasStringOperators<ENTITY, D> {

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