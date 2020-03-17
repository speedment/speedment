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

import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.Transaction;
import com.speedment.runtime.core.component.transaction.TransactionComponent;

import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 */
public final class TransactionImpl implements Transaction {

    private final TransactionComponent txComponent;
    private final Object txObject;
    private final DataSourceHandler<Object, Object> dataSourceHandler;

    TransactionImpl(
        final TransactionComponent txComponent,
        final Object txObject,
        final DataSourceHandler<Object, Object> dataSourceHandler
    ) {
        this.txComponent = requireNonNull(txComponent);
        this.txObject = requireNonNull(txObject);
        this.dataSourceHandler = requireNonNull(dataSourceHandler);
    }

    @Override
    public void commit() {
        dataSourceHandler.committer().accept(txObject);
    }

    @Override
    public void rollback() {
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
