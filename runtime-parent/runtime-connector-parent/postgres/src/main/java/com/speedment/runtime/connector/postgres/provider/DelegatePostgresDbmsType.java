package com.speedment.runtime.connector.postgres.provider;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.connector.postgres.PostgresDbmsType;
import com.speedment.runtime.connector.postgres.internal.PostgresDbmsTypeImpl;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class DelegatePostgresDbmsType implements PostgresDbmsType {

    private final PostgresDbmsTypeImpl inner;

    public DelegatePostgresDbmsType(
        final DriverComponent driverComponent,
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ProjectComponent projectComponent,
        final TransactionComponent transactionComponent
    ) {
        inner = new PostgresDbmsTypeImpl(driverComponent, connectionPoolComponent, dbmsHandlerComponent, projectComponent, transactionComponent);
    }

    @ExecuteBefore(State.STOPPED)
    public void close() {
        inner.close();
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
    public ConnectionType getConnectionType() {
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
    public SkipLimitSupport getSkipLimitSupport() {
        return inner.getSkipLimitSupport();
    }

    @Override
    public String applySkipLimit(String originalSql, List<Object> params, long skip, long limit) {
        return inner.applySkipLimit(originalSql, params, skip, limit);
    }

    @Override
    public SubSelectAlias getSubSelectAlias() {
        return inner.getSubSelectAlias();
    }

    @Override
    public SortByNullOrderInsertion getSortByNullOrderInsertion() {
        return inner.getSortByNullOrderInsertion();
    }

}
