package com.speedment.runtime.field;

import com.speedment.runtime.field.trait.HasStringOperators;

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

}