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
package com.speedment.runtime.core.internal.db;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.transaction.DataSourceHandler;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import com.speedment.runtime.core.db.DbmsType;
import com.speedment.runtime.core.internal.component.ConnectionPoolComponentImpl;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

final class DbmsOperationalHandlerBuilderImplTest {

    private DbmsOperationalHandlerBuilderImpl instance = new DbmsOperationalHandlerBuilderImpl(
            new DummyConnectionPoolComponent(),
            new DummyTransactionComponent());

    @Test
    void withGeneratedKeysHandler() {
        assertThrows(NullPointerException.class, () -> instance.withGeneratedKeysHandler(null));

        assertDoesNotThrow(() -> instance.withGeneratedKeysHandler(((preparedStatement, longConsumer) -> {})));
    }

    @Test
    void withConfigureSelectPreparedStatement() {
        assertThrows(NullPointerException.class, () -> instance.withConfigureSelectPreparedStatement(null));

        assertDoesNotThrow(() -> instance.withConfigureSelectPreparedStatement(preparedStatement -> {}));
    }

    @Test
    void withConfigureSelectResultSet() {
        assertThrows(NullPointerException.class, () -> instance.withConfigureSelectResultSet(null));

        assertDoesNotThrow(() -> instance.withConfigureSelectResultSet(resultSet -> {}));
    }

    @Test
    void withInsertHandler() {
        assertThrows(NullPointerException.class, () -> instance.withInsertHandler(null));

        assertDoesNotThrow(() -> instance.withInsertHandler((dbms, connection, hasGeneratedKeys) -> {}));
    }

    @Test
    void build() {
        assertNotNull(instance.build());
    }

    private static final class DummyConnectionPoolComponent extends ConnectionPoolComponentImpl {

        public DummyConnectionPoolComponent() {
            super(connection -> {},
                new DummyDbmsHandlerComponent(),
                new DummyPasswordComponent(),
                10000,
                10);
        }

        private static final class DummyDbmsHandlerComponent implements DbmsHandlerComponent {

            @Override
            public void install(DbmsType dbmsType) {
            }

            @Override
            public Stream<DbmsType> supportedDbmsTypes() {
                return Stream.empty();
            }

            @Override
            public Optional<DbmsType> findByName(String dbmsTypeName) {
                return Optional.empty();
            }
        }

        private static final class DummyPasswordComponent implements PasswordComponent {

            @Override
            public void put(String dbmsName, char[] password) {
            }

            @Override
            public Optional<char[]> get(String dbmsName) {
                return Optional.empty();
            }
        }
    }

    private static final class DummyTransactionComponent implements TransactionComponent {

        @Override
        public TransactionHandler createTransactionHandler() {
            return null;
        }

        @Override
        public <T> TransactionHandler createTransactionHandler(T dataSource) {
            return null;
        }

        @Override
        public <D, T> void putDataSourceHandler(Class<D> dataSourceClass,
                DataSourceHandler<D, T> dataSourceHandler) {

        }

        @Override
        public void put(Thread thread, Object txObject) {

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
    }
}
