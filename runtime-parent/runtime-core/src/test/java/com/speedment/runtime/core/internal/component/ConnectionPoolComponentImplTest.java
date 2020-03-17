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
package com.speedment.runtime.core.internal.component;


import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionDecorator;
import com.speedment.runtime.core.component.connectionpool.PoolableConnection;
import com.speedment.runtime.core.db.DbmsType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author pemi
 */
final class ConnectionPoolComponentImplTest {

    private static final ConnectionDecorator CONNECTION_DECORATOR = connection -> {};
    private static final int MAX_AGE = 29_000;
    private static final int MAX_RETAIN_SIZE = 31;

    private ConnectionPoolComponentImpl instance;

    @BeforeEach
    void setUp() {

        // if ConnectionPoolComponentImpl is final, we could test with Mock or Proxy

        instance = new ConnectionPoolComponentImpl(
            CONNECTION_DECORATOR,
            new DummyDbmsHandlerComponent(),
            new DummyPasswordComponent(),
            MAX_AGE,
            MAX_RETAIN_SIZE
        ) {
            @Override
            public Connection newConnection(String uri, String user, char[] password) {
                return new DummyConnectionImpl(uri, user, password);
            }

        };
    }

    @Test
    void testGetConnection() throws Exception {
        String uri = "thecooldatabase";
        String user = "tryggve";
        char[] password = "arne".toCharArray();
        final PoolableConnection result = instance.getConnection(uri, user, password);
        assertNotNull(result);
    }

    @Test
    void testReturnConnection() throws Exception {
        String uri = "thecooldatabase";
        String user = "tryggve";
        char[] password = "arne".toCharArray();
        final PoolableConnection connection = instance.getConnection(uri, user, password);
        assertDoesNotThrow(() -> {
            instance.returnConnection(connection);
        });
    }

    @Test
    void testNewConnection() throws Exception {
        String uri = "someurl";
        String user = "a";
        char[] password = "b".toCharArray();
        Connection result = instance.newConnection(uri, user, password);
        assertNotNull(result);
        assertFalse(result.isClosed());
    }

    @Test
    void testGetMaxAge() {
        long result = instance.getMaxAge();
        assertTrue(result >= 0);
/*        instance.setMaxAge(60_000);*/
        assertEquals(MAX_AGE, instance.getMaxAge());
    }

/*    @Test
    void testSetMaxAge() {
        //instance.setMaxAge(40_000);
        assertEquals(40_000, instance.getMaxAge());
    }*/

    @Test
    void testGetPoolSize() {
        assertEquals(MAX_RETAIN_SIZE, instance.getMaxRetainSize());
    }

/*    // Leaking connections
    @Test
    @Disabled
    void testLeak() throws Exception {
        String uri = "thecooldatabase";
        String user = "tryggve";
        char[] password = "arne".toCharArray();

        for (int i = 0; i < 100; i++) {
            final PoolableConnection connection = instance.getConnection(uri, user, password);
            //connection.close();
            log(instance.leaseSize() + ", " + instance.poolSize());
            Thread.sleep(10_000);
        }
    }*/

    @Test
    void testMaxOutAndReturn() throws Exception {
        String uri = "thecooldatabase";
        String user = "tryggve";
        char[] password = "arne".toCharArray();
        /*instance.setMaxAge(60 * 60_000);
        instance.setMaxRetainSize(10);*/
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
/*
    *//**
     * Test of setPoolSize method, of class ConnectionPoolComponentImpl.
     *//*
    @Test
    void testSetPoolSize() {
        int poolSize = 40;
        instance.setMaxRetainSize(poolSize);
        assertEquals(instance.getMaxRetainSize(), 40);
    }*/

    private static final class DummyPasswordComponent implements PasswordComponent {
        @Override public void put(String dbmsName, char[] password) {}
        @Override public Optional<char[]> get(String dbmsName) { return Optional.empty(); }
    }

    private static final class DummyDbmsHandlerComponent implements DbmsHandlerComponent {
        @Override public void install(DbmsType dbmsType) {}
        @Override public Stream<DbmsType> supportedDbmsTypes() { return Stream.empty(); }
        @Override public Optional<DbmsType> findByName(String dbmsTypeName) { return Optional.empty(); }
    }

    private static final class DummyConnectionImpl implements Connection {

        final String uri;
        final String user;
        final char[] password;

        private boolean closed;

        public DummyConnectionImpl(String uri, String user, char[] password) {
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
            return false;
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void commit() throws SQLException {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    private static void log(String msg) {
        //System.out.println(new Timestamp(System.currentTimeMillis()) + " " + msg);
    }

}
