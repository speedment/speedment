/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.internal.core.platform.component.impl;

import com.speedment.internal.core.platform.component.ConnectionPoolComponent;
import com.speedment.internal.core.pool.PoolableConnection;
import com.speedment.internal.core.pool.impl.PoolableConnectionImpl;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import static java.util.Objects.requireNonNull;

/**
 * A fully concurrent implementation of a connection pool.
 *
 * @author pemi
 */
public class ConnectionPoolComponentImpl implements ConnectionPoolComponent {

    private final Logger logger = LoggerManager.getLogger(ConnectionPoolComponentImpl.class);

    private final long DEFAULT_MAX_AGE = 30_000;
    private final int DEFAULT_MIN_POOL_SIZE_PER_DB = 32;

    private long maxAge;
    private int maxRetainSize;

    private final Map<Long, PoolableConnection> leasedConnections;
    private final Map<String, Deque<PoolableConnection>> pools;

    public ConnectionPoolComponentImpl() {
        maxAge = DEFAULT_MAX_AGE;
        maxRetainSize = DEFAULT_MIN_POOL_SIZE_PER_DB;
        pools = new ConcurrentHashMap<>();
        leasedConnections = new ConcurrentHashMap<>();
    }

    @Override
    public PoolableConnection getConnection(String uri, String user, String password) throws SQLException {
        requireNonNull(uri);
        // user nullable
        // password nullable
        logger.debug("getConnection(" + uri + ", " + user);
        final String key = makeKey(uri, user, password);
        final Deque<PoolableConnection> q = acquireDeque(key);
        final PoolableConnection reusedConnection = pollValidOrNull(q);
        if (reusedConnection != null) {
            logger.debug("Reuse Connection:" + reusedConnection);
            return lease(reusedConnection);
        } else {
            final Connection newRawConnection = newConnection(uri, user, password);
            final PoolableConnection newConnection = new PoolableConnectionImpl(uri, user, password, newRawConnection, System.currentTimeMillis() + getMaxAge());
//            getLogger().info("Created connection " + newConnection.getId() + " (" + leasedConnections.size() + ")");
            newConnection.setOnClose(() -> returnConnection(newConnection));
            logger.debug("New Connection:" + newConnection);
            return lease(newConnection);
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
                logger.debug("Recycled:" + connection);
                q.addFirst(connection);
            }
        }
    }

    @Override
    public Connection newConnection(String uri, String user, String password) throws SQLException {
        return DriverManager.getConnection(uri, user, password);
    }

    private void discard(PoolableConnection connection) {
        requireNonNull(connection);
        logger.debug("Discard:" + connection);
        try {
            connection.rawClose();
//            getLogger().info("Discarded connection " + connection.getId() + " (" + leasedConnections.size() + ")");
        } catch (SQLException sqle) {
            getLogger().error(sqle, "Error closing a connection.");
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
            getLogger().error(sqle, "Error while checking if a connection is closed.");
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

    private String makeKey(String uri, String user, String password) {
        requireNonNull(uri);
        // user nullable
        // password nullable
        return uri + Objects.toString(user) + Objects.toString(password);
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
        this.maxAge = maxAge;
    }

    @Override
    public int getMaxRetainSize() {
        return maxRetainSize;
    }

    @Override
    public void setMaxRetainSize(int maxRetainSize) {
        this.maxRetainSize = maxRetainSize;
    }

    private Logger getLogger() {
        return logger;
    }

}
