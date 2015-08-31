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
package com.speedment.internal.util.sql;

import com.speedment.exception.SpeedmentException;
import java.lang.reflect.Field;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import java.util.Optional;

/**
 *
 * @author pemi
 */
public final class SqlTypeInfo {

    //http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html#getTypeInfo()
    private final String sqlTypeName;
    private final int javaSqlTypeInt;
    private final int precision;
    private final int decimals;
    private final short nullable;
    private final boolean unsigned;

    public SqlTypeInfo(String sqlTypeName, int javaSqlTypeInt, int precision, int decimals, short nullable, boolean unsigned) {
        this.sqlTypeName = requireNonNull(sqlTypeName);
        this.javaSqlTypeInt = javaSqlTypeInt;
        this.precision = precision;
        this.decimals = decimals;
        this.nullable = nullable;
        this.unsigned = unsigned;
    }

    public static SqlTypeInfo from(ResultSet rs) throws SQLException {
        final String sqlTypeName = rs.getString("TYPE_NAME");
        final int javaSqlTypeInt = rs.getInt("DATA_TYPE");
        final int precision = rs.getInt("PRECISION");
        final short nullable = rs.getShort("NULLABLE");
        final boolean unsigned = rs.getBoolean("UNSIGNED_ATTRIBUTE");
        return new SqlTypeInfo(sqlTypeName, javaSqlTypeInt, precision, precision, nullable, unsigned);
    }

    private static final Map<Integer, String> JAVA_SQL_TYPE_INT_TO_STRING_MAP = new HashMap<>();
//    private static final Map<String, Integer> JAVA_SQL_TYPE_STRING_TO_INT_MAP = new HashMap<>();

    static {
        // Get all field in java.sql.Types using reflection
        final Field[] fields = java.sql.Types.class.getFields();
        for (final Field field : fields) {
            try {
                final String name = field.getName();
                final Integer value = (Integer) field.get(null);
                JAVA_SQL_TYPE_INT_TO_STRING_MAP.put(value, name);
//                JAVA_SQL_TYPE_STRING_TO_INT_MAP.put(name, value);
            } catch (final IllegalAccessException e) {
                throw new SpeedmentException(e);
            }
        }
    }

    public Optional<String> javaSqlTypeName() {
        return Optional.ofNullable(JAVA_SQL_TYPE_INT_TO_STRING_MAP.get(javaSqlTypeInt));
    }

//    public Optional<Integer> typeIntegerFrom() {
//        return Optional.ofNullable(JAVA_SQL_TYPE_STRING_TO_INT_MAP.get(sqlTypeName));
//    }
    public String getSqlTypeName() {
        return sqlTypeName;
    }

    public int getJavaSqlTypeInt() {
        return javaSqlTypeInt;
    }

    public int getPrecision() {
        return precision;
    }

    public int getDecimals() {
        return decimals;
    }

    public short getNullable() {
        return nullable;
    }

    public boolean isNoNulls() {
        return nullable == DatabaseMetaData.attributeNoNulls;
    }

    public boolean isNullable() {
        return nullable == DatabaseMetaData.attributeNullable;
    }

    public boolean isNullableUnknown() {
        return nullable == DatabaseMetaData.attributeNullableUnknown;
    }

    public boolean isUnsigned() {
        return unsigned;
    }

    @Override
    public String toString() {
        return getSqlTypeName()
                + " " + (isUnsigned() ? "UNSIGNED" : "")
                + " " + getPrecision()
                + " " + getDecimals()
                + " " + (isNullable() ? "NULL" : "")
                + " " + (isNoNulls() ? "NOT NULL" : "")
                + " (" + getJavaSqlTypeInt() + ")";
    }

}
