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
package com.speedment.runtime.core.component.transaction;

import com.speedment.common.injector.annotation.InjectKey;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * General Transaction Component
 *
 * @author Per Minborg
 * @since 3.0.17
 */
@InjectKey(TransactionComponent.class)
public interface TransactionComponent {

    /**
     * Creates and returns a new TransactionHandler for the single Dbms defined
     * in the current Speedment Project.
     *
     * @return a new TransactionHandler
     * @throws IllegalStateException if there is not exactly one Dbms defined in
     * the current project.
     */
    TransactionHandler createTransactionHandler();

    /**
     * Creates and returns a new TransactionHandler for the provided transaction
     * object (such as a database).
     *
     * @param <T> Type of the data source
     * @param dataSource the data source for which transactions are consistent.
     * @return a new TransactionHandler for the provided data source
     * @throws IllegalArgumentException if this TransactionComponent cannot
     * determine that the provided data source can be used with transactions.
     * @throws NullPointerException if the provided data source is null.
     */
    <T> TransactionHandler createTransactionHandler(T dataSource); // <T extends TransactionCapable>

    /**
     * Associates a certain data store class (e.g. Dbms) to some way of handling
     * an object that supports transactions (e.g. Connection) using a
     * DataSourceHandler
     * <p>
     * Example:
     * <pre>
     * {@code
     *    tc.putDataSourceHandle(
     *        Dbms.class,
     *        DataSourceHandler.of(
     *            connectionPoolComponent.getConnection(dbms),
     *            Connection::close
     *        )
     *    )
     * }
     * </pre>
     *
     * @param <D> the type of the data source (e.g. Dbms)
     * @param <T> the type of transaction aware object (e.g. Connection)
     * @param dataSourceClass the class of the data source (e.g. Dbms.class)
     * @param dataSourceHandler to associate with the class
     *
     */
    <D, T> void putDataSourceHandler(Class<D> dataSourceClass, DataSourceHandler<D, T> dataSourceHandler);

    /**
     * Associates a transaction aware object with the given thread.
     *
     * @param thread to associate the given transaction aware object to
     * @param txObject transaction aware object
     * @throws IllegalStateException if the thread is already associated with a
     * transaction aware object.
     * @throws NullPointerException if thread or txObject is null
     */
    void put(Thread thread, Object txObject);

    /**
     * Returns a transaction aware object (if any) for the given thread.
     *
     * @param thread to use
     * @return a transaction aware object (if any) for the given thread
     * @throws NullPointerException if thread is null
     */
    Optional<Object> get(Thread thread);

    /**
     * Removes the thread association to a transaction aware object (if
     * previously associated). If the thread was not previously associated, this
     * operation is a no-op.
     *
     * @param thread for which the association shall be removed
     * @throws NullPointerException if thread is null
     */
    void remove(Thread thread);

    /**
     * Creates and returns a Stream of threads that are associated with the
     * given transaction aware object.
     *
     * @param txObject for which Threads are associated to
     * @return a Stream of threads that are associated with the given
     * transaction aware object
     */
    Stream<Thread> threads(Object txObject);

}
