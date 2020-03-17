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

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.db.SqlSupplier;
import com.speedment.runtime.core.db.metadata.ColumnMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Per Minborg
 */
public class ColumnMetaDataImpl implements ColumnMetaData {

    private static final Logger LOGGER = LoggerManager.getLogger(ColumnMetaDataImpl.class);

    private final String tableCat;
    private final String tableSchem;
    private final String tableName;
    private final String columnName;
    private final IntHolder dataType;
    private final String typeName;
    private final IntHolder columnSize;
    private final Void bufferLength;
    private final IntHolder decimalDigits;
    private final IntHolder numPrecRadix;
    private final IntHolder nullable;
    private final String remarks;
    private final String columnDef;
    private final IntHolder sqlDataType;
    private final IntHolder sqlDatetimeSub;
    private final IntHolder charOctetLength;
    private final IntHolder ordinalPosition;
    private final String scopeCatalog;
    private final String scopeSchema;
    private final String scopeTable;
    private final IntHolder sourceDataType;
    private final String isAutoincrement;
    private final String isGeneratedcolumn;

    public ColumnMetaDataImpl(ResultSet rs) {
        // These must be read in order for some databases (sic!)
        tableCat = readSilent(rs, () -> rs.getString(1));
        tableSchem = readSilent(rs, () -> rs.getString(2));
        tableName = readSilent(rs, () -> rs.getString(3));
        columnName = readSilent(rs, () -> rs.getString(4));
        dataType = new IntHolder(rs, () -> rs.getInt(5));
        typeName = readSilent(rs, () -> rs.getString(6));
        columnSize = new IntHolder(rs, () -> rs.getInt(7));
        bufferLength = null;
        decimalDigits = new IntHolder(rs, () -> rs.getInt(9));
        numPrecRadix = new IntHolder(rs, () -> rs.getInt(10));
        nullable = new IntHolder(rs, () -> rs.getInt(11));
        remarks = readSilent(rs, () -> rs.getString(12));
        columnDef = readSilent(rs, () -> rs.getString(13));
        sqlDataType = new IntHolder(rs, () -> rs.getInt(14));
        sqlDatetimeSub = new IntHolder(rs, () -> rs.getInt(15));
        charOctetLength = new IntHolder(rs, () -> rs.getInt(16));
        ordinalPosition = new IntHolder(rs, () -> rs.getInt(17));
        scopeCatalog = readSilent(rs, () -> rs.getString(18));
        scopeSchema = readSilent(rs, () -> rs.getString(19));
        scopeTable = readSilent(rs, () -> rs.getString(20));
        sourceDataType = new IntHolder(rs, () -> rs.getInt(21));
        isAutoincrement = readSilent(rs, () -> rs.getString(22));
        isGeneratedcolumn = readSilent(rs, () -> rs.getString(23), false); // Somewhat non-standard
    }

    private static class IntHolder {

        private final int value;
        private final boolean isNull;

        IntHolder(ResultSet rs, SqlIntSupplier supplier) {
            int val = 0;
            boolean isValueNull= true;
            try {
                val = supplier.get();
                isValueNull = rs.wasNull();
            } catch (SQLException sqle) {
                // Ignore, value = 0 and wasNull true
            }
            this.value = val;
            this.isNull = isValueNull;
        }

        public int getValue() {
            return value;
        }

        public boolean isNull() {
            return isNull;
        }

        @Override
        public String toString() {
            return isNull ? "null" : Integer.toString(value);
        }

    }

    @FunctionalInterface
    interface SqlIntSupplier {

        int get() throws SQLException;
    }

    private <T> T readSilent(ResultSet rs, SqlSupplier<T> supplier) {
        return readSilent(rs, supplier, true);
    }

    private <T> T readSilent(ResultSet rs, SqlSupplier<T> supplier, boolean fullWarning) {
        try {
            final T result = supplier.get();
            if (rs.wasNull()) {
                return null;
            } else {
                return result;
            }
        } catch (SQLException sqle) {
            // ignore, just return null
            if (fullWarning) {
                LOGGER.warn(sqle, "Unable to read column metadata: " + sqle.getMessage());
            } else {
                LOGGER.info("Metadata not supported: " + sqle.getMessage());
            }
        }
        return null;
    }

