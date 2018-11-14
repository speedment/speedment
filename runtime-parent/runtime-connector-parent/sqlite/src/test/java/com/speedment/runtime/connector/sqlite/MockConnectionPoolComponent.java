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
 * @since  3.1.9
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
