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
package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.connector.sqlite.SqliteOperationHandler;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.DbmsOperationalHandlerBuilder;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The implementation of {@link DbmsOperationHandler} for SQLite databases.
 *
 * @author Emil Forslund
 * @since  3.1.10
 */
public final class SqliteOperationHandlerImpl implements SqliteOperationHandler {

    private final DbmsOperationHandler inner;

    public SqliteOperationHandlerImpl(
        final ConnectionPoolComponent connectionPoolComponent,
        final TransactionComponent transactionComponent
    ) {
        inner = DbmsOperationalHandlerBuilder.create(connectionPoolComponent, transactionComponent)
            .build();
    }

    @ExecuteBefore(State.STOPPED)
    @Override
    public void close() {
        inner.close();
    }

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper) {
        return inner.executeQuery(dbms, sql, values, rsMapper);
    }

    @Override
    public <T> AsynchronousQueryResult<T> executeQueryAsync(Dbms dbms, String sql, List<?> values, SqlFunction<ResultSet, T> rsMapper, ParallelStrategy parallelStrategy) {
        return inner.executeQueryAsync(dbms, sql, values, rsMapper, parallelStrategy);
    }

    @Override
    public <ENTITY> void executeInsert(Dbms dbms, String sql, List<?> values, Collection<Field<ENTITY>> generatedKeyFields, Consumer<List<Long>> generatedKeyConsumer) throws SQLException {
        inner.executeInsert(dbms, sql, values, generatedKeyFields, generatedKeyConsumer);
    }

    @Override
    public void executeUpdate(Dbms dbms, String sql, List<?> values) throws SQLException {
        inner.executeUpdate(dbms, sql, values);
    }

    @Override
    public void executeDelete(Dbms dbms, String sql, List<?> values) throws SQLException {
        inner.executeDelete(dbms, sql, values);
    }

    @Override
    public Clob createClob(Dbms dbms) throws SQLException {
        return inner.createClob(dbms);
    }

    @Override
    public Blob createBlob(Dbms dbms) throws SQLException {
        return inner.createBlob(dbms);
    }

    @Override
    public NClob createNClob(Dbms dbms) throws SQLException {
        return inner.createNClob(dbms);
    }

    @Override
    public SQLXML createSQLXML(Dbms dbms) throws SQLException {
        return inner.createSQLXML(dbms);
    }

    @Override
    public Array createArray(Dbms dbms, String typeName, Object[] elements) throws SQLException {
        return inner.createArray(dbms, typeName, elements);
    }

    @Override
    public Struct createStruct(Dbms dbms, String typeName, Object[] attributes) throws SQLException {
        return inner.createStruct(dbms, typeName, attributes);
    }

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, SqlFunction<ResultSet, T> rsMapper) {
        return inner.executeQuery(dbms, sql, rsMapper);
    }

    @Override
    public void configureSelect(PreparedStatement statement) throws SQLException {
        inner.configureSelect(statement);
    }

    @Override
    public void configureSelect(ResultSet resultSet) throws SQLException {
        inner.configureSelect(resultSet);
    }
}