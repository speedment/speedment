package com.speedment.runtime.connector.sqlite.provider;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.connector.sqlite.SqliteOperationHandler;
import com.speedment.runtime.connector.sqlite.internal.SqliteOperationHandlerImpl;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.Stream;

public final class DelegateSqliteOperationalHandler implements SqliteOperationHandler {

    private final DbmsOperationHandler inner;

    public DelegateSqliteOperationalHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent
    ) {
        this.inner = new SqliteOperationHandlerImpl(connectionPoolComponent, dbmsHandlerComponent, transactionComponent);
    }

    @Override
    public <T> Stream<T> executeQuery(Dbms dbms, String sql, SqlFunction<ResultSet, T> rsMapper) {
        return inner.executeQuery(dbms, sql, rsMapper);
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
    public void configureSelect(PreparedStatement statement) throws SQLException {
        inner.configureSelect(statement);
    }

    @Override
    public void configureSelect(ResultSet resultSet) throws SQLException {
        inner.configureSelect(resultSet);
    }

    @Override
    public void handleGeneratedKeys(PreparedStatement ps, LongConsumer longConsumer) throws SQLException {
        inner.handleGeneratedKeys(ps, longConsumer);
    }
}
