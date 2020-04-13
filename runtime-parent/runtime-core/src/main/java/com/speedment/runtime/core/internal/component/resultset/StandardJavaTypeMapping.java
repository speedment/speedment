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
package com.speedment.runtime.core.internal.component.resultset;

import com.speedment.runtime.core.component.resultset.ResultSetMapping;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.util.LongUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

public final class StandardJavaTypeMapping {

    private StandardJavaTypeMapping() {}

    /**
     * Returns a {@link Stream} of all JavaTypeMapping that is defined in this
     * class.
     *
     * @return stream of all JavaTypeMappings
     */
    public static Stream<ResultSetMapping<?>> stream() {
        return Stream.of(VALUES);
    }
    
    private static <T> T unableToMapString(Class<T> clazz) {
        return unableToMap(String.class, clazz);
    }

    private static <T> T unableToMapLong(Class<T> clazz) {
        return unableToMap(Long.class, clazz);
    }

    private static <T> T unableToMap(Class<?> from, Class<T> to) {
        throw new IllegalArgumentException("Unable to parse a " + from.toString() + " and make it " + to.toString());
    }

    private static final ResultSetMapping<?>[] VALUES = {
        new JavaTypeMappingImpl<>(
            Object.class, "Object", s -> s, l -> l
        ),
        
        new JavaTypeMappingImpl<>(
            Boolean.class, "Boolean", Boolean::parseBoolean, l -> unableToMapLong(Boolean.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Byte.class, "Byte", Byte::parseByte, l -> LongUtil.cast(l, Byte.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Short.class, "Short", Short::parseShort, l -> LongUtil.cast(l, Short.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Integer.class, "Int", Integer::parseInt, l -> LongUtil.cast(l, Integer.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Long.class, "Long", Long::parseLong, l -> l
        ),
        
        new JavaTypeMappingImpl<>(
            Float.class, "Float", Float::parseFloat, l -> LongUtil.cast(l, Float.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Double.class, "Double", Double::parseDouble, l -> LongUtil.cast(l, Double.class)
        ),
        
        new JavaTypeMappingImpl<>(
            String.class, "String", Function.identity(), l -> Optional.ofNullable(l).map(Object::toString).orElse(null)
        ),
        
        new JavaTypeMappingImpl<>(
            Date.class, "Date", Date::valueOf, l -> unableToMapLong(Date.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Time.class, "Time", Time::valueOf, l -> unableToMapLong(Time.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Timestamp.class, "Timestamp", Timestamp::valueOf, l -> unableToMapLong(Timestamp.class)
        ),
        
        new JavaTypeMappingImpl<>(
            BigDecimal.class, "BigDecimal", BigDecimal::new, l -> LongUtil.cast(l, BigDecimal.class)
        ),
        
        new JavaTypeMappingImpl<>(
            BigInteger.class, "BigInteger", BigInteger::new, l -> LongUtil.cast(l, BigInteger.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Blob.class, "Blob", s -> unableToMapString(Blob.class), l -> unableToMapLong(Blob.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Clob.class, "Clob", s -> unableToMapString(Clob.class), l -> unableToMapLong(Clob.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Array.class, "Array", s -> unableToMapString(Array.class), l -> unableToMapLong(Array.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Ref.class, "Ref", s -> unableToMapString(Ref.class), l -> unableToMapLong(Ref.class)
        ),
        
        new JavaTypeMappingImpl<>(
            URL.class, "URL", s -> {
                try {
                    return new URL(s);
                } catch (MalformedURLException mfe) {
                    throw new SpeedmentException(mfe);
                }
            }, l -> unableToMapLong(URL.class)
        ),
        
        new JavaTypeMappingImpl<>(
            RowId.class, "RowId", s -> unableToMapString(RowId.class), l -> unableToMapLong(RowId.class)
        ),
        
        new JavaTypeMappingImpl<>(
            NClob.class, "NClob", s -> unableToMapString(NClob.class), l -> unableToMapLong(NClob.class)
        ),
        
        new JavaTypeMappingImpl<>(
            SQLXML.class, "SQLXML", s -> unableToMapString(SQLXML.class), l -> unableToMapLong(SQLXML.class)
        ),
        
        new JavaTypeMappingImpl<>(
            UUID.class, "UUID", java.util.UUID::fromString, l -> unableToMapLong(UUID.class)
        ),

        new JavaTypeMappingImpl<>(
            byte[].class, "Bytes",
            s -> unableToMapString(byte[].class),
            l -> unableToMapLong(byte[].class)
        )
    };

}
