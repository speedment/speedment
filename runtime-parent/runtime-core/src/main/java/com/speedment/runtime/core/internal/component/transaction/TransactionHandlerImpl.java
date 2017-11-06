package com.speedment.runtime.core.internal.component.transaction;

import com.speedment.common.logger.Level;
import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.Isolation;
import com.speedment.runtime.core.component.transaction.Transaction;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import com.speedment.runtime.core.exception.TransactionException;
import static java.util.Objects.requireNonNull;
import java.util.function.Function;

/**
 *
 * @author Per Minborg
 */
public class TransactionHandlerImpl implements TransactionHandler {

    private final TransactionComponent txComponent;
    private final Object dataSource;
    private final DataSourceHandler<Object, Object> dataSourceHandler;
    private Isolation isolation;

    public TransactionHandlerImpl(
        final TransactionComponent txComponent,
        final Object dataSource,
        final DataSourceHandler<Object, Object> dataSourceHandler
    ) {
        this.txComponent = requireNonNull(txComponent);
        this.dataSource = requireNonNull(dataSource);
        this.dataSourceHandler = requireNonNull(dataSourceHandler);
        this.isolation = Isolation.DEFAULT;
    }

    @Override
    public void setIsolation(Isolation level) {
        this.isolation = level;
    }

    @Override
    public Isolation getIsolation() {
        return isolation;
    }

    @Override
    public <R> R createAndApply(Function<? super Transaction, ? extends R> mapper) throws TransactionException {
        requireNonNull(mapper);
        final Thread currentThread = Thread.currentThread();
        final Object txObject = dataSourceHandler.extractor().apply(dataSource); // e.g. obtains a Connection
        final Isolation oldIsolation = setAndGetIsolation(txObject, isolation);
        final Transaction tx = new TransactionImpl(txComponent, txObject, dataSourceHandler);
        txComponent.put(currentThread, txObject);
        try {
            dataSourceHandler.beginner().accept(txObject); // e.g. con.setAutocommit(false)
            return mapper.apply(tx);
        } catch (Exception e) {
            dataSourceHandler.rollbacker().accept(txObject); // Automatically rollback if there is an exception
            throw new TransactionException("Error while invoking transaction for object :" + txObject, e);
        } finally {
            dataSourceHandler.committer().accept(txObject); // Always commit() implicitly
            dataSourceHandler.closer().accept(txObject); // e.g. con.setAutocommit(true); con.close();
            setAndGetIsolation(txObject, oldIsolation);
            txComponent.remove(currentThread);
        }
    }

    private Isolation setAndGetIsolation(Object txObject, Isolation isolation) {
        if (Isolation.DEFAULT != isolation) {
            return dataSourceHandler.isolationConfigurator().apply(txObject, isolation);
        } else {
            return Isolation.DEFAULT;
        }
    }

}
