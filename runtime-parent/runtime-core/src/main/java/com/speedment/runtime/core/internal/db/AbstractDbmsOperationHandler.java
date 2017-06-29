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
package com.speedment.runtime.core.internal.db;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.ApplicationBuilder.LogType;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.manager.sql.SqlDeleteStatement;
import com.speedment.runtime.core.internal.manager.sql.SqlInsertStatement;
import com.speedment.runtime.core.internal.manager.sql.SqlStatement;
import com.speedment.runtime.core.internal.manager.sql.SqlUpdateStatement;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static com.speedment.common.invariant.NullUtil.requireNonNulls;
import static com.speedment.runtime.core.util.DatabaseUtil.dbmsTypeOf;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public abstract class AbstractDbmsOperationHandler implements DbmsOperationHandler {

    private static final Logger LOGGER = LoggerManager.getLogger(AbstractDbmsOperationHandler.class);
    protected static final Logger LOGGER_PERSIST = LoggerManager.getLogger(LogType.PERSIST.getLoggerName());
    protected static final Logger LOGGER_UPDATE = LoggerManager.getLogger(LogType.UPDATE.getLoggerName());
    protected static final Logger LOGGER_REMOVE = LoggerManager.getLogger(LogType.REMOVE.getLoggerName());

    public static final boolean SHOW_METADATA = false; // Warning: Enabling SHOW_METADATA will make some dbmses fail on metadata (notably Oracle) because all the columns must be read in order...

    @Inject
    private ConnectionPoolComponent connectionPoolComponent;
    @Inject
    private DbmsHandlerComponent dbmsHandlerComponent;

    protected AbstractDbmsOperationHandler() {
    }

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);

        try (
            final Connection connection = connectionPoolComponent.getConnection(dbms);
            final PreparedStatement ps = connection.prepareStatement(sql, java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY)) {
            configureSelect(ps);
            connection.setAutoCommit(false);
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
                connection.commit();
            }
        } catch (final SQLException sqle) {
            LOGGER.error(sqle, "Error querying " + sql);
            throw new SpeedmentException(sqle);
        }
    }

    @Override
    public <T> AsynchronousQueryResult<T> executeQueryAsync(
        Dbms dbms,
        String sql,
        List<?> values,
        SqlFunction<ResultSet, T> rsMapper,
        ParallelStrategy parallelStrategy) {

        return new AsynchronousQueryResultImpl<>(
            Objects.requireNonNull(sql),
            Objects.requireNonNull(values),
            Objects.requireNonNull(rsMapper),
            () -> connectionPoolComponent.getConnection(dbms),
            parallelStrategy,
            this::configureSelect,
            this::configureSelect
        );
    }

    @Override
    public <ENTITY> void executeInsert(Dbms dbms, String sql, List<?> values, Collection<Field<ENTITY>> generatedKeyFields, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
        logOperation(LOGGER_PERSIST, sql, values);
        final SqlInsertStatement<ENTITY> sqlUpdateStatement = new SqlInsertStatement<>(sql, values, generatedKeyFields, generatedKeyConsumer);
        execute(dbms, singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeUpdate(Dbms dbms, String sql, List<?> values) throws SQLException {
        logOperation(LOGGER_UPDATE, sql, values);
        final SqlUpdateStatement sqlUpdateStatement = new SqlUpdateStatement(sql, values);
        execute(dbms, singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeDelete(Dbms dbms, String sql, List<?> values) throws SQLException {
        logOperation(LOGGER_REMOVE, sql, values);
        final SqlDeleteStatement sqlDeleteStatement = new SqlDeleteStatement(sql, values);
        execute(dbms, singletonList(sqlDeleteStatement));
    }

    protected void logOperation(Logger logger, final String sql, final List<?> values) {
        logger.debug("%s, values:%s", sql, values);
    }

    protected void execute(Dbms dbms, List<? extends SqlStatement> sqlStatementList) throws SQLException {
        requireNonNull(sqlStatementList);
        int retryCount = 5;
        boolean transactionCompleted = false;

        do {
            SqlStatement lastSqlStatement = null;
            Connection conn = null;
            try {
                conn = connectionPoolComponent.getConnection(dbms);
                conn.setAutoCommit(false);
                for (final SqlStatement sqlStatement : sqlStatementList) {
                    lastSqlStatement = sqlStatement;
                    switch (sqlStatement.getType()) {
                        case INSERT: {
                            final SqlInsertStatement<?> s = (SqlInsertStatement<?>) sqlStatement;
                            handleSqlStatement(dbms, conn, s);
                            break;
                        }
                        case UPDATE: {
                            final SqlUpdateStatement s = (SqlUpdateStatement) sqlStatement;
                            handleSqlStatement(dbms, conn, s);
                            break;
                        }
                        case DELETE: {
                            final SqlDeleteStatement s = (SqlDeleteStatement) sqlStatement;
                            handleSqlStatement(dbms, conn, s);
                            break;
                        }
                    }

                }
                conn.commit();
                conn.close();
                transactionCompleted = true;
                conn = null;
            } catch (SQLException sqlEx) {
                LOGGER.error("SqlStatementList: " + sqlStatementList);
                LOGGER.error("SQL: " + lastSqlStatement);
                LOGGER.error(sqlEx, sqlEx.getMessage());
                final String sqlState = sqlEx.getSQLState();

                if ("08S01".equals(sqlState) || "40001".equals(sqlState)) {
                    retryCount--;
                } else {
                    retryCount = 0;
                    throw sqlEx; // Finally will be executed...
                }
            } finally {

                if (!transactionCompleted) {
                    try {
                        // If we got here, and conn is not null, the
                        // transaction should be rolled back, as not
                        // all work has been done
                        if (conn != null) {
                            try {
                                conn.rollback();
                            } finally {
                                conn.close();
                            }
                        }
                    } catch (SQLException sqlEx) {
                        //
                        // If we got an exception here, something
                        // pretty serious is going on, so we better
                        // pass it up the stack, rather than just
                        // logging it. . .
                        LOGGER.error(sqlEx, "Rollback error! connection:" + sqlEx.getMessage());
                        throw sqlEx;
                    }
                }
            }
        } while (!transactionCompleted && (retryCount > 0));

        if (transactionCompleted) {
            postSuccessfulTransaction(sqlStatementList);
        }
    }

    protected <ENTITY> void handleSqlStatement(Dbms dbms, Connection conn, SqlInsertStatement<ENTITY> sqlStatement) throws SQLException {
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();

            handleGeneratedKeys(ps, sqlStatement);
        }
    }

    @Override
    public <ENTITY> void handleGeneratedKeys(PreparedStatement ps, SqlInsertStatement<ENTITY> sqlStatement) throws SQLException {
        try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
            while (generatedKeys.next()) {
                sqlStatement.addGeneratedKey(generatedKeys.getLong(1));
            }
        }
    }

    protected void handleSqlStatement(Dbms dbms, Connection conn, SqlUpdateStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(conn, sqlStatement);
    }

    protected void handleSqlStatement(Dbms dbms, Connection conn, SqlDeleteStatement sqlStatement) throws SQLException {
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

    protected void postSuccessfulTransaction(List<? extends SqlStatement> sqlStatementList) {
        sqlStatementList.stream()
            .filter(SqlInsertStatement.class::isInstance)
            .map(SqlInsertStatement.class::cast)
            .forEach(SqlInsertStatement::acceptGeneratedKeys);
    }

    @FunctionalInterface
    protected interface TableChildMutator<T, U> {

        void mutate(T t, U u) throws SQLException;
    }

    protected String encloseField(Dbms dbms, String fieldName) {
        return dbmsTypeOf(dbmsHandlerComponent, dbms).getDatabaseNamingConvention().encloseField(fieldName);
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
        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
            return connection.createArrayOf(typeName, elements);
        }
    }

    @Override
    public Struct createStruct(Dbms dbms, String typeName, Object[] attributes) throws SQLException {
        try (final Connection connection = connectionPoolComponent.getConnection(dbms)) {
            return connection.createStruct(typeName, attributes);
        }
    }

    private <T> T applyOnConnection(Dbms dbms, SqlFunction<Connection, T> mapper) throws SQLException {
        try (final Connection c = connectionPoolComponent.getConnection(dbms)) {
            return mapper.apply(c);
        }
    }
}
