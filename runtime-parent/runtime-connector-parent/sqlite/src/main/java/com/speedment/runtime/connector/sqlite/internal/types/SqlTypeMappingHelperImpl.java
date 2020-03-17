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
package com.speedment.runtime.connector.sqlite.internal.types;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.JavaTypeMap;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.speedment.runtime.core.util.CaseInsensitiveMaps.newCaseInsensitiveMap;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static java.util.Objects.requireNonNull;
import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Default implementation of the {@link SqlTypeMappingHelper} interface.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SqlTypeMappingHelperImpl implements SqlTypeMappingHelper {

    private final ConnectionPoolComponent connectionPool;
    private final DbmsHandlerComponent dbmsHandler;
    private final JavaTypeMap javaTypeMap;

    SqlTypeMappingHelperImpl(
        final ConnectionPoolComponent connectionPool,
        final DbmsHandlerComponent dbmsHandler,
        final JavaTypeMap javaTypeMap
    ) {
        this.connectionPool = requireNonNull(connectionPool);
        this.dbmsHandler    = requireNonNull(dbmsHandler);
        this.javaTypeMap    = requireNonNull(javaTypeMap);
    }

    @Override
    public CompletableFuture<Map<String, Class<?>>> loadFor(Dbms dbms) {
        final DbmsType dbmsType = dbmsTypeOf(dbmsHandler, dbms);
        final Set<TypeInfoMetaData> preSet = dbmsType.getDataTypes();

        return supplyAsync(() -> {
            final Map<String, Class<?>> sqlTypeMapping;

            try {
                sqlTypeMapping = !preSet.isEmpty()
                    ? readTypeMapFromSet(preSet)
                    : readTypeMapFromDB(dbms);
            } catch (final SQLException ex) {
                throw new SpeedmentException(
                    "Error loading type map from database.", ex
                );
            }

            return sqlTypeMapping;
        });
    }

    @Override
    public Optional<Class<?>> findFor(Map<String, Class<?>> loaded, ColumnMetaData metaData) {
        return Optional.ofNullable(javaTypeMap.findJdbcType(loaded, metaData));
    }

    private Map<String, Class<?>> readTypeMapFromSet(Set<TypeInfoMetaData> typeInfos) {
        return typeMapFromTypeInfo(new ArrayList<>(typeInfos));
    }

    private Map<String, Class<?>> readTypeMapFromDB(Dbms dbms) throws SQLException {
        requireNonNull(dbms);

        final List<TypeInfoMetaData> typeInfoMetaDataList = new ArrayList<>();
        try (final Connection connection = connectionPool.getConnection(dbms)) {
            try (final ResultSet rs = connection.getMetaData().getTypeInfo()) {
                while (rs.next()) {
                    final TypeInfoMetaData typeInfo = TypeInfoMetaData.of(rs);
                    typeInfoMetaDataList.add(typeInfo);
                }
            }
            return typeMapFromTypeInfo(typeInfoMetaDataList);
        }
    }

    private Map<String, Class<?>> typeMapFromTypeInfo(List<TypeInfoMetaData> typeInfoMetaDataList) {
        requireNonNull(typeInfoMetaDataList);

        final Map<String, Class<?>> result = newCaseInsensitiveMap();
        // First, put the java.sql.Types mapping for all types
        typeInfoMetaDataList.forEach(ti -> {
            final Optional<String> javaSqlTypeName = ti.javaSqlTypeName();

            javaSqlTypeName.ifPresent(tn -> {
                final Class<?> mappedClass = javaTypeMap.get(tn);
                if (mappedClass != null) {
                    result.put(tn, mappedClass);
                }
            });
        });

        // Then, put the typeInfo sqlName (That may be more specific) for all types
        typeInfoMetaDataList.forEach(ti -> {
            final String key = ti.getSqlTypeName();
            final Class<?> mappedClass = javaTypeMap.get(key);
            if (mappedClass != null) {
                result.put(key, mappedClass);
            } else {
                final Optional<String> javaSqlTypeName = ti.javaSqlTypeName();
                javaSqlTypeName.ifPresent(ltn -> {
                    final Class<?> lookupMappedClass = javaTypeMap.get(ltn);
                    if (lookupMappedClass != null) {
                        result.put(key, lookupMappedClass);
                    }
                });
            }
        });
        return result;
    }
}
