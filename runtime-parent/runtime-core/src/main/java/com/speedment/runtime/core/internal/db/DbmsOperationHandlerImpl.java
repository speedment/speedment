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

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.manager.sql.SqlDeleteStatement;
import com.speedment.runtime.core.internal.manager.sql.SqlInsertStatement;
import com.speedment.runtime.core.internal.manager.sql.SqlUpdateStatement;
import com.speedment.runtime.core.manager.sql.HasGeneratedKeys;
import com.speedment.runtime.core.manager.sql.SqlStatement;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.core.internal.db.SqlQueryLoggerUtil.logOperation;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

final class DbmsOperationHandlerImpl implements DbmsOperationHandler {

    private static final int INITIAL_RETRY_COUNT = 5;
    private static final Logger LOGGER = LoggerManager.getLogger(DbmsOperationHandlerImpl.class);
    private static final Logger LOGGER_PERSIST = LoggerManager.getLogger(ApplicationBuilder.LogType.PERSIST.getLoggerName());
    private static final Logger LOGGER_UPDATE = LoggerManager.getLogger(ApplicationBuilder.LogType.UPDATE.getLoggerName());
    private static final Logger LOGGER_REMOVE = LoggerManager.getLogger(ApplicationBuilder.LogType.REMOVE.getLoggerName());
    private static final Logger LOGGER_SQL_RETRY = LoggerManager.getLogger(ApplicationBuilder.LogType.SQL_RETRY.getLoggerName());

    private final ConnectionPoolComponent connectionPoolComponent;
    private final TransactionComponent transactionComponent;
    private final SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler;
    private final SqlConsumer<PreparedStatement> preparedStatementConfigurator;
    private final SqlConsumer<ResultSet> resultSetConfigurator;
    private SqlTriConsumer<Dbms, Connection, HasGeneratedKeys> insertHandler;
    private final AtomicBoolean closed;

    DbmsOperationHandlerImpl(
        final ConnectionPoolComponent connectionPoolComponent,
        final TransactionComponent transactionComponent,
        final SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler,
        final SqlConsumer<PreparedStatement> preparedStatementConfigurator,
        final SqlConsumer<ResultSet> resultSetConfigurator,
        final SqlTriConsumer<Dbms, Connection, HasGeneratedKeys> insertHandler
    ) {
        this.connectionPoolComponent = requireNonNull(connectionPoolComponent);
        this.transactionComponent = requireNonNull(transactionComponent);
        this.generatedKeysHandler = ofNullable(generatedKeysHandler).orElse(this::defaultGeneratedKeys);
        this.preparedStatementConfigurator = ofNullable(preparedStatementConfigurator).orElse(ps -> {});
        this.resultSetConfigurator = ofNullable(resultSetConfigurator).orElse(rs -> {});
        this.insertHandler = ofNullable(insertHandler).orElse(this::defaultInsertHandler);
        closed = new AtomicBoolean();
    }

