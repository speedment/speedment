package com.speedment.runtime.core.internal.component.transaction;

import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.Transaction;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.exception.TransactionException;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public class TransactionImpl implements Transaction {

    private final TransactionComponent txComponent;
    private final Object txObject;
    private final DataSourceHandler<Object, Object> dataSourceHandler;

    public TransactionImpl(
        final TransactionComponent txComponent,
        final Object txObject,
        final DataSourceHandler<Object, Object> dataSourceHandler
    ) {
        this.txComponent = requireNonNull(txComponent);
        this.txObject = requireNonNull(txObject);
        this.dataSourceHandler = requireNonNull(dataSourceHandler);
    }

    @Override
    public void commit() throws TransactionException {
        dataSourceHandler.committer().accept(txObject);
    }

    @Override
    public void rollback() throws TransactionException {
        dataSourceHandler.rollbacker().accept(txObject);
    }

    @Override
    public void attachCurrentThread() {
        txComponent.put(Thread.currentThread(), txObject);
    }

    @Override
    public void detachCurrentThread() {
        txComponent.remove(Thread.currentThread());
    }

}
