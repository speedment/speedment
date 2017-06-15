/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.component;

import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.connectionpool.PoolableConnection;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.pool.PoolableConnectionImpl;
import com.speedment.runtime.core.util.DatabaseUtil;
import static com.speedment.runtime.core.util.OptionalUtil.unwrap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import static java.util.Objects.requireNonNull;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * A fully concurrent implementation of a connection pool.
 *
 * @author Per Minborg
 */
public class ConnectionPoolComponentImpl implements ConnectionPoolComponent {

    protected static final Logger LOGGER_CONNECTION = LoggerManager.getLogger(
        ApplicationBuilder.LogType.CONNECTION.getLoggerName()
    );

    @Config(name = "connectionpool.maxAge", value = "30000")
    private long maxAge;
    @Config(name = "connectionpool.maxRetainSize", value = "32")
    private int maxRetainSize;

    private final Map<Long, PoolableConnection> leasedConnections;
    private final Map<String, Deque<PoolableConnection>> pools;

    @Inject
    private DbmsHandlerComponent dbmsHandlerComponent;
    @Inject
    private PasswordComponent passwordComponent;

    public ConnectionPoolComponentImpl() {
        pools = new ConcurrentHashMap<>();
        leasedConnections = new ConcurrentHashMap<>();
    }

    @Override
    public PoolableConnection getConnection(Dbms dbms) {
        final String uri = DatabaseUtil.findConnectionUrl(dbmsHandlerComponent, dbms);
        final String username = unwrap(dbms.getUsername());
        final char[] password = unwrap(passwordComponent.get(dbms));

        return getConnection(uri, username, password);
    }

    @Override
    public PoolableConnection getConnection(
        final String uri,
        final String user,
        final char[] password
    ) {
        requireNonNull(uri);
        // user nullable
        // password nullable
        LOGGER_CONNECTION.debug("getConnection(%s, %s, *****)", uri, user);
        final String key = makeKey(uri, user, password);
        final Deque<PoolableConnection> q = acquireDeque(key);
        final PoolableConnection reusedConnection = pollValidOrNull(q);
        if (reusedConnection != null) {
            LOGGER_CONNECTION.debug("Reuse Connection: %s", reusedConnection);
            return lease(reusedConnection);
        } else {
            final Connection newRawConnection = newConnection(uri, user, password);
            final PoolableConnection newConnection = new PoolableConnectionImpl(uri, user, password, newRawConnection, System.currentTimeMillis() + getMaxAge());
            newConnection.setOnClose(() -> returnConnection(newConnection));
            LOGGER_CONNECTION.debug("New Connection: %s", newConnection);
            return lease(newConnection);
        }
    }

    @Override
    public Connection newConnection(final Dbms dbms) {
        final String uri = DatabaseUtil.findConnectionUrl(dbmsHandlerComponent, dbms);
        final String username = unwrap(dbms.getUsername());
        final char[] password = unwrap(passwordComponent.get(dbms));

        return newConnection(uri, username, password);
    }

    @Override
    public Connection newConnection(
        final String uri,
        final String username,
        final char[] password
    ) {
        try {
            final Connection connection = DriverManager.getConnection(uri, username, charsToString(password));
            LOGGER_CONNECTION.debug("New external connection: %s", connection);
            return connection;
        } catch (final SQLException ex) {
            final String msg = "Unable to get connection using url \"" + uri
                + "\", user = \"" + username
                + "\", password = \"********\".";

            LOGGER_CONNECTION.error(ex, msg);
            throw new SpeedmentException(msg, ex);
        }
    }

    @Override
    public void returnConnection(PoolableConnection connection) {
        requireNonNull(connection);
        leaseReturn(connection);
        if (!isValidOrNull(connection)) {
            discard(connection);
        } else {
            final String key = makeKey(connection);
            final Deque<PoolableConnection> q = acquireDeque(key);
            if (q.size() >= getMaxRetainSize()) {
                discard(connection);
            } else {
                LOGGER_CONNECTION.debug("Recycled: %s", connection);
                q.addFirst(connection);
            }
        }
    }

    private String charsToString(char[] chars) {
        return chars == null ? null : new String(chars);
    }

    private void discard(PoolableConnection connection) {
        requireNonNull(connection);
        LOGGER_CONNECTION.debug("Discard: %s", connection);
        try {
            connection.rawClose();
        } catch (SQLException sqle) {
            LOGGER_CONNECTION.error(sqle, "Error closing a connection.");
        }
    }

    private PoolableConnection lease(PoolableConnection poolableConnection) {
        leasedConnections.put(poolableConnection.getId(), poolableConnection);
        return poolableConnection;
    }

    private PoolableConnection leaseReturn(PoolableConnection poolableConnection) {
        leasedConnections.remove(poolableConnection.getId());
        return poolableConnection;
    }

    private boolean isValidOrNull(PoolableConnection connection) {
        // connection nullable
        try {
            return connection == null || (connection.getExpires() > System.currentTimeMillis() && !connection.isClosed());
        } catch (SQLException sqle) {
            LOGGER_CONNECTION.error(sqle, "Error while checking if a connection is closed.");
            return false;
        }
    }

    private PoolableConnection pollValidOrNull(Deque<PoolableConnection> q) {
        requireNonNull(q);
        PoolableConnection pc = q.pollLast();
        while (!isValidOrNull(pc)) {
            discard(pc); // If we discover an old connection, we discard it from the queue. Otherwise it will not be closed
            pc = q.pollLast();
        }
        return pc;
    }

    private String makeKey(PoolableConnection connection) {
        requireNonNull(connection);
        return makeKey(connection.getUri(), connection.getUser(), connection.getPassword());
    }

    private String makeKey(String uri, String user, char[] password) {
        requireNonNull(uri);
        // user nullable
        // password nullable
        return uri + Objects.toString(user) + ((password == null) ? "null" : new String(password));
    }

    private Deque<PoolableConnection> acquireDeque(String key) {
        requireNonNull(key);
        return pools.computeIfAbsent(key, $ -> new ConcurrentLinkedDeque<>());
    }

    @Override
    public int poolSize() {
        return pools
            .values()
            .stream()
            .mapToInt(Collection::size)
            .sum();
    }

    @Override
    public int leaseSize() {
        return leasedConnections.size();
    }

    @Override
    public long getMaxAge() {
        return maxAge;
    }

    @Override
    public void setMaxAge(long maxAge) {
        LOGGER_CONNECTION.warn("Unsafe method called. Use configuration parameters to set this value instead");
        this.maxAge = maxAge;
    }

    @Override
    public int getMaxRetainSize() {
        return maxRetainSize;
    }

    @Override
    public void setMaxRetainSize(int maxRetainSize) {
        LOGGER_CONNECTION.warn("Unsafe method called. Use configuration parameters to set this value instead");
        this.maxRetainSize = maxRetainSize;
    }

}
