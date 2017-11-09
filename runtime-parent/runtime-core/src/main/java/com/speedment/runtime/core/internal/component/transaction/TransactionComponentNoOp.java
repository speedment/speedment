/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
import com.speedment.runtime.core.component.transaction.TransactionBundle;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import java.util.Optional;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class TransactionComponentNoOp implements TransactionComponent {

    public TransactionComponentNoOp() {
    }

    @Override
    public TransactionHandler createTransactionHandler() {
        throw createException();
    }

    @Override
    public <T> TransactionHandler creaateTransactionHandler(T dataSource) {
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

    @Override
    public Stream<Thread> threads(Object txObject) {
        return Stream.empty();
    }

    private UnsupportedOperationException createException() {
        return new UnsupportedOperationException("Transactions are not supported. Make sure you use the " + TransactionBundle.class + " in your project to support transactions");
    }

}
