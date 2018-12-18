package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.db.ConnectionUrlGenerator;
import com.speedment.runtime.core.db.DatabaseNamingConvention;
import com.speedment.runtime.core.db.DbmsColumnHandler;
import com.speedment.runtime.core.db.DbmsMetadataHandler;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.db.FieldPredicateView;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.speedment.common.injector.State.CREATED;
import static com.speedment.common.injector.State.INITIALIZED;
import static java.util.Collections.emptySet;

/**
 * Implementation of {@link DbmsType} for the SQLite database type.
 *
 * @author Emil Forslund
 * @since  3.1.9
 */
public final class SqliteDbmsType implements DbmsType {

    public final static String SQLITE = "SQLite";

    private @Inject SqliteMetadataHandler metadataHandler;
    private @Inject SqliteOperationHandler operationHandler;

    @ExecuteBefore(INITIALIZED)
    void install(@WithState(CREATED) DbmsHandlerComponent component) {
        component.install(this);
    }

    @Override
    public boolean hasSchemaNames() {
        return false;
    }

    @Override
    public boolean hasDatabaseNames() {
        return false;
    }

    @Override
    public boolean hasDatabaseUsers() {
        return false;
    }

    @Override
    public String getName() {
        return SQLITE;
    }

    @Override
    public String getDriverManagerName() {
        return "SQLite JDBC Driver";
    }

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.DBMS_AS_FILE;
    }

    @Override
    public int getDefaultPort() {
        return 0; // In SQLite, you connect to a file directly.
    }

    @Override
    public String getSchemaTableDelimiter() {
        return "";
    }

    @Override
    public String getDbmsNameMeaning() {
        return "The name of the file where data is persisted.";
    }

    @Override
    public Optional<String> getDefaultDbmsName() {
        return Optional.empty();
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public String getDriverName() {
        return "org.sqlite.JDBC";
    }

    @Override
    public DatabaseNamingConvention getDatabaseNamingConvention() {
        return new SqliteNamingConvention();
    }

    @Override
    public DbmsMetadataHandler getMetadataHandler() {
        return metadataHandler;
    }

    @Override
    public DbmsOperationHandler getOperationHandler() {
        return operationHandler;
    }

    @Override
    public DbmsColumnHandler getColumnHandler() {
        return new SqliteColumnHandler();
    }

    @Override
    public String getResultSetTableSchema() {
        throw new UnsupportedOperationException(
            "SQLite does not have concept of 'schemas', so this method " +
            "should not be invoked."
        );
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return new SqliteConnectionUrlGenerator();
    }

    @Override
    public FieldPredicateView getFieldPredicateView() {
        return new SqliteFieldPredicateView();
    }

    @Override
    public Set<TypeInfoMetaData> getDataTypes() {
        return emptySet();
    }

    @Override
    public String getInitialQuery() {
        return "SELECT 1";
    }

    @Override
    public SkipLimitSupport getSkipLimitSupport() {
        return SkipLimitSupport.STANDARD;
    }

    @Override
    public String applySkipLimit(String originalSql, List<Object> params, long skip, long limit) {
        if (skip == 0 && limit == Long.MAX_VALUE) {
            return originalSql;
        }

        final StringBuilder sb = new StringBuilder(originalSql);
        if (limit == Long.MAX_VALUE) {
            sb.append(" LIMIT 223372036854775807"); // Some big number that does not overflow
        } else {
            sb.append(" LIMIT ?");
            params.add(limit);
        }

        if (skip > 0) {
            sb.append(" OFFSET ?");
            params.add(skip);
        }

        return sb.toString();
    }

    @Override
    public SubSelectAlias getSubSelectAlias() {
        return SubSelectAlias.PROHIBITED;
    }

    @Override
    public SortByNullOrderInsertion getSortByNullOrderInsertion() {
        return SortByNullOrderInsertion.PRE;
    }
}
