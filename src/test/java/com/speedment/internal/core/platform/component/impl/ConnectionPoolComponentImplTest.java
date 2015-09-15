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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.core.platform.component.impl;

import com.speedment.internal.core.pool.PoolableConnection;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author pemi
 */
public class ConnectionPoolComponentImplTest {

    ConnectionPoolComponentImpl instance;

    public ConnectionPoolComponentImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new ConnectionPoolComponentImpl() {

            @Override
            public Connection newConnection(String uri, String user, String password) throws SQLException {
                return new DummyConnectionImpl(uri, user, password);
            }

        };
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getConnection method, of class ConnectionPoolComponentImpl.
     */
    @Test
    public void testGetConnection() throws Exception {
        System.out.println("getConnection");
        String uri = "thecooldatabase";
        String user = "tryggve";
        String password = "arne";
        final PoolableConnection result = instance.getConnection(uri, user, password);
        assertNotNull(result);
    }

    /**
     * Test of returnConnection method, of class ConnectionPoolComponentImpl.
     */
    @Test
    public void testReturnConnection() throws Exception {
        System.out.println("returnConnection");
        String uri = "thecooldatabase";
        String user = "tryggve";
        String password = "arne";
        final PoolableConnection connection = instance.getConnection(uri, user, password);
        instance.returnConnection(connection);
    }

    /**
     * Test of newConnection method, of class ConnectionPoolComponentImpl.
     */
    @Test
    public void testNewConnection() throws Exception {
        System.out.println("newConnection");
        String uri = "someurl";
        String user = "a";
        String password = "b";
        Connection result = instance.newConnection(uri, user, password);
        assertNotNull(result);
        assertFalse(result.isClosed());
    }

    /**
     * Test of getMaxAge method, of class ConnectionPoolComponentImpl.
     */
    @Test
    public void testGetMaxAge() {
        System.out.println("getMaxAge");
        long result = instance.getMaxAge();
        assertTrue(result >= 0);
        instance.setMaxAge(60_000);
        assertEquals(60_000, instance.getMaxAge());
    }

    /**
     * Test of setMaxAge method, of class ConnectionPoolComponentImpl.
     */
    @Test
    public void testSetMaxAge() {
        System.out.println("setMaxAge");
        instance.setMaxAge(40_000);
        assertEquals(40_000, instance.getMaxAge());
    }

    /**
     * Test of getPoolSize method, of class ConnectionPoolComponentImpl.
     */
    @Test
    public void testGetPoolSize() {
        System.out.println("getPoolSize");
        final int result = instance.getMaxRetainSize();
        assertTrue(result >= 0);
        instance.setMaxRetainSize(10);
        assertEquals(10, instance.getMaxRetainSize());
    }

    // Leaking connections
    @Test
    @Ignore
    public void testLeak() throws Exception {
        System.out.println("leak");
        String uri = "thecooldatabase";
        String user = "tryggve";
        String password = "arne";

        for (int i = 0; i < 100; i++) {
            final PoolableConnection connection = instance.getConnection(uri, user, password);
            //connection.close();
            log(instance.leaseSize() + ", " + instance.poolSize());
            Thread.sleep(10_000);
        }
    }

    @Test
    public void testMaxOutAndReturn() throws Exception {
        System.out.println("maxOutAndReturn");
        String uri = "thecooldatabase";
        String user = "tryggve";
        String password = "arne";
        instance.setMaxAge(60 * 60_000);
        instance.setMaxRetainSize(10);
        long maxAge = instance.getMaxAge();
        int poolSize = instance.getMaxRetainSize();
        List<PoolableConnection> connections = new ArrayList<>();
        int loops = poolSize * 2;
        for (int i = 0; i < loops; i++) {
            final PoolableConnection connection = instance.getConnection(uri, user, password);
            connections.add(connection);
            assertEquals(i + 1, instance.leaseSize());
            assertEquals(0, instance.poolSize());
        }
        Collections.shuffle(connections);
        for (int i = loops - 1; i >= 0; i--) {
            connections.get(i).close();
            assertEquals(i, instance.leaseSize());
            assertEquals(Math.min(poolSize, loops - i), instance.poolSize());
        }
    }

    /**
     * Test of setPoolSize method, of class ConnectionPoolComponentImpl.
     */
    @Test
    public void testSetPoolSize() {
        System.out.println("setPoolSize");
        int poolSize = 40;
        instance.setMaxRetainSize(poolSize);
        assertEquals(instance.getMaxRetainSize(), 40);
    }

    private class DummyConnectionImpl implements Connection {

        final String uri;
        final String user;
        final String password;

        private boolean closed;

        public DummyConnectionImpl(String uri, String user, String password) {
            this.uri = uri;
            this.user = user;
            this.password = password;
        }

        @Override
        public Statement createStatement() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void commit() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void rollback() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void close() throws SQLException {
            if (isClosed()) {
                log("Closed twice");
            }
            closed = true;
        }

        @Override
        public boolean isClosed() throws SQLException {
            return closed;
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getCatalog() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void clearWarnings() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getHoldability() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Clob createClob() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Blob createBlob() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public NClob createNClob() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public String getSchema() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    }

    private void log(String msg) {
        System.out.println(new Timestamp(System.currentTimeMillis()) + " " + msg);
    }

}
