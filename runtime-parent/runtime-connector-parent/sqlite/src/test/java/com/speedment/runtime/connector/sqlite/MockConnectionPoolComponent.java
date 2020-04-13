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
package com.speedment.runtime.connector.sqlite;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.connectionpool.PoolableConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class MockConnectionPoolComponent implements ConnectionPoolComponent {

    private PoolableConnection connection;

    public void setConnection(Connection connection, String uri) {
        this.connection = new MockPoolableConnection(connection, uri);
    }

    @Override
    public PoolableConnection getConnection(String uri, String username, char[] password) {
        return connection;
    }

    @Override
    public PoolableConnection getConnection(Dbms dbms) {
        return connection;
    }

    @Override
    public Connection newConnection(String uri, String username, char[] password) {
        return connection;
    }

    @Override
    public Connection newConnection(Dbms dbms) {
        return connection;
    }

    @Override
    public void returnConnection(PoolableConnection connection) {
        // Do nothing.
    }

    @Override
    public int poolSize() {
        return 1;
    }

    @Override
    public int leaseSize() {
        return 1;
    }

    @Override
    public int getMaxRetainSize() {
        return 1;
    }

    @Override
    public long getMaxAge() {
        return 1000 * 60 * 60 * 24;
    }

    @ExecuteBefore(State.STOPPED)
    void closeConnections() throws SQLException {
        connection.rawClose();
    }
}
