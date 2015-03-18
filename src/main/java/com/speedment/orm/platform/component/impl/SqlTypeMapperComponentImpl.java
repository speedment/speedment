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
package com.speedment.orm.platform.component.impl;

import com.speedment.orm.config.model.Dbms;
import com.speedment.orm.platform.component.SqlTypeMapperComponent;
import com.speedment.util.java.sql.TypeInfo;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SqlTypeMapperComponentImpl implements SqlTypeMapperComponent {

    private static final Map<String, Class<?>> JAVA_TYPE_MAP = new HashMap<>();

    static {
        JAVA_TYPE_MAP.put("CHAR", String.class);
        JAVA_TYPE_MAP.put("VARCHAR", String.class);
        JAVA_TYPE_MAP.put("LONGVARCHAR", String.class);
        JAVA_TYPE_MAP.put("LONGVARCHAR", String.class);
        JAVA_TYPE_MAP.put("NUMERIC", BigDecimal.class);
        JAVA_TYPE_MAP.put("DECIMAL", BigDecimal.class);
//        JAVA_TYPE_MAP.put("BIT", BIT_MAPPING);
        JAVA_TYPE_MAP.put("TINYINT", Byte.class);
        JAVA_TYPE_MAP.put("SMALLINT", Short.class);
        JAVA_TYPE_MAP.put("INTEGER", Integer.class);
        JAVA_TYPE_MAP.put("BIGINT", Long.class);
        JAVA_TYPE_MAP.put("REAL", Float.class);
        JAVA_TYPE_MAP.put("FLOAT", Double.class);
        JAVA_TYPE_MAP.put("DOUBLE", Double.class);
//        JAVA_TYPE_MAP.put("BINARY", BYTE_ARRAY_MAPPING);
//        JAVA_TYPE_MAP.put("VARBINARY", BYTE_ARRAY_MAPPING);
        //JAVA_TYPE_MAP.put("LONGVARBINARY", BYTE_ARRAY_MAPPING);
        JAVA_TYPE_MAP.put("DATE", Date.class);
        JAVA_TYPE_MAP.put("TIME", Time.class);
        JAVA_TYPE_MAP.put("TIMESTAMP", Timestamp.class);
        JAVA_TYPE_MAP.put("CLOB", Clob.class);
        JAVA_TYPE_MAP.put("BLOB", Blob.class);
//        JAVA_TYPE_MAP.put("ARRAY", ARRAY_MAPPING);
        JAVA_TYPE_MAP.put("BOOLEAN", Boolean.class);

        //MySQL Specific mappings
        JAVA_TYPE_MAP.put("YEAR", Integer.class);

        //PostgreSQL specific mappings
        JAVA_TYPE_MAP.put("UUID", UUID.class);

    }

    @Override
    public Class<?> map(Dbms dbms, TypeInfo typeInfo) {
        return JAVA_TYPE_MAP.getOrDefault(typeInfo.javaSqlTypeName(), String.class);
    }

}
