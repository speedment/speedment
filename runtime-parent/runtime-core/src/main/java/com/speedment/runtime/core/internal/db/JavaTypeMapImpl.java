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
package com.speedment.runtime.core.internal.db;

import com.speedment.runtime.core.db.JavaTypeMap;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.component.resultset.StandardJavaTypeMapping;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.core.internal.db.RuleUtil.DEFAULT_RULE;
import static com.speedment.runtime.core.util.CaseInsensitiveMaps.newCaseInsensitiveMap;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.0
 */
public final class JavaTypeMapImpl implements JavaTypeMap {

    private final List<Rule> rules;
    private final Map<String, Class<?>> inner;
    
    public JavaTypeMapImpl() {
        this(map -> {});
    }
    
    /**
     * Sets up the java type map for this database type
     * 
     * @param installer  the installer
     * @see <a href="http://docs.oracle.com/javase/1.5.0/docs/guide/jdbc/getstart/mapping.html">Official Mappings</a>
     */
    private JavaTypeMapImpl(Consumer<Map<String, Class<?>>> installer) {
        rules = new CopyOnWriteArrayList<>();
        inner = newCaseInsensitiveMap();
        
        inner.put("CHAR", String.class);
        inner.put("VARCHAR", String.class);
        inner.put("LONGVARCHAR", String.class);
        inner.put("NUMERIC", BigDecimal.class);
        inner.put("DECIMAL", BigDecimal.class);
        inner.put("NUMBER", BigDecimal.class);
        inner.put("BIT", Integer.class); ///
        inner.put("TINYINT", Byte.class);
        inner.put("TINYINT UNSIGNED", Short.class);
        inner.put("SMALLINT", Short.class);
        inner.put("SMALLINT UNSIGNED", Integer.class);
        inner.put("INTEGER", Integer.class);
        inner.put("INTEGER UNSIGNED", Long.class);
        inner.put("BIGINT", Long.class);
        inner.put("BIGINT UNSIGNED", BigInteger.class);
        inner.put("REAL", Float.class);
        inner.put("FLOAT", Double.class);
        inner.put("DOUBLE", Double.class);
        inner.put("DATE", java.sql.Date.class);
        inner.put("DATETIME", Timestamp.class);
        inner.put("TIME", Time.class);
        inner.put("TIMESTAMP", Timestamp.class);
        inner.put("CLOB", Clob.class);
        inner.put("BLOB", Blob.class);
        inner.put("BOOLEAN", Boolean.class);
        inner.put("BOOL", Boolean.class);

        //MySQL Specific mappings
        inner.put("YEAR", Integer.class);
        inner.put("INT UNSIGNED", Long.class);

        //PostgreSQL specific mappings
        inner.put("UUID", UUID.class);
        
        installer.accept(inner);
        rules.add(DEFAULT_RULE);
        assertJavaTypesKnown();
    }
    
    @Override
    public final void addRule(Rule rule) {
        rules.add(requireNonNull(rule));
    }
    
    @Override
    public final Class<?> findJdbcType(Map<String, Class<?>> sqlTypeMapping, ColumnMetaData md) {
        // Firstly, check if we have any rule for this type.
        final Optional<Class<?>> ruled = rules.stream()
            .map(r -> r.findJdbcType(sqlTypeMapping, md))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .findFirst();
        
        if (ruled.isPresent()) {
            return ruled.get();
        } else {
            // Secondly, try  md.getTypeName()
            final String typeName = md.getTypeName();
            final Class<?> innerType = inner.get(typeName);
            if (innerType != null) return innerType;

            Class<?> result = sqlTypeMapping.get(typeName);
            if (result == null) {

                // Type (int) according to java.sql.Types (e.g. 4) that
                // we got from the ColumnMetaData
                final int type = md.getDataType();

                // Variable name (String) according to java.sql.Types (e.g. INTEGER)
                final Optional<String> oTypeName = TypeInfoMetaData.lookupJavaSqlType(type);
                if (oTypeName.isPresent()) {
                    final String typeName2 = oTypeName.get();
                    // Thirdly, try the corresponding name using md.getDataType()
                    // and then lookup java.sql.Types name
                    result = sqlTypeMapping.get(typeName2);
                }
            }

            return result;
        }
    }
    
    @Override
    public final void put(String key, Class<?> clazz) {
        requireNonNulls(key, clazz);
        inner.put(key, clazz);
    }
    
    @Override
    public final Class<?> get(String key) {
        requireNonNull(key);
        return inner.get(key);
    }
    
    private void assertJavaTypesKnown() {
        final Map<String, Class<?>> unmapped = new LinkedHashMap<>();
        
        inner.forEach((key, clazz) -> {
            if (StandardJavaTypeMapping.stream()
                .noneMatch(jtm -> jtm.getJavaClass().equals(clazz))) {
                unmapped.put(key, clazz);
            }
        });
        
        if (!unmapped.isEmpty()) {
            throw new SpeedmentException(
                "There are mappings that have no " + 
                StandardJavaTypeMapping.class.getSimpleName() + 
                " " + unmapped
            );
        }
    }
}
