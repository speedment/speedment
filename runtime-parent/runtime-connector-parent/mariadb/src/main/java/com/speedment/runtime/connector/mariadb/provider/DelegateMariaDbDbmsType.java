/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.connector.mariadb.provider;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.connector.mariadb.MariaDbDbmsType;
import com.speedment.runtime.connector.mariadb.internal.MariaDbDbmsTypeImpl;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public final class DelegateMariaDbDbmsType implements MariaDbDbmsType {

    private final MariaDbDbmsTypeImpl inner;

    public DelegateMariaDbDbmsType(
        final DriverComponent driverComponent,
        @Config(name = "db.mysql.binaryCollationName", value = "utf8_bin")
        final String binaryCollationName,
        @Config(name = "db.mysql.collationName", value = "utf8_general_ci")
        final String collationName,
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final ProjectComponent projectComponent,
        final TransactionComponent transactionComponent
    ) {
        inner = new MariaDbDbmsTypeImpl(driverComponent, binaryCollationName, collationName, connectionPoolComponent, dbmsHandlerComponent, projectComponent, transactionComponent);
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
