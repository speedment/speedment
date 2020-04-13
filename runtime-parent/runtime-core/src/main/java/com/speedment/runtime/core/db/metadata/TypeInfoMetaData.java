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
package com.speedment.runtime.core.db.metadata;

import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.db.metadata.TypeInfoMetaDataImpl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author  Per Minborg
 */
public interface TypeInfoMetaData {

    final class Hidden {

        private static final Map<Integer, String> JAVA_SQL_TYPE_INT_TO_STRING_MAP = new HashMap<>();

        private Hidden() {}
        
        static {
            // Get all field in java.sql.Types using reflection
            final Field[] fields = java.sql.Types.class.getFields();
            for (final Field field : fields) {
                try {
                    final String name = field.getName();
                    final Integer value = (Integer) field.get(null);
                    JAVA_SQL_TYPE_INT_TO_STRING_MAP.put(value, name);
                } catch (final IllegalAccessException e) {
                    throw new SpeedmentException(e);
                }
            }
        }
    }

    /**
     * Looks up and returns the given int and returns the name of that int
     * according to {@link java.sql.Types}. If no value can be found,
     * Optional.empty() is returned.
     *
     * @param javaSqlTypeInt the value in java.sql.Types to lookup
     * @return the given int and returns the name of that int according to
     * {@link java.sql.Types}
     */
    static Optional<String> lookupJavaSqlType(int javaSqlTypeInt) {
        return Optional.ofNullable(Hidden.JAVA_SQL_TYPE_INT_TO_STRING_MAP.get(javaSqlTypeInt));
    }

    //http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html#getTypeInfo()
    static TypeInfoMetaData of(ResultSet rs) throws SQLException {
        final String sqlTypeName = rs.getString("TYPE_NAME");
        final int javaSqlTypeInt = rs.getInt("DATA_TYPE");
        final int precision = rs.getInt("PRECISION");
        final short nullable = rs.getShort("NULLABLE");
        final boolean unsigned = rs.getBoolean("UNSIGNED_ATTRIBUTE");
        return of(sqlTypeName, javaSqlTypeInt, precision, precision, nullable, unsigned);
    }

    static TypeInfoMetaData of(String sqlTypeName, int javaSqlTypeInt, int precision, int decimals, short nullable, boolean unsigned) {
        return new TypeInfoMetaDataImpl(sqlTypeName, javaSqlTypeInt, precision, decimals, nullable, unsigned);
    }

    default Optional<String> javaSqlTypeName() {
        return lookupJavaSqlType(getJavaSqlTypeInt());
    }

    String getSqlTypeName();

    int getJavaSqlTypeInt();

    int getPrecision();

    int getDecimals();

    short getNullable();

    boolean isNoNulls();

    boolean isNullable();

    boolean isNullableUnknown();

    boolean isUnsigned();
}
