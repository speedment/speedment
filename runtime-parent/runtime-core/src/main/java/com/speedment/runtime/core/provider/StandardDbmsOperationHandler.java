package com.speedment.runtime.core.provider;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.AsynchronousQueryResult;
import com.speedment.runtime.core.db.DbmsOperationHandler;
import com.speedment.runtime.core.db.SqlBiConsumer;
import com.speedment.runtime.core.db.SqlFunction;
import com.speedment.runtime.core.internal.db.DbmsOperationHandlerImpl;
import com.speedment.runtime.core.stream.parallel.ParallelStrategy;
import com.speedment.runtime.field.Field;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.Stream;


public class StandardDbmsOperationHandler implements DbmsOperationHandler {

    private final DbmsOperationHandlerImpl inner;

    public StandardDbmsOperationHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent
    ) {
        inner = new DbmsOperationHandlerImpl(connectionPoolComponent, dbmsHandlerComponent, transactionComponent);
    }

    public StandardDbmsOperationHandler(
        final ConnectionPoolComponent connectionPoolComponent,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final TransactionComponent transactionComponent,
        final SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler
    ) {
        inner = new DbmsOperationHandlerImpl(connectionPoolComponent, dbmsHandlerComponent, transactionComponent, generatedKeysHandler);
    }

    @ExecuteBefore(State.STOPPED)
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

    @Override
    public void configureSelect(PreparedStatement statement) throws SQLException {
        inner.configureSelect(statement);
    }

    @Override
    public void configureSelect(ResultSet resultSet) throws SQLException {
        inner.configureSelect(resultSet);
    }
}
