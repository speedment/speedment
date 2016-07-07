/**
 *
 * Copyright (c) 2006-2016, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.internal.db;

import com.speedment.common.injector.annotation.Inject;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.component.DbmsHandlerComponent;
import com.speedment.runtime.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.db.AsynchronousQueryResult;
import com.speedment.runtime.db.DbmsOperationHandler;
import com.speedment.runtime.db.SqlFunction;
import com.speedment.runtime.exception.SpeedmentException;
import com.speedment.runtime.field.trait.FieldTrait;
import com.speedment.runtime.field.trait.ReferenceFieldTrait;
import com.speedment.runtime.internal.manager.sql.SqlDeleteStatement;
import com.speedment.runtime.internal.manager.sql.SqlInsertStatement;
import com.speedment.runtime.internal.manager.sql.SqlStatement;
import com.speedment.runtime.internal.manager.sql.SqlUpdateStatement;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.runtime.internal.util.document.DocumentDbUtil.dbmsTypeOf;
import static com.speedment.runtime.util.NullUtil.requireNonNulls;
import static java.util.Collections.singletonList;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author  Per Minborg
 * @author  Emil Forslund
 */
public abstract class AbstractDbmsOperationHandler implements DbmsOperationHandler {

    private final static Logger LOGGER = LoggerManager.getLogger(AbstractDbmsOperationHandler.class);
    public static final boolean SHOW_METADATA = false; // Warning: Enabling SHOW_METADATA will make some dbmses fail on metadata (notably Oracle) because all the columns must be read in order...

    private @Inject ConnectionPoolComponent connectionPoolComponent;
    private @Inject DbmsHandlerComponent dbmsHandlerComponent;

    protected AbstractDbmsOperationHandler() {}

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper) {
        requireNonNulls(sql, values, rsMapper);

        try (
            final Connection connection = connectionPoolComponent.getConnection(dbms);
            final PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            for (final Object o : values) {
                ps.setObject(i++, o);
            }
            try (final ResultSet rs = ps.executeQuery()) {

                // Todo: Make a transparent stream with closeHandler added.
                final Stream.Builder<T> streamBuilder = Stream.builder();
                while (rs.next()) {
                    streamBuilder.add(rsMapper.apply(rs));
                }
                return streamBuilder.build();
            }
        } catch (SQLException sqle) {
            LOGGER.error(sqle, "Error querying " + sql);
            throw new SpeedmentException(sqle);
        }
    }

    @Override
    public <T> AsynchronousQueryResult<T> executeQueryAsync(
        Dbms dbms, String sql, List<?> values, Function<ResultSet, T> rsMapper) {

        return new AsynchronousQueryResultImpl<>(
            Objects.requireNonNull(sql),
            Objects.requireNonNull(values),
            Objects.requireNonNull(rsMapper),
            () -> connectionPoolComponent.getConnection(dbms)
        );
    }

    @Override
    public <F extends FieldTrait & ReferenceFieldTrait<?, ?, ?>> void executeInsert(Dbms dbms, String sql, List<?> values, List<F> generatedKeyFields, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
        final SqlInsertStatement sqlUpdateStatement = new SqlInsertStatement(sql, values, generatedKeyFields, generatedKeyConsumer);
        execute(dbms, singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeUpdate(Dbms dbms, String sql, List<?> values) throws SQLException {
        final SqlUpdateStatement sqlUpdateStatement = new SqlUpdateStatement(sql, values);
        execute(dbms, singletonList(sqlUpdateStatement));
    }

    @Override
    public void executeDelete(Dbms dbms, String sql, List<?> values) throws SQLException {
        final SqlDeleteStatement sqlDeleteStatement = new SqlDeleteStatement(sql, values);
        execute(dbms, singletonList(sqlDeleteStatement));
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
                            final SqlInsertStatement s = (SqlInsertStatement) sqlStatement;
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

    protected void handleSqlStatement(Dbms dbms, final Connection conn, final SqlInsertStatement sqlStatement) throws SQLException {
        try (final PreparedStatement ps = conn.prepareStatement(sqlStatement.getSql(), Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            for (Object o : sqlStatement.getValues()) {
                ps.setObject(i++, o);
            }
            ps.executeUpdate();

            try (final ResultSet generatedKeys = ps.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    sqlStatement.addGeneratedKey(generatedKeys.getLong(1));
                }
            }
        }
    }

    protected void handleSqlStatement(Dbms dbms, final Connection conn, final SqlUpdateStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(dbms, conn, sqlStatement);
    }

    protected void handleSqlStatement(Dbms dbms, final Connection conn, final SqlDeleteStatement sqlStatement) throws SQLException {
        handleSqlStatementHelper(dbms, conn, sqlStatement);
    }

    private void handleSqlStatementHelper(Dbms dbms, final Connection conn, final SqlStatement sqlStatement) throws SQLException {
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