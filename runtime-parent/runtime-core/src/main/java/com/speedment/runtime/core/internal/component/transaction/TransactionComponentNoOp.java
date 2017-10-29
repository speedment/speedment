package com.speedment.runtime.core.internal.component.transaction;

import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.TransactionBundle;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import java.util.Optional;

/**
 *
 * @author Per Minborg
 */
public class TransactionComponentNoOp implements TransactionComponent {

    public TransactionComponentNoOp() {
    }

    @Override
    public TransactionHandler transactionHandler() {
        throw createException();
    }

    @Override
    public <T> TransactionHandler transactionHandler(T dataSource) {
        throw createException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D, T> void putDataSourceHandler(Class<D> dataSourceClass, DataSourceHandler<D, T> dataSourceHandler) {
        throw createException();
    }

    @Override
    public void put(Thread thread, Object txObject) {
        throw createException();
    }

    @Override
    public Optional<Object> get(Thread thread) {
        return Optional.empty();
    }

    @Override
    public void remove(Thread thread) {
    }

    private UnsupportedOperationException createException() {
        return new UnsupportedOperationException("Transactions are not supported. Make sure you use the " + TransactionBundle.class + " in your project to support transactions");
    }

}
