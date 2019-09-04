package com.speedment.runtime.connector.sqlite;

import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;

import java.util.Optional;
import java.util.stream.Stream;

public final class MockTransactionComponent implements TransactionComponent {

    @Override
    public TransactionHandler createTransactionHandler() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> TransactionHandler createTransactionHandler(T dataSource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <D, T> void putDataSourceHandler(Class<D> dataSourceClass, DataSourceHandler<D, T> dataSourceHandler) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put(Thread thread, Object txObject) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Object> get(Thread thread) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Thread thread) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<Thread> threads(Object txObject) {
        throw new UnsupportedOperationException();
    }
}