    @Override
    public String getTableCat() {
        return tableCat;
    }

    @Override
    public String getTableSchem() {
        return tableSchem;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public int getDataType() {
        return dataType.getValue();
    }

    @Override
    public boolean isDataTypeNull() {
        return dataType.isNull();
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public int getColumnSize() {
        return columnSize.getValue();
    }

    @Override
    public boolean isColumnSizeNull() {
        return columnSize.isNull();
    }

//    @Override
//    public Void getBufferLength() {
//        return bufferLength;
//    }
    @Override
    public int getDecimalDigits() {
        return decimalDigits.getValue();
    }

    @Override
    public boolean isDecimalDigitsNull() {
        return decimalDigits.isNull();
    }

    @Override
    public int getNumPrecRadix() {
        return numPrecRadix.getValue();
    }

    @Override
    public boolean isNumPrecRadixNull() {
        return numPrecRadix.isNull();
    }

    @Override
    public int getNullable() {
        return nullable.getValue();
    }

    @Override
    public boolean isNullableNull() {
        return nullable.isNull();
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public String getColumnDef() {
        return columnDef;
    }

//    @Override
//    public int getSqlDataType() {
//        return sqlDataType.getValue();
//    }
//
//    @Override
//    public boolean isSqlDataTypeNull() {
//        return sqlDataType.isNull();
//    }
//
//    @Override
//    public int getSqlDatetimeSub() {
//        return sqlDatetimeSub.getValue();
//    }
//
//    @Override
//    public boolean isSqlDatetimeSubNull() {
//        return sqlDatetimeSub.isNull();
//    }
    @Override
    public int getCharOctetLength() {
        return charOctetLength.getValue();
    }

    @Override
    public boolean isCharOctetLengthNull() {
        return charOctetLength.isNull();
    }

    @Override
    public int getOrdinalPosition() {
        return ordinalPosition.getValue();
    }

    @Override
    public boolean isOrdinalPositionNull() {
        return ordinalPosition.isNull();
    }

    @Override
    public String getScopeCatalog() {
        return scopeCatalog;
    }

    @Override
    public String getScopeSchema() {
        return scopeSchema;
    }

    @Override
    public String getScopeTable() {
        return scopeTable;
    }

    @Override
    public short getSourceDataType() {
        return (short) sourceDataType.getValue();
    }

    @Override
    public boolean isSourceDataTypeNull() {
        return sourceDataType.isNull();
    }

    @Override
    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    @Override
    public String getIsGeneratedcolumn() {
        return isGeneratedcolumn;
    }

    /*
    TABLE_CAT String => table catalog (may be null)
TABLE_SCHEM String => table schema (may be null)
TABLE_NAME String => table name
COLUMN_NAME String => column name
DATA_TYPE int => SQL type from java.sql.Types
TYPE_NAME String => Data source dependent type name, for a UDT the type name is fully qualified
COLUMN_SIZE int => column size.
BUFFER_LENGTH is not used.
DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
NUM_PREC_RADIX int => Radix (typically either 10 or 2)
NULLABLE int => is NULL allowed.
columnNoNulls - might not allow NULL values
columnNullable - definitely allows NULL values
columnNullableUnknown - nullability unknown
REMARKS String => comment describing column (may be null)
COLUMN_DEF String => default value for the column, which should be interpreted as a string when the value is enclosed in single quotes (may be null)
SQL_DATA_TYPE int => unused
SQL_DATETIME_SUB int => unused
CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
ORDINAL_POSITION int => index of column in table (starting at 1)
IS_NULLABLE String => ISO rules are used to determine the nullability for a column.
YES --- if the column can include NULLs
NO --- if the column cannot include NULLs
empty string --- if the nullability for the column is unknown
SCOPE_CATALOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
SCOPE_TABLE String => table name that this the scope of a reference attribute (null if the DATA_TYPE isn't REF)
SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or user-generated REF)
IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
YES --- if the column is auto incremented
NO --- if the column is not auto incremented
empty string --- if it cannot be determined whether the column is auto incremented
IS_GENERATEDCOLUMN String => Indicates whether this is a generated column
YES --- if this a generated column
NO --- if this not a generated column
empty string --- if it cannot be determined whether this is a generated column
     */
}
