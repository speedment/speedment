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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.Speedment;
import com.speedment.config.db.Dbms;
import com.speedment.component.SqlTypeMapperComponent;
import com.speedment.util.sql.SqlTypeInfo;
import com.speedment.license.Software;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import static java.util.Objects.requireNonNull;

public final class SqlTypeMapperComponentImpl extends InternalOpenSourceComponent implements SqlTypeMapperComponent {

    private static final Map<String, Class<?>> JAVA_TYPE_MAP = new HashMap<>();
    private static final Class<?> DEFAULT_MAPPING = Object.class;

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
        //put("BINARY", BYTE_ARRAY_MAPPING);
        //put("VARBINARY", BYTE_ARRAY_MAPPING);
        //put("LONGVARBINARY", BYTE_ARRAY_MAPPING);
        put("DATE", Date.class);
        put("TIME", Time.class);
        put("TIMESTAMP", Timestamp.class);
        put("CLOB", Clob.class);
        put("BLOB", Blob.class);
        //put("ARRAY", ARRAY_MAPPING);
        put("BOOLEAN", Boolean.class);

        //MySQL Specific mappings
        put("YEAR", Integer.class);

        //PostgreSQL specific mappings
        put("UUID", UUID.class);
        //TODO: Add postgresql specific type mappings
    }

    public SqlTypeMapperComponentImpl(Speedment speedment) {
        super(speedment);
    }

    @Override
    public Class<?> apply(Dbms dbms, SqlTypeInfo typeInfo) {
        requireNonNull(dbms);
        requireNonNull(typeInfo);

        // Firsty, check the sqlTypeName
        final String sqlTypeName = typeInfo.getSqlTypeName();
        final Class<?> clazz = JAVA_TYPE_MAP.get(sqlTypeName);
        if (clazz != null) {
            return clazz;
        }

        // Secondly, check the java.sql.Types name
        final Optional<String> key = typeInfo.javaSqlTypeName();
        if (key.isPresent()) {
            return JAVA_TYPE_MAP.getOrDefault(normalize(key.get()), DEFAULT_MAPPING);
        }
        return DEFAULT_MAPPING;
    }

    @Override
    public Stream<Software> getDependencies() {
        return Stream.empty();
    }

    private static void put(String key, Class<?> clazz) {
        JAVA_TYPE_MAP.put(normalize(key), clazz);
    }

    private static String normalize(String string) {
        return string.toUpperCase();
    }
}
