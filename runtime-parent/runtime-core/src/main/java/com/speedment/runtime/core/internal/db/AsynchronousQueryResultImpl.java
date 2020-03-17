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
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.SqlConsumer;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.exception.SpeedmentException;
import com.speedment.runtime.core.internal.stream.InternalStreamUtil;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> The type that the ResultSet shall be mapped to
 */
public final class AsynchronousQueryResultImpl<T> implements AsynchronousQueryResult<T> {

    private static final Logger LOGGER = LoggerManager.getLogger(AsynchronousQueryResultImpl.class);
    private static final Logger LOGGER_STREAM = LoggerManager.getLogger(ApplicationBuilder.LogType.STREAM.getLoggerName());

    private String sql;
    private List<?> values;
    private SqlFunction<ResultSet, T> rsMapper;
    private final Supplier<ConnectionInfo> connectionInfoSupplier;
    private final ParallelStrategy parallelStrategy;
    private final SqlConsumer<PreparedStatement> statementConfigurator;
    private final SqlConsumer<ResultSet> resultSetConfigurator;
    private ConnectionInfo connectionInfo;  // null allowed if the stream() method is not run
    private PreparedStatement ps;
    private ResultSet rs;
    private State state;

    public enum State {
        INIT, ESTABLISH, OPEN, CLOSED
    }

    public AsynchronousQueryResultImpl(
        final String sql,
        final List<?> values,
        final SqlFunction<ResultSet, T> rsMapper,
        final Supplier<ConnectionInfo> connectionSupplier,
        final ParallelStrategy parallelStrategy,
        final SqlConsumer<PreparedStatement> statementConfigurator,
        final SqlConsumer<ResultSet> resultSetConfigurator
    ) {
        setSql(sql); // requireNonNull in setter
        setValues(values); // requireNonNull in setter
        setRsMapper(rsMapper); // requireNonNull in setter
        this.connectionInfoSupplier = requireNonNull(connectionSupplier);
        this.parallelStrategy = requireNonNull(parallelStrategy);
        setState(State.INIT);
        this.statementConfigurator = requireNonNull(statementConfigurator);
        this.resultSetConfigurator = requireNonNull(resultSetConfigurator);
    }

    @Override
    public Stream<T> stream() {
        setState(State.ESTABLISH);
        try {
            SqlQueryLoggerUtil.logOperation(LOGGER_STREAM, getSql(), getValues());

            connectionInfo = connectionInfoSupplier.get();
            connectionInfo.ifNotInTransaction(c -> c.setAutoCommit(false)); // Streaming results must be autocommit false for PostgreSQL
            ps = connectionInfo.connection().prepareStatement(getSql(), java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
            statementConfigurator.accept(ps);

            //System.out.format("*** PreparedStatement: fetchDirection %d, fetchSize %d%n", ps.getFetchDirection(), ps.getFetchSize());

            int i = 1;
            for (final Object o : getValues()) {
                ps.setObject(i++, o);
            }
            rs = ps.executeQuery();
            resultSetConfigurator.accept(rs);

            //System.out.format("*** ResultSet: fetchDirection %d, fetchSize %d%n", rs.getFetchDirection(), rs.getFetchSize());

        } catch (SQLException sqle) {
            LOGGER.error(sqle, "Error executing " + getSql() + ", values=" + getValues());
            throw new SpeedmentException(sqle);
        }
        setState(State.OPEN);
        return InternalStreamUtil.asStream(rs, getRsMapper(), parallelStrategy);
    }

    @Override
    public void close() {
        closeSilently(rs);
        closeSilently(ps);
        commitSilently(connectionInfo);
        actionSilently(connectionInfo, ConnectionInfo::close, "closing");
        setState(State.CLOSED);
    }

    private void commitSilently(ConnectionInfo connectionInfo) {
        try {
            if (connectionInfo != null) {
                connectionInfo.ifNotInTransaction(c -> c.setAutoCommit(true));
            }
        } catch (SQLException e) {
            LOGGER.error(e, "Failed to commit connection upon close");
        }
    }

    private void closeSilently(final AutoCloseable closeable) {
        actionSilently(closeable, AutoCloseable::close, "closing");
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            LOGGER.error(e, "Error closing " + closeable);
            // Just log the error. No re-throw
        }
    }

    @FunctionalInterface
    private interface ThrowingConsumer<T>  {
        void accept(T t) throws Exception;
    }

    private <T> void actionSilently(final T actionTarget, ThrowingConsumer<T> action, String actionLabel) {
        try {
            if (actionTarget != null) {
                action.accept(actionTarget);
            }
        } catch (Exception e) {
            LOGGER.error(e, "Error " + actionLabel + " " + actionTarget);
            // Just log the error. No re-throw
        }
    }


    @Override
    public String toString() {
        return getState() + " \"" + getSql() + "\" <- " + getValues();
    }

    @Override
    public String getSql() {
        return sql;
    }

    @Override
    public void setSql(String sql) {
        this.sql = requireNonNull(sql);
    }

    @Override
    public List<?> getValues() {
        return values; // Intentionally expose the modifiable collection here. 
    }

    @Override
    public void setValues(List<?> values) {
        this.values = requireNonNull(values);
    }

    @Override
    public SqlFunction<ResultSet, T> getRsMapper() {
        return rsMapper;
    }

    @Override
    public void setRsMapper(SqlFunction<ResultSet, T> rsMapper) {
        this.rsMapper = requireNonNull(rsMapper);
    }

    private State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = requireNonNull(state);
    }
}
