package com.speedment.runtime.connector.sqlite.provider;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.OnlyIfMissing;
import com.speedment.runtime.connector.sqlite.SqliteDbmsType;
import com.speedment.runtime.connector.sqlite.SqliteMetadataHandler;
import com.speedment.runtime.connector.sqlite.SqliteOperationHandler;
import com.speedment.runtime.connector.sqlite.internal.SqliteDbmsTypeImpl;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class DelegateSqliteDbmsType implements SqliteDbmsType {

    private final DbmsType inner;

    @Inject
    @OnlyIfMissing(DriverComponent.class)
    public DelegateSqliteDbmsType(
        final SqliteMetadataHandler metadataHandler,
        final SqliteOperationHandler operationHandler
    ) {
        inner = new SqliteDbmsTypeImpl(metadataHandler, operationHandler);
    }

    @Inject
    public DelegateSqliteDbmsType(
        final DriverComponent driverComponent,
        final SqliteMetadataHandler metadataHandler,
        final SqliteOperationHandler operationHandler
    ) {
        inner = new SqliteDbmsTypeImpl(driverComponent, metadataHandler, operationHandler);
    }

    @Override
    public String getName() {
        return inner.getName();
    }

    @Override
    public String getDriverManagerName() {
        return inner.getDriverManagerName();
    }

    @Override
    public int getDefaultPort() {
        return inner.getDefaultPort();
    }

    @Override
    public String getDbmsNameMeaning() {
        return inner.getDbmsNameMeaning();
    }

    @Override
    public String getDriverName() {
        return inner.getDriverName();
    }

    @Override
    public DbmsMetadataHandler getMetadataHandler() {
        return inner.getMetadataHandler();
    }

    @Override
    public DbmsOperationHandler getOperationHandler() {
        return inner.getOperationHandler();
    }

    @Override
    public ConnectionUrlGenerator getConnectionUrlGenerator() {
        return inner.getConnectionUrlGenerator();
    }

    @Override
    public DatabaseNamingConvention getDatabaseNamingConvention() {
        return inner.getDatabaseNamingConvention();
    }

    @Override
    public FieldPredicateView getFieldPredicateView() {
        return inner.getFieldPredicateView();
    }

    @Override
    public boolean isSupported() {
        return inner.isSupported();
    }

    @Override
    public String getSchemaTableDelimiter() {
        return inner.getSchemaTableDelimiter();
    }

    @Override
    public Optional<String> getDefaultSchemaName() {
        return inner.getDefaultSchemaName();
    }

    @Override
    public boolean hasSchemaNames() {
        return inner.hasSchemaNames();
    }

    @Override
    public boolean hasDatabaseNames() {
        return inner.hasDatabaseNames();
    }

    @Override
    public boolean hasDatabaseUsers() {
        return inner.hasDatabaseUsers();
    }

    @Override
    public DbmsTypeDefault.ConnectionType getConnectionType() {
        return inner.getConnectionType();
    }

    @Override
    public DbmsColumnHandler getColumnHandler() {
        return inner.getColumnHandler();
    }

    @Override
    public String getResultSetTableSchema() {
        return inner.getResultSetTableSchema();
    }

    @Override
    public String getInitialQuery() {
        return inner.getInitialQuery();
    }

    @Override
    public Optional<String> getDefaultDbmsName() {
        return inner.getDefaultDbmsName();
    }

    @Override
    public Set<TypeInfoMetaData> getDataTypes() {
        return inner.getDataTypes();
    }

    @Override
    public SqlPredicateFragment getCollateFragment() {
        return inner.getCollateFragment();
    }

    @Override
    public DbmsTypeDefault.SkipLimitSupport getSkipLimitSupport() {
        return inner.getSkipLimitSupport();
    }

    @Override
    public String applySkipLimit(String originalSql, List<Object> params, long skip, long limit) {
        return inner.applySkipLimit(originalSql, params, skip, limit);
    }

    @Override
    public DbmsTypeDefault.SubSelectAlias getSubSelectAlias() {
        return inner.getSubSelectAlias();
    }

    @Override
    public DbmsTypeDefault.SortByNullOrderInsertion getSortByNullOrderInsertion() {
        return inner.getSortByNullOrderInsertion();
    }

}
