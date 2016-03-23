package com.speedment.db.metadata;

import com.speedment.internal.core.db.metadata.ColumnMetadataImpl;
import java.sql.ResultSet;

/**
 *
 * @author Per Minborg
 */
public interface ColumnMetadata {

    String getTableCat();

    String getTableSchem();

    String getTableName();

    String getColumnName();

    int getDataType();

    boolean isDataTypeNull();

    String getTypeName();

    int getColumnSize();

    boolean isColumnSizeNull();

    Void getBufferLength();

    int getDecimalDigits();

    boolean isDecimalDigitsNull();

    int getNumPrecRadix();

    boolean isNumPrecRadixNull();

    int getNullable();

    boolean isNullableNull();

    String getRemarks();

    String getColumnDef();

    int getSqlDataType();

    boolean isSqlDataTypeNull();

    int getSqlDatetimeSub();

    boolean isSqlDatetimeSubNull();

    int getCharOctetLength();

    boolean isCharOctetLengthNull();

    int getOrdinalPosition();

    boolean isOrdinalPositionNull();

    String getScopeCatalog();

    String getScopeSchema();

    String getScopeTable();

    short getSourceDataType();
    
    boolean isSourceDataTypeNull();

    String getIsAutoincrement();

    String getIsGeneratedcolumn();

    static ColumnMetadata of(ResultSet rs) {
        return new ColumnMetadataImpl(rs);
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
