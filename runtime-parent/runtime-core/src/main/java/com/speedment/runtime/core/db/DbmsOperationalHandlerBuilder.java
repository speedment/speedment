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
package com.speedment.runtime.core.db;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.internal.db.DbmsOperationalHandlerBuilderImpl;
import com.speedment.runtime.core.manager.sql.HasGeneratedKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.LongConsumer;

public interface DbmsOperationalHandlerBuilder {

    DbmsOperationalHandlerBuilder withGeneratedKeysHandler(SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler);

    DbmsOperationalHandlerBuilder withConfigureSelectPreparedStatement(SqlConsumer<PreparedStatement> configurator);

    DbmsOperationalHandlerBuilder withConfigureSelectResultSet(SqlConsumer<ResultSet> configurator);

    DbmsOperationalHandlerBuilder withInsertHandler(SqlTriConsumer<Dbms, Connection, HasGeneratedKeys> insertHandler);

    /**
     * Creates and returns a new DbmsOperationHandler.
     *
     * @return a new DbmsOperationHandler
     */
    DbmsOperationHandler build();

    /**
     * Creates and returns a new DbmsOperationalHandlerBuilder.
     * @param connectionPoolComponent to use
     * @param transactionComponent to use
     * @return a new DbmsOperationalHandlerBuilder
     *
     * @throws NullPointerException if any of the provided
     *         parameters are {@code null}
     */
    static  DbmsOperationalHandlerBuilder create(
        final ConnectionPoolComponent connectionPoolComponent,
        final TransactionComponent transactionComponent
    ) {
        return new DbmsOperationalHandlerBuilderImpl(connectionPoolComponent, transactionComponent);
    }

}
