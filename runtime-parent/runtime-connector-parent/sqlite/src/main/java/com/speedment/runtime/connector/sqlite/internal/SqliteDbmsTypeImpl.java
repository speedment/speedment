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
package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.injector.annotation.OnlyIfMissing;
import com.speedment.runtime.connector.sqlite.SqliteMetadataHandler;
import com.speedment.runtime.connector.sqlite.SqliteOperationHandler;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.db.metadata.TypeInfoMetaData;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Objects.requireNonNull;

/**
 * Implementation of {@link DbmsType} for the SQLite database type.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SqliteDbmsTypeImpl implements DbmsType {

    static final String SQLITE = "SQLite";

    private final DriverComponent drivers; // Nullable
    private final SqliteMetadataHandler metadataHandler;
    private final SqliteOperationHandler operationHandler;

    @Inject
    @OnlyIfMissing(DriverComponent.class)
    public SqliteDbmsTypeImpl(
        final SqliteMetadataHandler metadataHandler,
        final ConnectionPoolComponent connectionPoolComponent,
        final TransactionComponent transactionComponent
    ) {
        this.drivers          = null; // Nullable
        this.metadataHandler  = requireNonNull(metadataHandler);
        this.operationHandler = new SqliteOperationHandlerImpl(connectionPoolComponent, transactionComponent);
    }

    @Inject
    public SqliteDbmsTypeImpl(
        final DriverComponent drivers,
        final SqliteMetadataHandler metadataHandler,
        final ConnectionPoolComponent connectionPoolComponent,
        final TransactionComponent transactionComponent
    ) {
        this.drivers          = requireNonNull(drivers);
        this.metadataHandler  = requireNonNull(metadataHandler);
        this.operationHandler = new SqliteOperationHandlerImpl(connectionPoolComponent, transactionComponent);
    }

    @ExecuteBefore(State.STOPPED)
    public void close() {
        operationHandler.close();
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
        return drivers != null && drivers.driver(getDriverName()).isPresent();
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
