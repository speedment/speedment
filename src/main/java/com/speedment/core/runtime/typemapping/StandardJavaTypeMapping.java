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
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLXML;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author pemi
 */
public enum StandardJavaTypeMapping implements JavaTypeMapping {

    // If you add a mapping X here, make sure that AbstractSqlManager has a
    // corresponding method getX(ResultSet, String)
    
    OBJECT(Object.class, "Object", s -> (Object) s),
    BOOLEAN(Boolean.class, "Boolean", Boolean::parseBoolean),
    BYTE(Byte.class, "Byte", Byte::parseByte),
    SHORT(Short.class, "Short", Short::parseShort),
    INTEGER(Integer.class, "Int", Integer::parseInt),
    LONG(Long.class, "Long", Long::parseLong),
    FLOAT(Float.class, "Float", Float::parseFloat),
    DOUBLE(Double.class, "Double", Double::parseDouble),
    STRING(String.class, "String", Function.identity()),
    DATE(LocalDateTime.class, "LocalDateTime", LocalDateTime::parse),
    BIG_DECIMAL(BigDecimal.class, "BigDecimal", BigDecimal::new),
    BLOB(Blob.class, "Blob", s -> unableToMap(Blob.class)),
    CLOB(Clob.class, "Clob", s -> unableToMap(Clob.class)),
    ARRAY(Array.class, "Array", s -> unableToMap(Array.class)),
    REF(Ref.class, "Ref", s -> unableToMap(Ref.class)),
    URL(URL.class, "URL", s -> {
        try {
            return new URL(s);
        } catch (MalformedURLException mfe) {
            throw new SpeedmentException(mfe);
        }
    }),
    ROW_ID(RowId.class, "RowId", s -> unableToMap(RowId.class)),
    N_CLOB(NClob.class, "NClob", s -> unableToMap(NClob.class)),
    SQLXML(SQLXML.class, "SQLXML", s -> unableToMap(SQLXML.class));

    private static final Map<Class<?>, Function<String, ?>> parsers = new HashMap<>();

    static {
        for (StandardJavaTypeMapping mapping : values()) {
            parsers.put(mapping.clazz, mapping.stringMapper);
        }
    }

    private static <T> T unableToMap(Class<T> clazz) {
        throw new IllegalArgumentException("Unable to parse a string and make it " + clazz.toString());
    }

    private <T> StandardJavaTypeMapping(Class<T> clazz, String resultSetMethodName, Function<String, T> stringMapper) {
        this.clazz = Objects.requireNonNull(clazz);
        this.resultSetMethodName = Objects.requireNonNull(resultSetMethodName);
        this.stringMapper = Objects.requireNonNull(stringMapper);
    }

    private final Class<?> clazz;
    private final String resultSetMethodName;
    private final Function<String, ?> stringMapper;

//    private <T> void put(Class<T> clazz, Function<String, T> mapper) {
//        parsers.put(clazz, mapper);
//    }

    public static <T> T parse(Class<T> type, String inputValue) {
        @SuppressWarnings("unchecked")
        final Function<String, T> mapper = (Function<String, T>) parsers.getOrDefault(type, s -> unableToMap(type));
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
