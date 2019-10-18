package com.speedment.runtime.core.internal.db;

import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.db.*;
import com.speedment.runtime.core.manager.sql.HasGeneratedKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.LongConsumer;

import static java.util.Objects.requireNonNull;

public final class DbmsOperationalHandlerBuilderImpl implements DbmsOperationalHandlerBuilder {

    private final ConnectionPoolComponent connectionPoolComponent;
    private final TransactionComponent transactionComponent;

    private SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler;
    private SqlConsumer<PreparedStatement> preparedStatementConfigurator;
    private SqlConsumer<ResultSet> resultSetConfigurator;
    private SqlTriConsumer<Dbms, Connection, HasGeneratedKeys> insertHandler;

    public DbmsOperationalHandlerBuilderImpl(ConnectionPoolComponent connectionPoolComponent, TransactionComponent transactionComponent) {
        this.connectionPoolComponent = requireNonNull(connectionPoolComponent);
        this.transactionComponent = requireNonNull(transactionComponent);
        this.generatedKeysHandler = DbmsOperationHandlerImpl::defaultGeneratedKeys;
        this.preparedStatementConfigurator = ps -> {};
        this.resultSetConfigurator = rs -> {};
        this.insertHandler = null;
    }

    @Override
    public DbmsOperationalHandlerBuilder withGeneratedKeysHandler(SqlBiConsumer<PreparedStatement, LongConsumer> generatedKeysHandler) {
        this.generatedKeysHandler = requireNonNull(generatedKeysHandler);
        return this;
    }

    @Override
    public DbmsOperationalHandlerBuilder withConfigureSelectPreparedStatement(SqlConsumer<PreparedStatement> configurator) {
        this.preparedStatementConfigurator = requireNonNull(configurator);
        return this;
    }

    @Override
    public DbmsOperationalHandlerBuilder withConfigureSelectResultSet(SqlConsumer<ResultSet> configurator) {
        this.resultSetConfigurator = requireNonNull(configurator);
        return this;
    }

    @Override
    public DbmsOperationalHandlerBuilder withInsertHandler(SqlTriConsumer<Dbms, Connection, HasGeneratedKeys> insertHandler) {
        this.insertHandler = requireNonNull(insertHandler);
        return this;
    }

    @Override
    public DbmsOperationHandler build() {
        return new DbmsOperationHandlerImpl(
            connectionPoolComponent,
            transactionComponent,
            generatedKeysHandler,
            preparedStatementConfigurator,
            resultSetConfigurator
        );
    }
}
