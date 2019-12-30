package com.speedment.plugins.enums.internal.ui;

import com.speedment.common.injector.annotation.Config;
import com.speedment.runtime.config.identifier.TableIdentifier;
import com.speedment.runtime.core.component.SqlAdapter;
import com.speedment.runtime.core.db.SqlFunction;

import java.sql.ResultSet;

import static java.util.Objects.requireNonNull;

final class SingleColumnSqlAdapter implements SqlAdapter<String> {

    private final TableIdentifier<String> tableId;
    private final String column;

    public SingleColumnSqlAdapter(
        @Config(name = "temp.dbms", value = "") final String dbms,
        @Config(name = "temp.schema", value = "") final String schema,
        @Config(name = "temp.table", value = "") final String table,
        @Config(name = "temp.column", value = "") final String column
    ) {
        this.column = requireNonNull(column);
        this.tableId = TableIdentifier.of(
            requireNonNull(dbms),
            requireNonNull(schema),
            requireNonNull(table)
        );
    }

    @Override
    public TableIdentifier<String> identifier() {
        return tableId;
    }

    @Override
    public SqlFunction<ResultSet, String> entityMapper() {
        return in -> in.getString(column);
    }

    @Override
    public SqlFunction<ResultSet, String> entityMapper(int offset) {
        return entityMapper(); // We do not use index and offset
    }

}
