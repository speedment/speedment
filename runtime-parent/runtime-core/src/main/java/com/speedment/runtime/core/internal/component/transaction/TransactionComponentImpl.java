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

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.WithState;
import com.speedment.common.logger.Logger;
import com.speedment.common.logger.LoggerManager;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.ProjectComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.connectionpool.PoolableConnection;
import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.Isolation;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import com.speedment.runtime.core.db.SqlConsumer;
import com.speedment.runtime.core.exception.TransactionException;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.speedment.common.injector.State.STARTED;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

/**
 *
 * @author Per Minborg
 */
public final class TransactionComponentImpl implements TransactionComponent {

    private static final Logger LOGGER = LoggerManager.getLogger(TransactionComponentImpl.class);

    private final Map<Class<?>, DataSourceHandler<Object, Object>> dataSourceHandlers;
    private final Map<Thread, Object> txObjects;
    private final Map<Object, Set<Thread>> threadSets;
    private Dbms singleDbms;

    public TransactionComponentImpl() {
        this.dataSourceHandlers = new ConcurrentHashMap<>();
        this.txObjects = new ConcurrentHashMap<>();
        this.threadSets = new ConcurrentHashMap<>();
    }

    @ExecuteBefore(STARTED)
    public void setupSingleDbms(@WithState(State.RESOLVED) ProjectComponent projectComponent) {
        final Set<Dbms> dbmses = projectComponent.getProject().dbmses().collect(toSet());
        if (dbmses.size() == 1) {
            singleDbms = dbmses.iterator().next();
        } else {
            LOGGER.warn("There are %d dbmses in the project %s -> TransactionComponent.createTransactionHandler() cannot be used.", dbmses.size(), projectComponent.getProject().getId());
        }
    }

    @ExecuteBefore(State.STARTED)
    public void addDbmsDataSourceHandler(ConnectionPoolComponent connectionPoolComponent) {
        final Function<Dbms, PoolableConnection> extractor = connectionPoolComponent::getConnection;
        final Consumer<PoolableConnection> starter = wrapSqlException(c -> c.setAutoCommit(false), "setup connection");
        final BiFunction<PoolableConnection, Isolation, Isolation> isolationConfigurator = (PoolableConnection c, Isolation newLevel) -> {
            final int previousLevel;
            try {
                previousLevel = c.getTransactionIsolation();
                c.setTransactionIsolation(newLevel.getSqlIsolationLevel());
            } catch (SQLException sqle) {
                throw new TransactionException("Unable to get/set isolation level for a connection " + c, sqle);
            }
            return Isolation.fromSqlIsolationLevel(previousLevel);
        };
        final Consumer<PoolableConnection> committer = wrapSqlException(PoolableConnection::commit, "commit connection");
        final Consumer<PoolableConnection> rollbacker = wrapSqlException(PoolableConnection::rollback, "rollback connection");
        final Consumer<PoolableConnection> closer = wrapSqlException(c -> {
            c.setAutoCommit(true);
            c.close();
        }, "close connection");
        putDataSourceHandler(Dbms.class, DataSourceHandler.of(extractor, isolationConfigurator, starter, committer, rollbacker, closer));
    }

    @Override
    public TransactionHandler createTransactionHandler() {
        if (singleDbms == null) {
            throw new IllegalStateException("This project does not contain exactly one Dbms.");
        }
        return createTransactionHandler(singleDbms);
    }

    @Override
    public <T> TransactionHandler createTransactionHandler(T dataSource) {
        return new TransactionHandlerImpl(this, dataSource, findMapping(dataSource));
    }

    @Override
    @SuppressWarnings("unchecked")
    public <D, T> void putDataSourceHandler(Class<D> dataSourceClass, DataSourceHandler<D, T> dataSourceHandler) {
        dataSourceHandlers.put(dataSourceClass, (DataSourceHandler<Object, Object>) dataSourceHandler);
    }

    @Override
    public void put(Thread thread, Object txObject) {
        if (txObjects.putIfAbsent(thread, txObject) != null) {
            throw new IllegalStateException(
                String.format("There is already a txObject associated with thread %s ", thread)
            );
        }
        threadSets.computeIfAbsent(
            txObject,
            (Object k) -> new HashSet<>()
        ).add(thread);
    }

    @Override
    public Optional<Object> get(Thread thread) {
        return Optional.ofNullable(txObjects.get(requireNonNull(thread)));
    }

    @Override
    public void remove(Thread thread) {
        final Object removedTxObject = txObjects.remove(requireNonNull(thread));
        if (removedTxObject != null) {
            final Set<Thread> threadSet = threadSets.get(removedTxObject);
            threadSet.remove(thread);
            if (threadSet.isEmpty()) {
                // Clean up 
                threadSets.remove(removedTxObject);
            }
        }
    }

    @Override
    public Stream<Thread> threads(Object txObject) {
        return Optional.ofNullable(threadSets.get(txObject))
            .map(Set::stream)
            .orElse(Stream.empty());
    }

    private DataSourceHandler<Object, Object> findMapping(Object dataSource) {
        final Class<?> originalClass = dataSource.getClass();

        final DataSourceHandler<Object, Object> dataSourceHandlerOriginal = dataSourceHandlers.get(originalClass);
        if (dataSourceHandlerOriginal != null) {
            return dataSourceHandlerOriginal;
         }

        for (Class<?> inter : originalClass.getInterfaces()) {
            final DataSourceHandler<Object, Object> dataSourceHandler = dataSourceHandlers.get(inter);
            if (dataSourceHandler != null) {
                dataSourceHandlers.put(inter, dataSourceHandler); // Enter this association to speed up next look-up
                return dataSourceHandler;
            }
        }
        Class<?> superClass = originalClass.getSuperclass();
        while (superClass != null) {
            final DataSourceHandler<Object, Object> dataSourceHandler = dataSourceHandlers.get(superClass);
            if (dataSourceHandler != null) {
                dataSourceHandlers.put(superClass, dataSourceHandler); // Enter this association to speed up next look-up
                return dataSourceHandler;
            }
            superClass = superClass.getSuperclass();
        }
        throw new IllegalArgumentException(
            String.format(
                "Unable to find a mapping for the data source %s of class %s. Available class mappings: %s",
                dataSource,
                originalClass,
                dataSourceHandlers.keySet().stream().map(Object::toString).collect(joining(", "))
            )
        );
    }

    private <T> Consumer<T> wrapSqlException(SqlConsumer<T> sqlConsumer, String label) {
        return (T t) -> {
            try {
                sqlConsumer.accept(t);
            } catch (SQLException sqle) {
                throw new TransactionException("Unable to " + label + ": " + t, sqle);
            }
        };
    }

}
