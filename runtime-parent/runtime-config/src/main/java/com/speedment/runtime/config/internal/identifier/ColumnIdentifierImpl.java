package com.speedment.runtime.config.internal.identifier;

import com.speedment.runtime.config.identifier.ColumnIdentifier;

import static java.util.Objects.requireNonNull;

/**
 * Default implementation of the {@link ColumnIdentifier}. Note that for
 * performance reasons, usually the interface is implemented using a generated
 * {@code enum} and not by this class. This implementation should only be used
 * when fully dynamic identifiers are required.
 * <p>
 * This implementation is immutable and may be used as key in a
 * {@link java.util.Map}.
 *
 * @author Emil Forslund
 * @since  3.0.15
 */
public final class ColumnIdentifierImpl<ENTITY>
implements ColumnIdentifier<ENTITY> {

    private final String dbmsName;
    private final String schemaName;
    private final String tableName;
    private final String columnName;

    public ColumnIdentifierImpl(
            final String dbmsName,
            final String schemaName,
            final String tableName,
            final String columnName) {
        this.dbmsName   = requireNonNull(dbmsName);
        this.schemaName = requireNonNull(schemaName);
        this.tableName  = requireNonNull(tableName);
        this.columnName = requireNonNull(columnName);
    }

    @Override
    public String getDbmsName() {
        return dbmsName;
    }

    @Override
    public String getSchemaName() {
        return schemaName;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnIdentifier)) return false;

        final ColumnIdentifier<?> that = (ColumnIdentifier<?>) o;
        return getDbmsName().equals(that.getDbmsName())
            && getSchemaName().equals(that.getSchemaName())
            && getTableName().equals(that.getTableName())
            && getColumnName().equals(that.getColumnName());
    }

    @Override
    public int hashCode() {
        int result = getDbmsName().hashCode();
        result = 31 * result + getSchemaName().hashCode();
        result = 31 * result + getTableName().hashCode();
        result = 31 * result + getColumnName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ColumnIdentifier{" +
            dbmsName + '.' +
            schemaName + '.' +
            tableName + '.' +
            columnName + '}';
    }
}
