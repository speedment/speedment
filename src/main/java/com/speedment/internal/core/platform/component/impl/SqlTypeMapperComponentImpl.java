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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.config.Dbms;
import com.speedment.internal.core.platform.component.SqlTypeMapperComponent;
import com.speedment.internal.util.sql.SqlTypeInfo;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.UUID;

public final class SqlTypeMapperComponentImpl implements SqlTypeMapperComponent {

    private static final Map<String, Class<?>> JAVA_TYPE_MAP = new HashMap<>();
    private static final Class<?> DEFAULT_MAPPING = String.class;

    private static void put(String key, Class<?> clazz) {
        JAVA_TYPE_MAP.put(normalize(key), clazz);
    }

    static {
        put("CHAR", String.class);
        put("VARCHAR", String.class);
        put("LONGVARCHAR", String.class);
        put("LONGVARCHAR", String.class);
        put("NUMERIC", BigDecimal.class);
        put("DECIMAL", BigDecimal.class);
        put("BIT", Integer.class); ///
        put("TINYINT", Byte.class);
        put("SMALLINT", Short.class);
        put("INTEGER", Integer.class);
        put("BIGINT", Long.class);
        put("REAL", Float.class);
        put("FLOAT", Double.class);
        put("DOUBLE", Double.class);
//        JAVA_TYPE_MAP.put("BINARY", BYTE_ARRAY_MAPPING);
//        JAVA_TYPE_MAP.put("VARBINARY", BYTE_ARRAY_MAPPING);
        //JAVA_TYPE_MAP.put("LONGVARBINARY", BYTE_ARRAY_MAPPING);
        put("DATE", Date.class);
        put("TIME", Time.class);
        put("TIMESTAMP", Timestamp.class);
        put("CLOB", Clob.class);
        put("BLOB", Blob.class);
//        JAVA_TYPE_MAP.put("ARRAY", ARRAY_MAPPING);
        put("BOOLEAN", Boolean.class);

        //MySQL Specific mappings
        put("YEAR", Integer.class);

        //PostgreSQL specific mappings
        put("UUID", UUID.class);
    }

    @Override
    public Class<?> apply(Dbms dbms, SqlTypeInfo typeInfo) {
        requireNonNull(dbms);
        requireNonNull(typeInfo);
        final Optional<String> key = typeInfo.javaSqlTypeName();
        if (key.isPresent()) {
            return JAVA_TYPE_MAP.getOrDefault(normalize(key.get()), String.class);
        }
        return DEFAULT_MAPPING;
    }

    private static Optional<String> normalize(Optional<String> string) {
        if (string.isPresent()) {
            Optional.of(normalize(string.get()));
        }
        return Optional.empty();
    }

    private static String normalize(String string) {
        return string.toUpperCase();
    }

}
