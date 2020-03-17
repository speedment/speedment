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

import com.speedment.common.function.ToBooleanFunction;
import com.speedment.common.function.ToByteFunction;
import com.speedment.common.function.ToCharFunction;
import com.speedment.common.function.ToFloatFunction;
import com.speedment.common.function.ToShortFunction;
import com.speedment.runtime.compute.*;
import com.speedment.runtime.compute.trait.HasMapToDoubleIfPresent;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.field.exception.SpeedmentFieldException;
import com.speedment.runtime.field.internal.ReferenceFieldImpl;
import com.speedment.runtime.field.internal.expression.*;
import com.speedment.runtime.field.method.ReferenceGetter;
import com.speedment.runtime.field.method.ReferenceSetter;
import com.speedment.runtime.field.trait.HasReferenceOperators;
import com.speedment.runtime.field.trait.HasReferenceValue;
import com.speedment.runtime.typemapper.TypeMapper;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import static java.lang.String.format;

/**
 * A field that represents an object value.
 * 
 * @param <ENTITY>  the entity type
 * @param <D>       the database type
 * @param <V>       the field value type
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 * 
 * @see  Field
 * @see  HasReferenceOperators
 * @see  HasReferenceValue
 */
public interface ReferenceField<ENTITY, D, V> 
extends Field<ENTITY>, 
        HasReferenceOperators<ENTITY>,
        HasReferenceValue<ENTITY, D, V>,
        HasMapToDoubleIfPresent<ENTITY, ToDoubleFunction<V>> {
    
    /**
     * Creates a new {@link ReferenceField} using the default implementation. 
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
     * @return the created field
     */
    static <ENTITY, D, V> ReferenceField<ENTITY, D, V> create(
            ColumnIdentifier<ENTITY> identifier,
            ReferenceGetter<ENTITY, V> getter,
            ReferenceSetter<ENTITY, V> setter,
            TypeMapper<D, V> typeMapper,
            boolean unique) {
        
        return new ReferenceFieldImpl<>(
            identifier, getter, setter, typeMapper, unique
        );
    }

    @Override
    ReferenceField<ENTITY, D, V> tableAlias(String tableAlias);

    /**
     * Returns an {@link ToByteNullable} expression that has the value returned
     * by the specified mapper function if the value for this field is not
     * {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToByteNullable<ENTITY>
    mapToByteIfPresent(ToByteFunction<V> mapper) {
        return new FieldToByteImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToByteNullable} expression that has the value of this
     * field, casted to a {@code byte} by first casting it to {@link Number} and
     * then invoking {@link Number#byteValue()}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToByteNullable<ENTITY> asByte() {
        return mapToByteIfPresent(val -> {
            if (val instanceof Number) {
                final Number number = (Number) val;
                return number.byteValue();
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type byte, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToShortNullable} expression that has the value returned
     * by the specified mapper function if the value for this field is not
     * {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToShortNullable<ENTITY>
    mapToShortIfPresent(ToShortFunction<V> mapper) {
        return new FieldToShortImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToShortNullable} expression that has the value of this
     * field, casted to a {@code short} by first casting it to {@link Number}
     * and then invoking {@link Number#shortValue()}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToShortNullable<ENTITY> asShort() {
        return mapToShortIfPresent(val -> {
            if (val instanceof Number) {
                final Number number = (Number) val;
                return number.shortValue();
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type short, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToIntNullable} expression that has the value returned
     * by the specified mapper function if the value for this field is not
     * {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToIntNullable<ENTITY>
    mapToIntIfPresent(ToIntFunction<V> mapper) {
        return new FieldToIntImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToIntNullable} expression that has the value of this
     * field, casted to a {@code int} by first casting it to {@link Number} and
     * then invoking {@link Number#intValue()}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToIntNullable<ENTITY> asInt() {
        return mapToIntIfPresent(val -> {
            if (val instanceof Number) {
                final Number number = (Number) val;
                return number.intValue();
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type int, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToLongNullable} expression that has the value returned
     * by the specified mapper function if the value for this field is not
     * {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToLongNullable<ENTITY>
    mapToLongIfPresent(ToLongFunction<V> mapper) {
        return new FieldToLongImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToLongNullable} expression that has the value of this
     * field, casted to a {@code long} by first casting it to {@link Number} and
     * then invoking {@link Number#longValue()}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToLongNullable<ENTITY> asLong() {
        return mapToLongIfPresent(val -> {
            if (val instanceof Number) {
                final Number number = (Number) val;
                return number.longValue();
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type long, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToFloatNullable} expression that has the value returned
     * by the specified mapper function if the value for this field is not
     * {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToFloatNullable<ENTITY>
    mapToFloatIfPresent(ToFloatFunction<V> mapper) {
        return new FieldToFloatImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToFloatNullable} expression that has the value of this
     * field, casted to a {@code float} by first casting it to {@link Number}
     * and then invoking {@link Number#floatValue()}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToFloatNullable<ENTITY> asFloat() {
        return mapToFloatIfPresent(val -> {
            if (val instanceof Number) {
                final Number number = (Number) val;
                return number.floatValue();
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type float, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToDoubleNullable} expression that has the value
     * returned by the specified mapper function if the value for this field is
     * not {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    @Override
    default ToDoubleNullable<ENTITY>
    mapToDoubleIfPresent(ToDoubleFunction<V> mapper) {
        return new FieldToDoubleImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToDoubleNullable} expression that has the value of this
     * field, casted to a {@code double} by first casting it to {@link Number}
     * and then invoking {@link Number#doubleValue()}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToDoubleNullable<ENTITY> asDouble() {
        return mapToDoubleIfPresent(val -> {
            if (val instanceof Number) {
                final Number number = (Number) val;
                return number.doubleValue();
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type double, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToCharNullable} expression that has the value returned
     * by the specified mapper function if the value for this field is not
     * {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToCharNullable<ENTITY>
    mapToCharIfPresent(ToCharFunction<V> mapper) {
        return new FieldToCharImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToCharNullable} expression that has the value of this
     * field, casted to a {@code char} by first casting it to {@link Number} and
     * then invoking {@link Number#intValue()} and casting it to a {@code char}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToCharNullable<ENTITY> asChar() {
        return mapToCharIfPresent(val -> {
            if (val instanceof Number) {
                final Number number = (Number) val;
                return (char) number.intValue();
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type char, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToBooleanNullable} expression that has the value
     * returned by the specified mapper function if the value for this field is
     * not {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToBooleanNullable<ENTITY>
    mapToBooleanIfPresent(ToBooleanFunction<V> mapper) {
        return new FieldToBooleanImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToBooleanNullable} expression that has the value of
     * this field, casted to a {@code boolean} by first casting it to
     * {@link Boolean}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToBooleanNullable<ENTITY> asBoolean() {
        return mapToBooleanIfPresent(val -> {
            if (val instanceof Boolean) {
                return (Boolean) val;
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type boolean, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToStringNullable} expression that has the value
     * returned by the specified mapper function if the value for this field is
     * not {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToStringNullable<ENTITY>
    mapToStringIfPresent(Function<V, String> mapper) {
        return new FieldToStringImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToBooleanNullable} expression that has the value of
     * this field, casted to a {@code boolean} by first casting it to
     * {@link Boolean}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToStringNullable<ENTITY> asString() {
        return mapToStringIfPresent(val -> {
            if (val instanceof String) {
                return (String) val;
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type String, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToStringNullable} expression that has the value
     * returned by the specified mapper function if the value for this field is
     * not {@code null}, and otherwise {@code null}.
     *
     * @param mapper  the mapper operation
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToBigDecimalNullable<ENTITY>
    mapToBigDecimalIfPresent(Function<V, BigDecimal> mapper) {
        return new FieldToBigDecimalImpl<>(this, mapper);
    }

    /**
     * Returns an {@link ToBigDecimalNullable} expression that has the value of
     * this field but casted to a {@link BigDecimal}.
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default ToBigDecimalNullable<ENTITY> asBigDecimal() {
        return mapToBigDecimalIfPresent(val -> {
            if (val instanceof BigDecimal) {
                return (BigDecimal) val;
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type BigDecimal, but it was not.",
                identifier()
            ));
        });
    }

    /**
     * Returns an {@link ToEnumNullable} expression that has the value
     * returned by the specified mapper function if the value for this field is
     * not {@code null}, and otherwise {@code null}.
     *
     * @param <E>        the enum type
     * @param mapper     the mapper operation
     * @param enumClass  class of the enum to map to
     *
     * @return  expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default <E extends Enum<E>> ToEnumNullable<ENTITY, E>
    mapToEnumIfPresent(Function<V, E> mapper, Class<E> enumClass) {
        return new FieldToEnumImpl<>(this, mapper, enumClass);
    }

    /**
     * Returns an {@link ToBooleanNullable} expression that has the value of
     * this field, casted to a particular {@code enum} class.
     *
     * @param <E>        the enum type
     * @param enumClass  class of the enum to map to
     *
     * @return    expression for this value after mapper has been applied
     *
     * @since 3.1.0
     */
    default <E extends Enum<E>> ToEnumNullable<ENTITY, E> asEnum(Class<E> enumClass) {
        return mapToEnumIfPresent(val -> {
            if (enumClass.isInstance(val)) {
                return enumClass.cast(val);
            } else throw new SpeedmentFieldException(format(
                "Expected field %s to be of type %s, but it was not.",
                enumClass.getName(), identifier()
            ));
        }, enumClass);
    }
}