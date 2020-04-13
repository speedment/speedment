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

import com.speedment.runtime.core.component.connectionpool.PoolableConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import static java.util.Objects.requireNonNull;

/**
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class MockPoolableConnection implements PoolableConnection {

    private final long created;
    private final Connection wrapped;
    private final List<Runnable> closeListeners;
    private final String uri;

    public MockPoolableConnection(Connection wrapped, String uri) {
        this.wrapped        = requireNonNull(wrapped);
        this.uri            = requireNonNull(uri);
        this.closeListeners = new ArrayList<>();
        this.created = System.currentTimeMillis();
    }


    @Override
    public void close() {
        // Do nothing. The connection is closed by the pool.
    }

    @Override
    public long getId() {
        return 1;
    }

    @Override
    public void rawClose() throws SQLException {
        onClose();
        wrapped.close();
    }

    @Override
    public long getCreated() {
        return created;
    }

    @Override
    public long getExpires() {
        return created + 1000 * 60 * 60 * 24;
    }

    @Override
    public String getUser() {
        return "root";
    }

    @Override
    public char[] getPassword() {
        return new char[0];
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void onClose() {
        closeListeners.forEach(Runnable::run);
    }

    @Override
    public void setOnClose(Runnable onClose) {
        closeListeners.add(onClose);
    }

    @Override
    public Statement createStatement() throws SQLException {return wrapped.createStatement();}

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {return wrapped.prepareStatement(sql);}

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {return wrapped.prepareCall(sql);}

    @Override
    public String nativeSQL(String sql) throws SQLException {return wrapped.nativeSQL(sql);}

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {wrapped.setAutoCommit(autoCommit);}

    @Override
    public boolean getAutoCommit() throws SQLException {return wrapped.getAutoCommit();}

    @Override
    public void commit() throws SQLException {wrapped.commit();}

    @Override
    public void rollback() throws SQLException {wrapped.rollback();}

    @Override
    public boolean isClosed() throws SQLException {return wrapped.isClosed();}

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {return wrapped.getMetaData();}

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {wrapped.setReadOnly(readOnly);}

    @Override
    public boolean isReadOnly() throws SQLException {return wrapped.isReadOnly();}

    @Override
    public void setCatalog(String catalog) throws SQLException {wrapped.setCatalog(catalog);}

    @Override
    public String getCatalog() throws SQLException {return wrapped.getCatalog();}

    @Override
    public void setTransactionIsolation(int level) throws SQLException {wrapped.setTransactionIsolation(level);}

    @Override
    public int getTransactionIsolation() throws SQLException {return wrapped.getTransactionIsolation();}

    @Override
    public SQLWarning getWarnings() throws SQLException {return wrapped.getWarnings();}

    @Override
    public void clearWarnings() throws SQLException {wrapped.clearWarnings();}

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {return wrapped.createStatement(resultSetType, resultSetConcurrency);}

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {return wrapped.prepareStatement(sql, resultSetType, resultSetConcurrency);}

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {return wrapped.prepareCall(sql, resultSetType, resultSetConcurrency);}

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {return wrapped.getTypeMap();}

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {wrapped.setTypeMap(map);}

    @Override
    public void setHoldability(int holdability) throws SQLException {wrapped.setHoldability(holdability);}

    @Override
    public int getHoldability() throws SQLException {return wrapped.getHoldability();}

    @Override
    public Savepoint setSavepoint() throws SQLException {return wrapped.setSavepoint();}

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {return wrapped.setSavepoint(name);}

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {wrapped.rollback(savepoint);}

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {wrapped.releaseSavepoint(savepoint);}

    @Override
    public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {return wrapped.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);}

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {return wrapped.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);}

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {return wrapped.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);}

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {return wrapped.prepareStatement(sql, autoGeneratedKeys);}

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {return wrapped.prepareStatement(sql, columnIndexes);}

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {return wrapped.prepareStatement(sql, columnNames);}

    @Override
    public Clob createClob() throws SQLException {return wrapped.createClob();}

    @Override
    public Blob createBlob() throws SQLException {return wrapped.createBlob();}

    @Override
    public NClob createNClob() throws SQLException {return wrapped.createNClob();}

    @Override
    public SQLXML createSQLXML() throws SQLException {return wrapped.createSQLXML();}

    @Override
    public boolean isValid(int timeout) throws SQLException {return wrapped.isValid(timeout);}

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {wrapped.setClientInfo(name, value);}

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {wrapped.setClientInfo(properties);}

    @Override
    public String getClientInfo(String name) throws SQLException {return wrapped.getClientInfo(name);}

    @Override
    public Properties getClientInfo() throws SQLException {return wrapped.getClientInfo();}

    @Override
    public Array createArrayOf(String typeName, Object[] elements) throws SQLException {return wrapped.createArrayOf(typeName, elements);}

    @Override
    public Struct createStruct(String typeName, Object[] attributes) throws SQLException {return wrapped.createStruct(typeName, attributes);}

    @Override
    public void setSchema(String schema) throws SQLException {wrapped.setSchema(schema);}

    @Override
    public String getSchema() throws SQLException {return wrapped.getSchema();}

    @Override
    public void abort(Executor executor) throws SQLException {wrapped.abort(executor);}

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {wrapped.setNetworkTimeout(executor, milliseconds);}

    @Override
    public int getNetworkTimeout() throws SQLException {return wrapped.getNetworkTimeout();}

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return wrapped.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return wrapped.isWrapperFor(iface);
    }
}
