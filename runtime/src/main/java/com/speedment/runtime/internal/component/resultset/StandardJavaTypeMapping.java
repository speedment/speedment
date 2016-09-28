/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.component.resultset;

import com.speedment.runtime.component.resultset.ResultSetMapping;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.internal.util.LongUtil;

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
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.runtime.component.resultset.ResultSetMapping.unableToMapLong;
import static com.speedment.runtime.component.resultset.ResultSetMapping.unableToMapString;
import static com.speedment.runtime.util.StaticClassUtil.instanceNotAllowed;

public final class StandardJavaTypeMapping {
    
    /**
     * Returns a {@link Stream} of all JavaTypeMapping that is defined in this
     * class.
     *
     * @return stream of all JavaTypeMappings
     */
    public static Stream<ResultSetMapping<?>> stream() {
        return Stream.of(VALUES);
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
            Long.class, "Long", Long::parseLong, Function.identity()
        ),
        
        new JavaTypeMappingImpl<>(
            Float.class, "Float", Float::parseFloat, l -> LongUtil.cast(l, Float.class)
        ),
        
        new JavaTypeMappingImpl<>(
            Double.class, "Double", Double::parseDouble, l -> LongUtil.cast(l, Double.class)
        ),
        
        new JavaTypeMappingImpl<>(
            String.class, "String", Function.identity(), l -> Optional.ofNullable(l).map(lo -> lo.toString()).orElse(null)
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
            BigDecimal.class, "BigDecimal", s -> new BigDecimal(s), l -> LongUtil.cast(l, BigDecimal.class)
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
        )
    };

    /**
     * Utility classes should not be instantiated.
     */
    private StandardJavaTypeMapping() {
        instanceNotAllowed(getClass());
    }
}
