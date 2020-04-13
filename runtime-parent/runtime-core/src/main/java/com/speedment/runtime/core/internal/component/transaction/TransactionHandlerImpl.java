/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.internal.component.transaction;

import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.core.ApplicationBuilder;
import com.speedment.runtime.core.component.transaction.*;
import com.speedment.runtime.core.exception.TransactionException;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class TransactionHandlerImpl implements TransactionHandler {

    private static final Logger TRANSACTION_LOGGER = LoggerManager.getLogger(ApplicationBuilder.LogType.TRANSACTION.getLoggerName());

    private final TransactionComponent txComponent;
    private final Object dataSource;
    private final DataSourceHandler<Object, Object> dataSourceHandler;
    private Isolation isolation;

    TransactionHandlerImpl(
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


    public <R> R createAndApply(Function<? super Transaction, ? extends R> mapper) {
        requireNonNull(mapper);
        final Thread currentThread = Thread.currentThread();
        final Object txObject = dataSourceHandler.extractor().apply(dataSource); // e.g. obtains a Connection
        final Isolation oldIsolation = setAndGetIsolation(txObject, isolation);
        final Transaction tx = new TransactionImpl(txComponent, txObject, dataSourceHandler);
        TRANSACTION_LOGGER.debug("Transaction %s created for thread '%s' on tranaction object %s", tx, currentThread.getName(), txObject);
        txComponent.put(currentThread, txObject);
        try {
            dataSourceHandler.beginner().accept(txObject); // e.g. con.setAutocommit(false)
            return mapper.apply(tx);
        } catch (Exception e) {
            // Executed in the finally block : dataSourceHandler.rollbacker().accept(txObject); // Automatically rollback if there is an exception
            throw new TransactionException("Error while invoking transaction for object :" + txObject, e);
        } finally {
            dataSourceHandler.rollbacker().accept(txObject); // Always rollback() implicitly and discard uncommitted data
            dataSourceHandler.closer().accept(txObject); // e.g. con.setAutocommit(true); con.close();
            setAndGetIsolation(txObject, oldIsolation);
            txComponent.remove(currentThread);
            TRANSACTION_LOGGER.debug("Transaction %s owned by thread '%s' was discarded", tx, currentThread.getName());
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
