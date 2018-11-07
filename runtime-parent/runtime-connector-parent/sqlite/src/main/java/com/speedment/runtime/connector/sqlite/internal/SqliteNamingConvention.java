package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.runtime.core.db.DatabaseNamingConvention;

import java.util.Set;

import static java.util.Collections.emptySet;

/**
 * @author Emil Forslund
 * @since  3.1.9
 */
public class SqliteNamingConvention implements DatabaseNamingConvention {

    @Override
    public String fullNameOf(String schemaName, String tableName, String columnName) {
        return tableName + "." + columnName;
    }

    @Override
    public String fullNameOf(String schemaName, String tableName) {
        return tableName;
    }

    @Override
    public String quoteField(String field) {
        return '\'' + field + '\'';
    }

    @Override
    public String encloseField(String field) {
        return '"' + field + '"';
    }

    @Override
    public Set<String> getSchemaExcludeSet() {
        return EXCLUDE_SET;
    }

    private final static Set<String> EXCLUDE_SET = emptySet();
}
