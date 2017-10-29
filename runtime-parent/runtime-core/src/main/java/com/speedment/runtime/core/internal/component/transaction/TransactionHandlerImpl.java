package com.speedment.runtime.core.internal.component.transaction;

import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.Transaction;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import com.speedment.runtime.core.exception.TransactionException;
import static java.util.Objects.requireNonNull;
import java.util.function.Consumer;

/**
 *
 * @author Per Minborg
 */
public class TransactionHandlerImpl implements TransactionHandler {

    private final TransactionComponent txComponent;
    private final Object dataSource;
    private final DataSourceHandler<Object, Object> dataSourceHandler;

    public TransactionHandlerImpl(
        final TransactionComponent txComponent,
        final Object dataSource,
        final DataSourceHandler<Object, Object> dataSourceHandler
    ) {
        this.txComponent = requireNonNull(txComponent);
        this.dataSource = requireNonNull(dataSource);
        this.dataSourceHandler = requireNonNull(dataSourceHandler);
    }

    @Override
    public void invoke(Consumer<Transaction> action) throws TransactionException {
        final Thread currentThread = Thread.currentThread();
        final Object txObject = dataSourceHandler.extractor().apply(dataSource); // e.g. obtains a Connection
        final Transaction tx = new TransactionImpl(txObject, dataSourceHandler);
        txComponent.put(currentThread, txObject);
        try {
            dataSourceHandler.beginner().accept(txObject); // e.g. con.setAutocommit(false)
            action.accept(tx);
        } catch (Exception e) {
            dataSourceHandler.rollbacker().accept(txObject); // Automatically rollback if there is an exception
            throw new TransactionException("Error while invoking transaction for object :" + txObject, e);
        } finally {
            dataSourceHandler.closer().accept(txObject); // e.g. con.setAutocommit(true); con.close();
            txComponent.remove(currentThread);
        }
    }

}
