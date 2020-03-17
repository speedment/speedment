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

import com.speedment.runtime.core.internal.db.metadata.ColumnMetaDataImpl;

import java.sql.ResultSet;

/**
 *
 * @author Per Minborg
 */
public interface ColumnMetaData {

    /**
     * Returns the table catalog (may be null).
     *
     * @return the table catalog (may be null)
     */
    String getTableCat();

    /**
     * Returns the table schema (may be null).
     *
     * @return the table schema (may be null)
     */
    String getTableSchem();

    /**
     * Returns the table name.
     *
     * @return the table name
     */
    String getTableName();

    /**
     * Returns the column name.
     *
     * @return the column name
     */
    String getColumnName();

    /**
     * Returns the SQL type from {@code java.sql.Types}.
     *
     * @return the SQL type from {@code java.sql.Types}
     */
    int getDataType();

    /**
     * Returns if the SQL type from {@code java.sql.Types} was null.
     *
     * @return if the SQL type from {@code java.sql.Types} was null
     */
    boolean isDataTypeNull();

    /**
     * Returns the Data source dependent type name, for a UDT the type name is
     * fully qualified.
     *
     * @return the Data source dependent type name, for a UDT the type name is
     * fully qualified
     */
    String getTypeName();

    /**
     * Returns the column size.
     *
     * @return the column size
     */
    int getColumnSize();

    /**
     * Returns if the column size was null.
     *
     * @return if the column size wass null
     */
    boolean isColumnSizeNull();

    /**
     * Returns the number of fractional digits. Null is returned for data types
     * where DECIMAL_DIGITS is not applicable.
     *
     * @return the number of fractional digits. Null is returned for data types
     * where DECIMAL_DIGITS is not applicable
     */
    int getDecimalDigits();

    /**
     * Returns if the number of fractional digits is not applicable.
     *
     * @return if the number of fractional digits is not applicable
     */
    boolean isDecimalDigitsNull();

    /**
     * Returns the Radix (typically either 10 or 2).
     *
     * @return the Radix (typically either 10 or 2)
     */
    int getNumPrecRadix();

    /**
     * Returns if the Radix was null.
     *
     * @return if the Radix was null
     */
    boolean isNumPrecRadixNull();

    /**
     * Return is NULL allowed value as defined in
     * {@link java.sql.DatabaseMetaData}
     *
     * @return is NULL allowed value
     * @see java.sql.DatabaseMetaData#columnNullable
     * @see java.sql.DatabaseMetaData#columnNullableUnknown
     * @see java.sql.DatabaseMetaData#columnNoNulls
     */
    int getNullable();

    /**
     * Return if nullable is NULL (should never happen).
     *
     * @return if nullable is NULL (should never happen)
     */
    boolean isNullableNull();

    /**
     * Return the comment describing column (may be null).
     *
     * @return the comment describing column (may be null)
     */
    String getRemarks();

    /**
     * Returns the default value for the column, which should be interpreted as
     * a string when the value is enclosed in single quotes (may be null).
     *
     * @return the default value for the column, which should be interpreted as
     * a string when the value is enclosed in single quotes (may be null)
     */
    String getColumnDef();

    /**
     * Return (for char types) the maximum number of bytes in the column.
     *
     * @return (for char types) the maximum number of bytes in the column
     */
    int getCharOctetLength();

    /**
     * Return if (for char types) the maximum number of bytes in the column was
     * null.
     *
     * @return if (for char types) the maximum number of bytes in the column was
     * null
     */
    boolean isCharOctetLengthNull();

    /**
     * Returns the ordinal position of column in the table (starting at 1).
     *
     * @return the ordinal position of column in the table (starting at 1)
     */
    int getOrdinalPosition();

    /**
     * Returns if the ordinal position of column in the table was null.
     *
     * @return if the ordinal position of column in the table was null
     */
    boolean isOrdinalPositionNull();

    /**
     * Return the catalog of table that is the scope of a reference attribute
     * (null if DATA_TYPE isn't REF).
     *
     * @return the catalog of table that is the scope of a reference attribute
     * (null if DATA_TYPE isn't REF)
     */
    String getScopeCatalog();

    /**
     * Return the schema of table that is the scope of a reference attribute
     * (null if the DATA_TYPE isn't REF).
     *
     * @return the schema of table that is the scope of a reference attribute
     * (null if the DATA_TYPE isn't REF)
     */
    String getScopeSchema();

    /**
     * Return the table name that this the scope of a reference attribute (null
     * if the DATA_TYPE isn't REF).
     *
     * @return the table name that this the scope of a reference attribute (null
     * if the DATA_TYPE isn't REF)
     */
    String getScopeTable();

    /**
     * Returns the source type of a distinct type or user-generated Ref type,
     * SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or
     * user-generated REF).
     *
     * @return the source type of a distinct type or user-generated Ref type,
     * SQL type from java.sql.Types (null if DATA_TYPE isn't DISTINCT or
     * user-generated REF)
     */
    short getSourceDataType();

    /**
     * Returns if the source type was null.
     *
     * @return if the source type was null
     * @see #getSourceDataType()
     */
    boolean isSourceDataTypeNull();

    /**
     * Returns whether this column is auto incremented.
     * <ul>
     * <li>
     * YES --- if the column is auto incremented
     * <li>
     * NO --- if the column is not auto incremented
     * <li>
     * empty string --- if it cannot be determined whether the column is auto
     * incremented
     * </ul>
     *
     * @return whether this column is auto incremented
     */
    String getIsAutoincrement();

    /**
     * Returns whether this is a generated column.
     * <ul>
     * <li>
     * YES --- if this a generated column
     * <li>
     * NO --- if this not a generated column
     * <li>
     * empty string --- if it cannot be determined whether this is a generated
     * column
     * <li>
     * null --- not supported for this database type/driver
     * </ul>
     *
     * @return whether this is a generated column
     */
    String getIsGeneratedcolumn();

    /**
     * Creates and returns a new ColumnMetadata from the given
     * {@link ResultSet}.
     *
     * @param rs with column metadata
     * @return a new ColumnMetadata from the given {@link ResultSet}
     */
    static ColumnMetaData of(ResultSet rs) {
        return new ColumnMetaDataImpl(rs);
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
