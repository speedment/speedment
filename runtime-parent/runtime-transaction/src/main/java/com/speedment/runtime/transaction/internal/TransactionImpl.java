package com.speedment.runtime.transaction.internal;

import com.speedment.runtime.transaction.DataSourceHandler;
import com.speedment.runtime.transaction.Transaction;
import com.speedment.runtime.transaction.exception.TransactionException;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public class TransactionImpl implements Transaction {

    private final Object transactionObject;
    private final DataSourceHandler<Object, Object> dataSourceHandler;

    public TransactionImpl(Object transactionObject, DataSourceHandler<Object, Object> dataSourceHandler) {
        this.transactionObject = requireNonNull(transactionObject);
        this.dataSourceHandler = requireNonNull(dataSourceHandler);
    }

    @Override
    public void commit() throws TransactionException {
        dataSourceHandler.committer().accept(transactionObject);
    }

    @Override
    public void rollback() throws TransactionException {
        dataSourceHandler.rollbacker().accept(transactionObject);
    }

}
