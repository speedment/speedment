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
package com.speedment.runtime.core.internal.db;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.manager.sql.HasGeneratedKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.LongConsumer;

import static java.util.Objects.requireNonNull;

public final class DbmsOperationalHandlerBuilderImpl implements DbmsOperationalHandlerBuilder {

    private final ConnectionPoolComponent connectionPoolComponent;
    private final TransactionComponent transactionComponent;

    private SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler;
    private SqlConsumer<PreparedStatement> preparedStatementConfigurator;
    private SqlConsumer<ResultSet> resultSetConfigurator;
    private SqlTriConsumer<Dbms, Connection, HasGeneratedKeys> insertHandler;

    public DbmsOperationalHandlerBuilderImpl(ConnectionPoolComponent connectionPoolComponent, TransactionComponent transactionComponent) {
        this.connectionPoolComponent = requireNonNull(connectionPoolComponent);
        this.transactionComponent = requireNonNull(transactionComponent);
    }

    @Override
    public DbmsOperationalHandlerBuilder withGeneratedKeysHandler(SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler) {
        this.generatedKeysHandler = requireNonNull(generatedKeysHandler);
        return this;
    }

    @Override
    public DbmsOperationalHandlerBuilder withConfigureSelectPreparedStatement(SqlConsumer<PreparedStatement> configurator) {
        this.preparedStatementConfigurator = requireNonNull(configurator);
        return this;
    }

    @Override
    public DbmsOperationalHandlerBuilder withConfigureSelectResultSet(SqlConsumer<ResultSet> configurator) {
        this.resultSetConfigurator = requireNonNull(configurator);
        return this;
    }

    @Override
    public DbmsOperationalHandlerBuilder withInsertHandler(SqlTriConsumer<Dbms, Connection, HasGeneratedKeys> insertHandler) {
        this.insertHandler = requireNonNull(insertHandler);
        return this;
    }

    @Override
    public DbmsOperationHandler build() {
        return new DbmsOperationHandlerImpl(
            connectionPoolComponent,
            transactionComponent,
            generatedKeysHandler,
            preparedStatementConfigurator,
            resultSetConfigurator,
            insertHandler
        );
    }
}
