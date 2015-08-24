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
package com.speedment.core.platform.component.impl;

import com.speedment.core.platform.component.ConnectionPoolComponent;
import com.speedment.core.pool.PoolableConnection;
import com.speedment.core.pool.impl.PoolableConnectionImpl;
import com.speedment.logging.Logger;
import com.speedment.logging.LoggerManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
 * @author pemi
 */
public class ConnectionPoolComponentImpl implements ConnectionPoolComponent {

    private Logger logger;

    private final long DEFAULT_MAX_AGE = 5_000;
    private final int DEFAULT_MAX_POOL_SIZE_PER_DB = 32;

    private long maxAge;
    private int poolSize;

    private final Map<String, Deque<PoolableConnection>> pools;

    public ConnectionPoolComponentImpl() {
        maxAge = DEFAULT_MAX_AGE;
        poolSize = DEFAULT_MAX_POOL_SIZE_PER_DB;
        pools = new ConcurrentHashMap<>();
    }

    @Override
    public PoolableConnection getConnection(String uri, String user, String password) throws SQLException {
        final String key = makeKey(uri, user, password);
        final Deque<PoolableConnection> q = acquireDeque(key);
        final PoolableConnection reusedConnection = pollValidOrNull(q);
        if (reusedConnection != null) {
            return reusedConnection;
        } else {
            final Connection newRawConnection = DriverManager.getConnection(uri, user, password);
            final PoolableConnection newConnection = new PoolableConnectionImpl(uri, user, password, newRawConnection, System.currentTimeMillis() + getMaxAge());
            newConnection.setOnClose(() -> returnConnection(newConnection));
            return newConnection;
        }

    }

    @Override
    public void returnConnection(PoolableConnection connection) {
        if (!isValidOrNull(connection)) {
            discard(connection);
        } else {
            final String key = makeKey(connection);
            final Deque<PoolableConnection> q = acquireDeque(key);
            if (q.size() >= getPoolSize()) {
                discard(connection);
            } else {
                q.addFirst(connection);
            }
        }
    }

    private void discard(PoolableConnection connection) {
        try {
            connection.rawClose();
        } catch (SQLException sqle) {
            getLogger().error(sqle, "Error closing a connection.");
        }
    }

    private boolean isValidOrNull(PoolableConnection connection) {
        try {
            return connection == null || (connection.getExpires() > System.currentTimeMillis() && !connection.isClosed());
        } catch (SQLException sqle) {
            getLogger().error(sqle, "Error while checking if a connection is closed.");
            return false;
        }
    }

    private PoolableConnection pollValidOrNull(Deque<PoolableConnection> q) {
        PoolableConnection pc = q.pollLast();
        while (!isValidOrNull(pc)) {
            pc = q.pollLast();
        }
        return pc;
    }

    private String makeKey(PoolableConnection connection) {
        return makeKey(connection.getUri(), connection.getUser(), connection.getPassword());
    }

    private String makeKey(String uri, String user, String password) {
        return uri + user + password;
    }

    private Deque<PoolableConnection> acquireDeque(String key) {
        return pools.computeIfAbsent(key, $ -> new ConcurrentLinkedDeque<>());
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
    public int getPoolSize() {
        return poolSize;
    }

    @Override
    public void setPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }

    private Logger getLogger() {
        if (logger == null) {
            logger = LoggerManager.getLogger(ConnectionPoolComponentImpl.class);
        }
        return logger;
    }

}
