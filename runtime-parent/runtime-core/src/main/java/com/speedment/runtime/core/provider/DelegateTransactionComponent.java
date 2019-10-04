/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
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
package com.speedment.runtime.core.provider;

import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import com.speedment.runtime.core.internal.component.transaction.TransactionComponentImpl;

import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateTransactionComponent implements TransactionComponent {

    private TransactionComponent inner;

    public DelegateTransactionComponent() {
        this.inner = new TransactionComponentImpl();
    }

    @Override
    public TransactionHandler createTransactionHandler() {
        return inner.createTransactionHandler();
    }

    @Override
    public <T> TransactionHandler createTransactionHandler(T dataSource) {
        return inner.createTransactionHandler(dataSource);
    }

    @Override
    public <D, T> void putDataSourceHandler(Class<D> dataSourceClass, DataSourceHandler<D, T> dataSourceHandler) {
        inner.putDataSourceHandler(dataSourceClass, dataSourceHandler);
    }

    @Override
    public void put(Thread thread, Object txObject) {
        inner.put(thread, txObject);
    }

    @Override
    public Optional<Object> get(Thread thread) {
        return inner.get(thread);
    }

    @Override
    public void remove(Thread thread) {
        inner.remove(thread);
    }

    @Override
    public Stream<Thread> threads(Object txObject) {
        return inner.threads(txObject);
    }
}
