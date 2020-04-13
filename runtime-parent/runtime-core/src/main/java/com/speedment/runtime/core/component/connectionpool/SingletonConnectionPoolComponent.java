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
package com.speedment.runtime.core.component.connectionpool;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.util.DatabaseUtil;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static com.speedment.runtime.core.util.OptionalUtil.unwrap;
import static java.util.Objects.requireNonNull;

/**
 * Alternative implementation of {@link ConnectionPoolComponent} that uses a
 * semaphore to make sure that only one connection is able to communicate with
 * the database at a time. This is useful for file based databases where locking
 * is an issue.
 * <p>
 * To enable the {@code SingletonConnectionPoolComponent}, add the following to
 * the Speedment application builder:
 * <pre>{@code
 *     var app = new DemoApplicationBuilder()
 *         .withComponent(SingletonConnectionPoolComponent.class)
 *         .build();
 * }</pre>
 * It will automatically replace the default connection pool. There are two
 * modes in which the pool can work. It can either block until a connection
 * becomes available or throw an exception. To control this behaviour, use the
 * configuration flag {@code connectionpool.blocking=true}. The default value is
 * {@code false}, meaning that the pool will throw an exception instead of
 * blocking if multiple connections to the same {@link Dbms} are requested.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SingletonConnectionPoolComponent implements ConnectionPoolComponent {

    private static final Logger LOGGER_CONNECTION = LoggerManager.getLogger(
        ApplicationBuilder.LogType.CONNECTION.getLoggerName()
    );

    private final Map<String, SerializedConnectionManager> connectionManagers;

    private final DbmsHandlerComponent dbmsHandlerComponent;
    private final PasswordComponent passwordComponent;
    /**
     * Configuration parameter that controls if a request for a new connection
     * should be queued until one becomes available or if it should cause an
     * exception to be thrown if no connection is available.
     */
    private final boolean blocking;

    public SingletonConnectionPoolComponent(
        final DbmsHandlerComponent dbmsHandlerComponent,
        final PasswordComponent passwordComponent,
        @Config(name="connectionpool.blocking", value="false") final boolean blocking
    ) {
        this.dbmsHandlerComponent = requireNonNull(dbmsHandlerComponent);
        this.passwordComponent = requireNonNull(passwordComponent);
        this.blocking = blocking;
        connectionManagers = new ConcurrentHashMap<>();
    }

    @ExecuteBefore(State.STOPPED)
    public void closeOpenConnections() {
        connectionManagers.values()
            .forEach(SerializedConnectionManager::close);
    }

    private String makeKey(String uri, String user, char[] password) {
        requireNonNull(uri);
        // user nullable
        // password nullable
        return uri + user +
            ((password == null) ? "null" : new String(password));
    }

    @Override
    public PoolableConnection getConnection(Dbms dbms) {
        final String uri = DatabaseUtil.findConnectionUrl(dbmsHandlerComponent, dbms);
        final DbmsType type = DatabaseUtil.dbmsTypeOf(dbmsHandlerComponent, dbms);

        if (type.hasDatabaseUsers()) {
            final String username = unwrap(dbms.getUsername());
            final char[] password = unwrap(passwordComponent.get(dbms));
            return getConnection(uri, username, password);
        }

        return getConnection(uri, null, null);
    }

    @Override
    public PoolableConnection getConnection(String uri, String user, char[] password) {
        final String key = makeKey(uri, user, password);
        return connectionManagers.computeIfAbsent(key, k -> {
            try {
                final Connection conn2 = DriverManager.getConnection(
                    uri, user, password == null ? null : new String(password));
                return new SerializedConnectionManager(conn2, uri, user, password, blocking);
            } catch (SQLException ex) {
                throw new SpeedmentException(ex);
            }
        }).newConnection();
    }

    @Override
    public Connection newConnection(String uri, String user, char[] password) {
        return getConnection(uri, user, password);
    }

    @Override
    public Connection newConnection(Dbms dbms) {
        return getConnection(dbms);
    }

    @Override
    public void returnConnection(PoolableConnection poolableConnection) {
        // Do nothing.
    }

    @Override
    public int poolSize() {
        return (int) connectionManagers.values().stream()
            .mapToInt(man -> man.counter.get())
            .filter(c -> c == 0L)
            .count();
    }

    @Override
    public int leaseSize() {
        return connectionManagers.values().stream()
            .mapToInt(man -> man.counter.get())
            .sum();
    }

    @Override
    public int getMaxRetainSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long getMaxAge() {
        return Long.MAX_VALUE;
    }

    private static final class SerializedConnectionManager implements Closeable {

        private final Semaphore lock;
        private final Connection connection;
        private final String uri;
        private final String user;
        private final char[] password;
        private final AtomicInteger counter;
        private final boolean blocking;

        SerializedConnectionManager(Connection connection, String uri,
                                    String user, char[] password,
                                    boolean blocking) {
            this.lock       = new Semaphore(1);
            this.connection = requireNonNull(connection);
            this.uri        = requireNonNull(uri);
            this.user       = user;     // Nullable
            this.password   = password; // Nullable
            this.counter    = new AtomicInteger();
            this.blocking   = blocking;
        }

        PoolableConnection newConnection() {
            return new SerializedConnection(
                lock, connection, counter, uri, user, password, blocking);
        }

        @Override
        public void close() {
            try {
                if (connection.isClosed()) {
                    throw new SpeedmentException("Wrapped connection is already closed.");
                }
                connection.close();
            } catch (final SQLException ex) {
                throw new SpeedmentException(ex);
            }
        }
    }

    private static final class SerializedConnection implements PoolableConnection {

        private final long id;
        private final long created;
        private final Semaphore lock;
        private final Connection connection;
        private final AtomicInteger counter;
        private final String uri;
        private final String user;
        private final char[] password;
        private final boolean blocking;
        private boolean available;
        private boolean closed;
        private List<Runnable> onClose;

        SerializedConnection(Semaphore lock, Connection connection, AtomicInteger counter, String uri, String user, char[] password, boolean blocking) {
            this.id         = ThreadLocalRandom.current().nextLong();
            this.created    = System.currentTimeMillis();
            this.lock       = requireNonNull(lock);
            this.connection = requireNonNull(connection);
            this.counter    = requireNonNull(counter);
            this.uri        = requireNonNull(uri);
            this.user       = user;     // Nullable
            this.password   = password; // Nullable
            this.blocking   = blocking;
            this.available  = false;
            this.closed     = false;
            this.onClose = new ArrayList<>();
        }

        @Override
        public long getId() {
            return id;
        }

        @Override
        public void rawClose() { /* Do nothing.*/ }

        @Override
        public long getCreated() {
            return created;
        }

        @Override
        public long getExpires() {
            return Long.MAX_VALUE;
        }

        @Override
        public String getUser() {
            return user;
        }

        @Override
        public char[] getPassword() {
            return password;
        }

        @Override
        public String getUri() {
            return uri;
        }

        @Override
        public void onClose() {
            for (final Runnable action : onClose) {
                action.run();
            }
        }

        @Override
        public void setOnClose(Runnable runnable) {
            onClose.add(requireNonNull(runnable));
        }

        @Override
        public Statement createStatement() throws SQLException {
            blockUntilAvailable();
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            blockUntilAvailable();
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            blockUntilAvailable();
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            blockUntilAvailable();
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            if (!closed) { // Nothing to do.
                blockUntilAvailable();
                connection.setAutoCommit(autoCommit);
            }
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            blockUntilAvailable();
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            if (!closed) { // Nothing to do.
                blockUntilAvailable();
                connection.commit();
            }
        }

        @Override
        public void rollback() throws SQLException {
            blockUntilAvailable();
            connection.rollback();
        }

        @Override
        public void close() {
            if (!closed) {
                if (available) {
                    LOGGER_CONNECTION.debug("Releasing connection");
                    lock.release();
                    available = false;
                }

                closed = true;
                onClose();
                counter.decrementAndGet();
            }
        }

        @Override
        public boolean isClosed() {
            return closed;
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            blockUntilAvailable();
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            blockUntilAvailable();
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            blockUntilAvailable();
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            blockUntilAvailable();
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            blockUntilAvailable();
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            blockUntilAvailable();
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            blockUntilAvailable();
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            blockUntilAvailable();
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            blockUntilAvailable();
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            blockUntilAvailable();
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            blockUntilAvailable();
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            blockUntilAvailable();
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            blockUntilAvailable();
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            blockUntilAvailable();
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            blockUntilAvailable();
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            blockUntilAvailable();
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            blockUntilAvailable();
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            blockUntilAvailable();
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            blockUntilAvailable();
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            blockUntilAvailable();
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            blockUntilAvailable();
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            blockUntilAvailable();
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            blockUntilAvailable();
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            blockUntilAvailable();
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            blockUntilAvailable();
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            blockUntilAvailable();
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            blockUntilAvailable();
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            blockUntilAvailable();
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            blockUntilAvailable();
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            blockUntilAvailable();
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            blockUntilAvailable();
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            blockUntilAvailable();
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            blockUntilAvailable();
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            blockUntilAvailable();
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            blockUntilAvailable();
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            blockUntilAvailable();
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            blockUntilAvailable();
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            blockUntilAvailable();
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            blockUntilAvailable();
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            blockUntilAvailable();
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            blockUntilAvailable();
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            blockUntilAvailable();
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            blockUntilAvailable();
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            blockUntilAvailable();
            return connection.isWrapperFor(iface);
        }

        private void blockUntilAvailable() {
            if (closed) {
                throw new IllegalStateException("This connection has already been closed.");
            }

            if (!available) {
                LOGGER_CONNECTION.debug("Aquiring connection");
                try {
                    if (!lock.tryAcquire()) {
                        if (blocking) {
                            LOGGER_CONNECTION.warn("Waiting for connection to become available...");
                            lock.acquire();
                        } else {
                            throw new SpeedmentException(
                                "Error! No connection available. Try " +
                                "wrapping the expression in a transaction or " +
                                "enable the Speedment parameter " +
                                "'connectionpool.blocking=true'.");
                        }
                    }
                } catch (final InterruptedException ex) {
                    try {
                        throw new SpeedmentException(
                            "Interrupted while waiting for available connection.", ex);
                    } finally {
                        Thread.currentThread().interrupt();
                    }
                }
                this.counter.incrementAndGet();
                available = true;
            }
        }
    }
}
