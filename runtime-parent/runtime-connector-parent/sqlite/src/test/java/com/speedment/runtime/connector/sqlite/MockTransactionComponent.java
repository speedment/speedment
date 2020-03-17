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
