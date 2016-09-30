package com.speedment.runtime.config.internal.identifier;

import com.speedment.runtime.config.identifier.TableIdentifier;

/**
 *
 * @author Per Minborg
 */
public final class TableIdentifierImpl<ENTITY> implements TableIdentifier<ENTITY> {

    private final String dbmsName, schemaName, tableName;

    public TableIdentifierImpl(String dbmsName, String schemaName, String tableName) {
        this.dbmsName = dbmsName;
        this.schemaName = schemaName;
        this.tableName = tableName;
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

}
