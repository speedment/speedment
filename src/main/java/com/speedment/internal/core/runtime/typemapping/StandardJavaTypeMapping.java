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
package com.speedment.internal.core.runtime.typemapping;

import com.speedment.exception.SpeedmentException;
import static com.speedment.internal.core.runtime.typemapping.JavaTypeMapping.unableToMapLong;
import static com.speedment.internal.core.runtime.typemapping.JavaTypeMapping.unableToMapString;
import com.speedment.internal.util.LongUtil;
import static com.speedment.internal.util.StaticClassUtil.instanceNotAllowed;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.function.Function;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

public final class StandardJavaTypeMapping {

    public static final JavaTypeMapping<Object> OBJECT = new JavaTypeMappingImpl<>(
        Object.class, "Object", s -> (Object) s, l -> (Object) l
    );
    public static final JavaTypeMapping<Boolean> BOOLEAN = new JavaTypeMappingImpl<>(
        Boolean.class, "Boolean", Boolean::parseBoolean, l -> unableToMapLong(Boolean.class)
    );
    public static final JavaTypeMapping<Byte> BYTE = new JavaTypeMappingImpl<>(
        Byte.class, "Byte", Byte::parseByte, l -> LongUtil.cast(l, Byte.class)
    );
    public static final JavaTypeMapping<Short> SHORT = new JavaTypeMappingImpl<>(
        Short.class, "Short", Short::parseShort, l -> LongUtil.cast(l, Short.class)
    );
    public static final JavaTypeMapping<Integer> INTEGER = new JavaTypeMappingImpl<>(
        Integer.class, "Int", Integer::parseInt, l -> LongUtil.cast(l, Integer.class)
    );
    public static final JavaTypeMapping<Long> LONG = new JavaTypeMappingImpl<>(
        Long.class, "Long", Long::parseLong, Function.identity()
    );
    public static final JavaTypeMapping<Float> FLOAT = new JavaTypeMappingImpl<>(
        Float.class, "Float", Float::parseFloat, l -> LongUtil.cast(l, Float.class)
    );
    public static final JavaTypeMapping<Double> DOUBLE = new JavaTypeMappingImpl<>(
        Double.class, "Double", Double::parseDouble, l -> LongUtil.cast(l, Double.class)
    );
    public static final JavaTypeMapping<String> STRING = new JavaTypeMappingImpl<>(
        String.class, "String", Function.identity(), l -> Optional.ofNullable(l).map(lo -> lo.toString()).orElse(null)
    );
    public static final JavaTypeMapping<Date> DATE = new JavaTypeMappingImpl<>(
        Date.class, "Date", Date::valueOf, l -> unableToMapLong(Date.class)
    );
    public static final JavaTypeMapping<Time> TIME = new JavaTypeMappingImpl<>(
        Time.class, "Time", Time::valueOf, l -> unableToMapLong(Time.class)
    );
    public static final JavaTypeMapping<Timestamp> TIMESTAMP = new JavaTypeMappingImpl<>(
        Timestamp.class, "Timestamp", Timestamp::valueOf, l -> unableToMapLong(Timestamp.class)
    );
    public static final JavaTypeMapping<BigDecimal> BIG_DECIMAL = new JavaTypeMappingImpl<>(
        BigDecimal.class, "BigDecimal", s -> new BigDecimal(s), l -> LongUtil.cast(l, BigDecimal.class)
    );
    public static final JavaTypeMapping<Blob> BLOB = new JavaTypeMappingImpl<>(
        Blob.class, "Blob", s -> unableToMapString(Blob.class), l -> unableToMapLong(Blob.class)
    );
    public static final JavaTypeMapping<Clob> CLOB = new JavaTypeMappingImpl<>(
        Clob.class, "Clob", s -> unableToMapString(Clob.class), l -> unableToMapLong(Clob.class)
    );
    public static final JavaTypeMapping<Array> ARRAY = new JavaTypeMappingImpl<>(
        Array.class, "Array", s -> unableToMapString(Array.class), l -> unableToMapLong(Array.class)
    );
    public static final JavaTypeMapping<Ref> REF = new JavaTypeMappingImpl<>(
        Ref.class, "Ref", s -> unableToMapString(Ref.class), l -> unableToMapLong(Ref.class)
    );
    public static final JavaTypeMapping<URL> URL = new JavaTypeMappingImpl<>(
        URL.class, "URL", s -> {
            try {
                return new URL(s);
            } catch (MalformedURLException mfe) {
                throw new SpeedmentException(mfe);
            }
        }, l -> unableToMapLong(URL.class)
    );
    public static final JavaTypeMapping<RowId> ROW_ID = new JavaTypeMappingImpl<>(
        RowId.class, "RowId", s -> unableToMapString(RowId.class), l -> unableToMapLong(RowId.class)
    );
    public static final JavaTypeMapping<NClob> N_CLOB = new JavaTypeMappingImpl<>(
        NClob.class, "NClob", s -> unableToMapString(NClob.class), l -> unableToMapLong(NClob.class)
    );
    public static final JavaTypeMapping<SQLXML> SQLXML = new JavaTypeMappingImpl<>(
        SQLXML.class, "SQLXML", s -> unableToMapString(SQLXML.class), l -> unableToMapLong(SQLXML.class)
    );

    /**
     * Iterate over all JavaTypeMapping fields that are defined in this Class
     * and collect them in an array. If we add new static fields in the future,
     * they will be added automatically :-)
     *
     */
    private static final JavaTypeMapping<?>[] VALUES = Stream.of(StandardJavaTypeMapping.class.getDeclaredFields())
        .filter(f -> Modifier.isStatic(f.getModifiers()))
        .map(StandardJavaTypeMapping::wrapGetStaticField)
        .filter(Objects::nonNull)
        .filter(f -> JavaTypeMapping.class.isAssignableFrom(f.getClass()))
        .map(JavaTypeMapping.class::cast)
        .collect(toList()).toArray(new JavaTypeMapping<?>[0]);
    
    /**
     * Returns a {@link Stream} of all JavaTypeMapping that is defined in this
     * class.
     *
     * @return a {@link Stream} of all JavaTypeMapping that is defined in this
     * class
     */
    public static Stream<JavaTypeMapping<?>> stream() {
        return Stream.of(VALUES);
    }

    /**
     * Wraps the exception so that we can use Field::get in Streams.
     *
     * @param f the field
     * @return the value of the static field
     */
    private static Object wrapGetStaticField(Field f) {
        requireNonNull(f);
        try {
            final Object o = f.get(null);
            return o;
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new SpeedmentException(e);
        }
    }

    /**
     * Utility classes should not be instantiated.
     */
    private StandardJavaTypeMapping() { instanceNotAllowed(getClass()); }
}