    public void close() {
        closed.set(true);
    }

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);
        assertNotClosed();

        final ConnectionInfo connectionInfo = new ConnectionInfo(dbms, connectionPoolComponent, transactionComponent);
        try (
            final PreparedStatement ps = connectionInfo.connection().prepareStatement(sql, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY)) {
            configureSelect(ps);
            connectionInfo.ifNotInTransaction(c -> c.setAutoCommit(false));
            try {
                int i = 1;
                for (final Object o : values) {
                    ps.setObject(i++, o);
                }
                try (final ResultSet rs = ps.executeQuery()) {
                    configureSelect(rs);

                    // Todo: Make a transparent stream with closeHandler added.
                    final Stream.Builder<T> streamBuilder = Stream.builder();
                    while (rs.next()) {
                        streamBuilder.add(rsMapper.apply(rs));
                    }
                    return streamBuilder.build();
                }
            } finally {
                connectionInfo.ifNotInTransaction(Connection::commit);
            }
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Error querying " + sql);
            throw new SpeedmentException(sqle);
        } finally {
            closeQuietly(connectionInfo::close);
        }
    }

    @Override
    public <T> AsynchronousQueryResult<T> executeQueryAsync(
        final Dbms dbms,
        final String sql,
        final List<?> values,
        final SqlFunction<ResultSet, T> rsMapper,
        final ParallelStrategy parallelStrategy
    ) {
        assertNotClosed();
        return new AsynchronousQueryResultImpl<>(
            sql,
            values,
            rsMapper,
            () -> new ConnectionInfo(dbms, connectionPoolComponent, transactionComponent),
            parallelStrategy,
            this::configureSelect,
            this::configureSelect
        );
    }

    @Override
    public <ENTITY> void executeInsert(Dbms dbms, String sql, List<?> values, Collection<Field<ENTITY>> generatedKeyFields, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
        logOperation(LOGGER_PERSIST, sql, values);
        final SqlInsertStatement sqlUpdateStatement = new SqlInsertStatement(sql, values, new ArrayList<>(generatedKeyFields), generatedKeyConsumer);
        execute(dbms, sqlUpdateStatement);
    }

    @Override
    public void executeUpdate(Dbms dbms, String sql, List<?> values) throws SQLException {
        logOperation(LOGGER_UPDATE, sql, values);
        final SqlUpdateStatement sqlUpdateStatement = new SqlUpdateStatement(sql, values);
        execute(dbms, sqlUpdateStatement);
    }

    @Override
    public void executeDelete(Dbms dbms, String sql, List<?> values) throws SQLException {
        logOperation(LOGGER_REMOVE, sql, values);
        final SqlDeleteStatement sqlDeleteStatement = new SqlDeleteStatement(sql, values);
        execute(dbms, sqlDeleteStatement);
    }

    @Override
    public Clob createClob(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createClob);
    }

    @Override
    public Blob createBlob(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createBlob);
    }

    @Override
    public NClob createNClob(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createNClob);
    }

    @Override
    public SQLXML createSQLXML(Dbms dbms) throws SQLException {
        return applyOnConnection(dbms, Connection::createSQLXML);
    }

    @Override
    public Array createArray(Dbms dbms, String typeName, Object[] elements) throws SQLException {
        assertNotClosed();
        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
            return connection.createArrayOf(typeName, elements);
        }
    }

    @Override
    public Struct createStruct(Dbms dbms, String typeName, Object[] attributes) throws SQLException {
        assertNotClosed();
        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
            return connection.createStruct(typeName, attributes);
        }
    }

    @Override
    public void configureSelect(ResultSet resultSet) throws SQLException {
        resultSetConfigurator.accept(resultSet);
    }

    @Override
    public void configureSelect(PreparedStatement statement) throws SQLException {
        preparedStatementConfigurator.accept(statement);
    }

    private void execute(Dbms dbms, SqlStatement sqlStatement) throws SQLException {
        final ConnectionInfo connectionInfo = new ConnectionInfo(dbms, connectionPoolComponent, transactionComponent);
        if (connectionInfo.isInTransaction()) {
            executeInTransaction(dbms, connectionInfo.connection(), sqlStatement);
        } else {
            executeNotInTransaction(dbms, connectionInfo.connection(), sqlStatement);
        }
    }

    private void executeNotInTransaction(
        final Dbms dbms,
        final Connection conn,
        final SqlStatement sqlStatement
    ) throws SQLException {
        requireNonNull(dbms);
        requireNonNull(conn);
        requireNonNull(sqlStatement);

        assertNotClosed();
        int retryCount = INITIAL_RETRY_COUNT;
        boolean complete = false;
        do {
            try {
                conn.setAutoCommit(false);
                executeSqlStatement(sqlStatement, dbms, conn);
                conn.commit();
                conn.close();
                complete = true;
            } catch (SQLException sqlEx) {
                if (retryCount < INITIAL_RETRY_COUNT) {
                    LOGGER_SQL_RETRY.error("SqlStatementList: " + sqlStatement);
                    LOGGER_SQL_RETRY.error("SQL: " + sqlStatement);
                    LOGGER_SQL_RETRY.error(sqlEx, sqlEx.getMessage());
                }

                final String sqlState = sqlEx.getSQLState();

                if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
                    retryCount--;
                } else {
                    throw sqlEx; // Finally will be executed...
                }
            } finally {
                cleanup(conn, complete);
            }
        } while (!complete && (retryCount > 0));

        if (complete) {
            postSuccessfulTransaction(sqlStatement);
        }
    }

    private void cleanup(Connection conn, boolean transactionCompleted) throws SQLException {
        if (!transactionCompleted) {
            try {
                // If we got here the transaction should be rolled back, as not
                // all work has been done
                conn.rollback();
            } catch (SQLException sqlEx) {
                // If we got an exception here, something
                // pretty serious is going on
                throw new SpeedmentException("Rollback error! connection:", sqlEx);
            } finally {
                conn.close();
            }
        }
    }

    private void executeInTransaction(
        final Dbms dbms,
        final Connection conn,
        final SqlStatement sqlStatement
    ) throws SQLException {
        requireNonNull(dbms);
        requireNonNull(conn);
        requireNonNull(sqlStatement);

        assertNotClosed();
        executeSqlStatement(sqlStatement, dbms, conn);
        postSuccessfulTransaction(sqlStatement);
    }

    private void handleSqlStatement(Connection conn, SqlUpdateStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(conn, sqlStatement);
    }

    private void handleSqlStatement(Connection conn, SqlDeleteStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(conn, sqlStatement);
    }

    private void handleSqlStatementHelper(Connection conn, SqlStatement sqlStatement) throws SQLException {
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.NO_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();
        }
    }

    private void postSuccessfulTransaction(SqlStatement sqlStatement) {
        if (sqlStatement instanceof SqlInsertStatement) {
            final SqlInsertStatement sqlInsertStatement = (SqlInsertStatement)sqlStatement;
            sqlInsertStatement.notifyGeneratedKeyListener();
        }
    }

    private void executeSqlStatement(SqlStatement sqlStatement, Dbms dbms, Connection conn) throws SQLException {
        assertNotClosed();
        switch (sqlStatement.getType()) {
            case INSERT:
                insert((SqlInsertStatement) sqlStatement, dbms, conn);
                break;
            case UPDATE:
                update((SqlUpdateStatement) sqlStatement, dbms, conn);
                break;
            case DELETE:
                delete((SqlDeleteStatement) sqlStatement, dbms, conn);
                break;
        }
    }

    private void delete(SqlDeleteStatement sqlStatement, Dbms dbms, Connection conn) throws SQLException {
        final SqlDeleteStatement s = sqlStatement;
        handleSqlStatement(conn, s);
    }

    private void update(SqlUpdateStatement sqlStatement, Dbms dbms, Connection conn) throws SQLException {
        final SqlUpdateStatement s = sqlStatement;
        handleSqlStatement(conn, s);
    }

    private void insert(SqlInsertStatement sqlStatement, Dbms dbms, Connection conn) throws SQLException {
        final SqlInsertStatement s = sqlStatement;
        insertHandler.accept(dbms,conn,s);
    }

    private <T> T applyOnConnection(Dbms dbms, SqlFunction<Connection, T> mapper) throws SQLException {
        assertNotClosed();
        try (final Connection c = connectionPoolComponent.getConnection(dbms)) {
            return mapper.apply(c);
        }
    }

    private void assertNotClosed() {
        if (closed.get()) {
            throw new IllegalStateException(
                "The " + DbmsOperationHandler.class.getSimpleName() + " " +
                    getClass().getSimpleName() + " has been closed."
            );
        }
    }

    private interface ThrowingClosable {
        void close() throws Exception;
    }

     private void closeQuietly(ThrowingClosable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    private void handleGeneratedKeys(PreparedStatement ps, LongConsumer longConsumer) throws SQLException {
        generatedKeysHandler.accept(ps, longConsumer);
    }

    private void defaultGeneratedKeys(PreparedStatement ps, LongConsumer longConsumer) throws SQLException {
        try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
            while (generatedKeys.next()) {
                longConsumer.accept(generatedKeys.getLong(1));
            }
        }
    }

    private void defaultInsertHandler(Dbms dbms, Connection conn, HasGeneratedKeys sqlStatement) throws SQLException {
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();

            handleGeneratedKeys(ps, sqlStatement::addGeneratedKey);
        }
    }

}