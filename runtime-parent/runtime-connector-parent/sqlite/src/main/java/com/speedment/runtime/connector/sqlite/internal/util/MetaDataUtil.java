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
package com.speedment.runtime.connector.sqlite.internal.util;

import com.speedment.runtime.config.parameter.OrderType;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import com.speedment.runtime.core.exception.SpeedmentException;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class used to read values from JDBC Metadata objects.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class MetaDataUtil {

    /**
     * Code returns {@code true} if the column with the specified metadata can
     * sometimes hold {@code NULL}-values in the database, otherwise
     * {@code false}.
     *
     * @param metaData  the metadata object retrieved from JDBC
     * @return  {@code true} if nullable, else {@code false}
     */
    public static boolean isNullable(ColumnMetaData metaData) {
        switch (metaData.getNullable()) {
            case DatabaseMetaData.columnNullable:
            case DatabaseMetaData.columnNullableUnknown: return true;
            case DatabaseMetaData.columnNoNulls: return false;
            default: throw new SpeedmentException(
                "Unknown nullable type " + metaData.getNullable());
        }
    }

    /**
     * Returns the {@link OrderType} of the index queried.
     *
     * @param rsIndex  the result set of the index metadata query
     * @return  the appropriate {@link OrderType}
     * @throws SQLException  if the result set could not be read
     */
    public static OrderType getOrderType(ResultSet rsIndex) throws SQLException {
        final String ascOrDesc = rsIndex.getString("ASC_OR_DESC");
        if ("A".equalsIgnoreCase(ascOrDesc)) {
            return OrderType.ASC;
        } else if ("D".equalsIgnoreCase(ascOrDesc)) {
            return OrderType.DESC;
        } else {
            return OrderType.NONE;
        }
    }

    /**
     * Returns {@code true} if the the column with the specified metadata should
     * be marked as auto-incrementing.
     *
     * @param metaData  the metadata object retrieved from JDBC
     * @return  {@code true} if auto-incrementing, else {@code false}
     */
    public static boolean isAutoIncrement(ColumnMetaData metaData) {
        return "YES".equalsIgnoreCase(metaData.getIsAutoincrement())
            || "YES".equalsIgnoreCase(metaData.getIsGeneratedcolumn());
    }

    /**
     * Returns {@code true} if the specified class is one of the standard java
     * wrapper types.
     *
     * @param type  the type to check
     * @return  {@code true} if a wrapper type, otherwise {@code false}
     */
    public static boolean isWrapper(Class<?> type) {
        return type == Byte.class
        ||  type == Short.class
        ||  type == Integer.class
        ||  type == Long.class
        ||  type == Float.class
        ||  type == Double.class
        ||  type == Character.class
        ||  type == Boolean.class;
    }

    /**
     * Utility classes should not be instantiated.
     */
    private MetaDataUtil() {}
}
