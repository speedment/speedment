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
package com.speedment.runtime.core.internal.db.metadata;

import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.sql.DatabaseMetaData;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class TypeInfoMetaDataImpl implements TypeInfoMetaData {

    //http://docs.oracle.com/javase/7/docs/api/java/sql/DatabaseMetaData.html#getTypeInfo()
    private final String sqlTypeName;
    private final int javaSqlTypeInt;
    private final int precision;
    private final int decimals;
    private final short nullable;
    private final boolean unsigned;

    public TypeInfoMetaDataImpl(String sqlTypeName, int javaSqlTypeInt, int precision, int decimals, short nullable, boolean unsigned) {
        this.sqlTypeName = requireNonNull(sqlTypeName);
        this.javaSqlTypeInt = javaSqlTypeInt;
        this.precision = precision;
        this.decimals = decimals;
        this.nullable = nullable;
        this.unsigned = unsigned;
    }

//    private static final Map<Integer, String> JAVA_SQL_TYPE_INT_TO_STRING_MAP = new HashMap<>();
//
//    static {
//        // Get all field in java.sql.Types using reflection
//        final Field[] fields = java.sql.Types.class.getFields();
//        for (final Field field : fields) {
//            try {
//                final String name = field.getName();
//                final Integer value = (Integer) field.get(null);
//                JAVA_SQL_TYPE_INT_TO_STRING_MAP.put(value, name);
//            } catch (final IllegalAccessException e) {
//                throw new SpeedmentException(e);
//            }
//        }
//    }

    @Override
    public String getSqlTypeName() {
        return sqlTypeName;
    }

    @Override
    public int getJavaSqlTypeInt() {
        return javaSqlTypeInt;
    }

    @Override
    public int getPrecision() {
        return precision;
    }

    @Override
    public int getDecimals() {
        return decimals;
    }

    @Override
    public short getNullable() {
        return nullable;
    }

    @Override
    public boolean isNoNulls() {
        return nullable == DatabaseMetaData.attributeNoNulls;
    }

    @Override
    public boolean isNullable() {
        return nullable == DatabaseMetaData.attributeNullable;
    }

    @Override
    public boolean isNullableUnknown() {
        return nullable == DatabaseMetaData.attributeNullableUnknown;
    }

    @Override
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
