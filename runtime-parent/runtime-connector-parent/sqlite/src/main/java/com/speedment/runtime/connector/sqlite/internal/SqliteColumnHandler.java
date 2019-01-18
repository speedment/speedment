package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.runtime.config.Column;
import com.speedment.runtime.core.db.DbmsColumnHandler;

import java.util.function.Predicate;

/**
 * Implementation of {@link DbmsColumnHandler} for SQLite databases.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SqliteColumnHandler implements DbmsColumnHandler {
    @Override
    public Predicate<Column> excludedInInsertStatement() {
        return Column::isAutoIncrement;
    }

    @Override
    public Predicate<Column> excludedInUpdateStatement() {
        return col -> false;
    }
}
