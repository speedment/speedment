/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.connector.mysql.internal;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.provider.StandardDbmsOperationHandler;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.Stream;

/**
 *
 * @author Emil Forslund
 * @since 3.0.0
 */
public final class MySqlDbmsOperationHandler implements DbmsOperationHandler {

    private final StandardDbmsOperationHandler inner;

    public MySqlDbmsOperationHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent
    ) {
        inner = new StandardDbmsOperationHandler(connectionPoolComponent, dbmsHandlerComponent, transactionComponent);
    }

    // Begin: Changed from inner

    @Override
    public void configureSelect(PreparedStatement statement) throws SQLException {
        statement.setFetchSize(Integer.MIN_VALUE); // Enable streaming ResultSet
    }

    // End: Changed from inner

    @ExecuteBefore(State.STOPPED)
    public void close() {
        inner.close();
    }

    @Override
    public void configureSelect(ResultSet resultSet) throws SQLException {
        inner.configureSelect(resultSet);
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
    public void handleGeneratedKeys(PreparedStatement ps, LongConsumer longConsumer) throws SQLException {
        inner.handleGeneratedKeys(ps, longConsumer);
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

    public String encloseField(Dbms dbms, String fieldName) {
        return inner.encloseField(dbms, fieldName);
    }

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, SqlFunction<ResultSet, T> rsMapper) {
        return inner.executeQuery(dbms, sql, rsMapper);
    }
}
