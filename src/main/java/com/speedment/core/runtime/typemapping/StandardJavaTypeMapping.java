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
package com.speedment.core.runtime.typemapping;

import com.speedment.core.config.model.Dbms;
import com.speedment.core.exception.SpeedmentException;
import com.speedment.util.LongUtil;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author pemi
 */
public enum StandardJavaTypeMapping implements JavaTypeMapping {

    // If you add a mapping X here, make sure that AbstractSqlManager has a
    // corresponding method getX(ResultSet, String)
    OBJECT(Object.class, "Object", s -> (Object) s, l -> (Object) l),
    BOOLEAN(Boolean.class, "Boolean", Boolean::parseBoolean, l -> unableToMapLong(Boolean.class)),
    BYTE(Byte.class, "Byte", Byte::parseByte, l -> LongUtil.cast(l, Byte.class)),
    SHORT(Short.class, "Short", Short::parseShort, l -> LongUtil.cast(l, Short.class)),
    INTEGER(Integer.class, "Int", Integer::parseInt, l -> LongUtil.cast(l, Integer.class)),
    LONG(Long.class, "Long", Long::parseLong, Function.identity()),
    FLOAT(Float.class, "Float", Float::parseFloat, l -> LongUtil.cast(l, Float.class)),
    DOUBLE(Double.class, "Double", Double::parseDouble, l -> LongUtil.cast(l, Double.class)),
    STRING(String.class, "String", Function.identity(), l -> Optional.ofNullable(l).map(lo -> lo.toString()).orElse(null)),
    DATE(Date.class, "Date", Date::valueOf, l -> unableToMapLong(Date.class)),
    TIME(Time.class, "Time", Time::valueOf, l -> unableToMapLong(Time.class)),
    TIMESTAMP(Timestamp.class, "Timestamp", Timestamp::valueOf, l -> unableToMapLong(Timestamp.class)),
    BIG_DECIMAL(BigDecimal.class, "BigDecimal", s -> new BigDecimal(s), l -> LongUtil.cast(l, BigDecimal.class)),
    BLOB(Blob.class, "Blob", s -> unableToMapString(Blob.class), l -> unableToMapLong(Blob.class)),
    CLOB(Clob.class, "Clob", s -> unableToMapString(Clob.class), l -> unableToMapLong(Clob.class)),
    ARRAY(Array.class, "Array", s -> unableToMapString(Array.class), l -> unableToMapLong(Array.class)),
    REF(Ref.class, "Ref", s -> unableToMapString(Ref.class), l -> unableToMapLong(Ref.class)),
    URL(URL.class, "URL", s -> {
        try {
            return new URL(s);
        } catch (MalformedURLException mfe) {
            throw new SpeedmentException(mfe);
        }
    }, l -> unableToMapLong(URL.class)),
    ROW_ID(RowId.class, "RowId", s -> unableToMapString(RowId.class), l -> unableToMapLong(RowId.class)),
    N_CLOB(NClob.class, "NClob", s -> unableToMapString(NClob.class), l -> unableToMapLong(NClob.class)),
    SQLXML(SQLXML.class, "SQLXML", s -> unableToMapString(SQLXML.class), l -> unableToMapLong(SQLXML.class));

    private static final Map<Class<?>, Function<String, ?>> stringParsers = new HashMap<>();
    private static final Map<Class<?>, Function<Long, ?>> longParsers = new HashMap<>();

    static {
        for (StandardJavaTypeMapping mapping : values()) {
            stringParsers.put(mapping.clazz, mapping.stringMapper);
            longParsers.put(mapping.clazz, mapping.longMapper);
        }
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

    private <T> StandardJavaTypeMapping(Class<T> clazz, String resultSetMethodName, Function<String, T> stringMapper, Function<Long, T> longMapper) {
        this.clazz = Objects.requireNonNull(clazz);
        this.resultSetMethodName = Objects.requireNonNull(resultSetMethodName);
        this.stringMapper = Objects.requireNonNull(stringMapper);
        this.longMapper = longMapper;
    }

    private final Class<?> clazz;
    private final String resultSetMethodName;
    private final Function<String, ?> stringMapper;
    private final Function<Long, ?> longMapper; // Used for auto-increment fields for example

//    private <T> void put(Class<T> clazz, Function<String, T> mapper) {
//        parsers.put(clazz, mapper);
//    }
    public static <T> T parse(Class<T> type, String inputValue) {
        @SuppressWarnings("unchecked")
        final Function<String, T> mapper = (Function<String, T>) stringParsers.getOrDefault(type, s -> unableToMapString(type));
        return mapper.apply(inputValue);
    }

    public static <T> T parse(Class<T> type, Long inputValue) {
        @SuppressWarnings("unchecked")
        final Function<Long, T> mapper = (Function<Long, T>) longParsers.getOrDefault(type, s -> unableToMapLong(type));
        return mapper.apply(inputValue);
    }

    @Override
    public Class<?> getJavaClass() {
        return clazz;
    }

    @Override
    public String getResultSetMethodName(Dbms dbms) {
        return resultSetMethodName;
    }

}
