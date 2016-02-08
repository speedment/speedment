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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.internal.core.db;

import com.speedment.db.AsynchronousQueryResult;
import com.speedment.exception.SpeedmentException;
import com.speedment.internal.logging.Logger;
import com.speedment.internal.logging.LoggerManager;
import com.speedment.internal.core.stream.StreamUtil;
import com.speedment.stream.ParallelStrategy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 *
 * @author pemi
 * @param <T> The type that the ResultSet shall be mapped to
 */
public final class AsynchronousQueryResultImpl<T> implements AsynchronousQueryResult<T> {

    private static final Logger LOGGER = LoggerManager.getLogger(AsynchronousQueryResultImpl.class);

    private String sql;
    private List<?> values;
    private Function<ResultSet, T> rsMapper;
    private final Supplier<Connection> connectionSupplier;
    private ParallelStrategy parallelStrategy;
    private Connection connection;
    private PreparedStatement ps;
    private ResultSet rs;
    private State state;

    public enum State {
        INIT, ESTABLISH, OPEN, CLOSED;
    }

    public AsynchronousQueryResultImpl(
        final String sql,
        final List<?> values,
        final Function<ResultSet, T> rsMapper,
        Supplier<Connection> connectionSupplier
    ) {
        setSql(sql); // requireNonNull in setter
        setValues(values);
        setRsMapper(rsMapper);
        this.connectionSupplier = requireNonNull(connectionSupplier);
        parallelStrategy = ParallelStrategy.DEFAULT;
        setState(State.INIT);
        debug();
    }

    @Override
    public Stream<T> stream() {
        setState(State.ESTABLISH);
        try {
            connection = connectionSupplier.get();
            ps = connection.prepareStatement(getSql());
            int i = 1;
            for (final Object o : getValues()) {
                ps.setObject(i++, o);
            }
            LOGGER.debug("sql:%s, values:%s", getSql(), getValues());
            rs = ps.executeQuery();
        } catch (SQLException sqle) {
            LOGGER.error(sqle, "Error executing " + getSql() + ", values=" + getValues());
            throw new SpeedmentException(sqle);
        }
        setState(State.OPEN);
        return StreamUtil.asStream(rs, getRsMapper(), parallelStrategy);
    }

    @Override
    public void close() {
        closeSilently(rs);
        closeSilently(ps);
        closeSilently(connection);
        setState(State.CLOSED);
    }

    protected void closeSilently(final AutoCloseable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (Exception e) {
            LOGGER.error(e, "Error closing " + closeable);
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
        return values;
    }

    @Override
    public void setValues(List<?> values) {
        this.values = requireNonNull(values);
    }

    @Override
    public Function<ResultSet, T> getRsMapper() {
        return rsMapper;
    }

    @Override
    public void setRsMapper(Function<ResultSet, T> rsMapper) {
        this.rsMapper = requireNonNull(rsMapper);
    }

    public State getState() {
        return state;
    }

    protected void setState(State state) {
        this.state = requireNonNull(state);
        debug();
    }

    private void debug() {
        // LOGGER.debug(this);
    }
    
        public ParallelStrategy getParallelStrategy() {
        return parallelStrategy;
    }

    public void setParallelStrategy(ParallelStrategy parallelStrategy) {
        this.parallelStrategy = parallelStrategy;
    }
    

}
