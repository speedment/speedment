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
import com.speedment.runtime.core.db.SqlConsumer;
import com.speedment.runtime.core.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author Per Minborg Since 3.0.17
 */
public final class ConnectionInfo {

    private final Connection connection;
    private final boolean inTransaction;

    public ConnectionInfo(
        final Dbms dbms,
        final ConnectionPoolComponent connectionPoolComponent,
        final TransactionComponent transactionComponent
    ) {
        if (transactionComponent != null) {
            final Optional<Object> txObject = transactionComponent.get(Thread.currentThread());
            if (txObject.isPresent()) {
                final Object o = txObject.get();
                if (o instanceof Connection) {
                    connection = (Connection) o;
                    inTransaction = true;
                } else {
                    throw new TransactionException(
                        String.format(
                            "A transaction object %s of type %s already exists but comes from antoher transaction domain. Not from %s",
                            o,
                            o.getClass(),
                            dbms
                        )
                    );
                }
            } else {
                connection = connectionPoolComponent.getConnection(dbms);
                inTransaction = false;
            }
        } else {
            connection = connectionPoolComponent.getConnection(dbms);
            inTransaction = false;
        }
    }

    /**
     * Only closes the connection if it is NOT from a transaction.
     *
     * @throws SQLException if there is a SQL error
     */
    public void close() throws SQLException {
        if (!inTransaction) {
            connection.close();
        }
    }

    public Connection connection() {
        return connection;
    }

    public boolean isInTransaction() {
        return inTransaction;
    }

    public void ifNotInTransaction(SqlConsumer<? super Connection> action) throws SQLException {
        if (!inTransaction) {
            action.accept(connection);
        }
    }

    public void ifInTransaction(SqlConsumer<? super Connection> action) throws SQLException {
        if (inTransaction) {
            action.accept(connection);
        }
    }

}