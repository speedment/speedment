package com.speedment.plugins.enums.internal.ui;

import com.speedment.runtime.config.identifier.ColumnIdentifier;

import static java.util.Objects.requireNonNull;

final class TempColumnIdentifier implements ColumnIdentifier<String> {
    private final String dbms;
    private final String schema;
    private final String table;
    private final String column;

    TempColumnIdentifier(
        final String dbms,
        final String schema,
        final String table,
        final String column
    ) {
        this.dbms = requireNonNull(dbms);
        this.schema = requireNonNull(schema);
        this.table = requireNonNull(table);
        this.column = requireNonNull(column);
    }

    @Override
    public String getDbmsId() {
        return dbms;
    }

    @Override
    public String getTableId() {
        return table;
    }

    @Override
    public String getColumnId() {
        return column;
    }

    @Override
    public String getSchemaId() {
        return schema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnIdentifier)) return false;

        final ColumnIdentifier<?> that = (ColumnIdentifier<?>) o;
        return dbms.equals(that.getDbmsId())
            && schema.equals(that.getSchemaId())
            && table.equals(that.getTableId())
            && column.equals(that.getColumnId());
    }

    @Override
    public int hashCode() {
        int result = dbms.hashCode();
        result = 31 * result + schema.hashCode();
        result = 31 * result + table.hashCode();
        result = 31 * result + column.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TempColumnIdentifier{" +
            "dbms='" + dbms + '\'' +
            ", schema='" + schema + '\'' +
            ", table='" + table + '\'' +
            ", column='" + column + '\'' +
            '}';
    }
}
