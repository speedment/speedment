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
package com.speedment.runtime.core.provider;

import com.speedment.common.injector.State;
import com.speedment.common.injector.annotation.Config;
import com.speedment.common.injector.annotation.ExecuteBefore;
import com.speedment.common.injector.annotation.Inject;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.core.component.DbmsHandlerComponent;
import com.speedment.runtime.core.component.PasswordComponent;
import com.speedment.runtime.core.component.connectionpool.ConnectionDecorator;
import com.speedment.runtime.core.component.connectionpool.ConnectionPoolComponent;
import com.speedment.runtime.core.component.connectionpool.PoolableConnection;
import com.speedment.runtime.core.internal.component.ConnectionPoolComponentImpl;

import java.sql.Connection;

/**
 *
 * @author Per Minborg
 * @since 3.2.0
 */
public final class DelegateConnectionPoolComponent implements ConnectionPoolComponent {

    private final ConnectionPoolComponentImpl inner;

    @Inject
    public DelegateConnectionPoolComponent(
        final ConnectionDecorator connectionDecorator,
        final DbmsHandlerComponent dbmsHandlerComponent,
        final PasswordComponent passwordComponent,
        final @Config(name = "connectionpool.maxAge", value = "30000") long maxAge,
        final @Config(name = "connectionpool.maxRetainSize", value = "32") int maxRetainSize
    ) {
        inner = new ConnectionPoolComponentImpl(connectionDecorator, dbmsHandlerComponent, passwordComponent, maxAge, maxRetainSize);
    }

    @ExecuteBefore(State.STOPPED)
    public void closeOpenConnections() {
        inner.closeOpenConnections();
    }

    @Override
    public PoolableConnection getConnection(Dbms dbms) {
        return inner.getConnection(dbms);
    }

    @Override
    public PoolableConnection getConnection(String uri, String user, char[] password) {
        return inner.getConnection(uri, user, password);
    }

    @Override
    public Connection newConnection(Dbms dbms) {
        return inner.newConnection(dbms);
    }

    @Override
    public Connection newConnection(String uri, String username, char[] password) {
        return inner.newConnection(uri, username, password);
    }

    @Override
    public void returnConnection(PoolableConnection connection) {
        inner.returnConnection(connection);
    }

    @Override
    public int poolSize() {
        return inner.poolSize();
    }

    @Override
    public int leaseSize() {
        return inner.leaseSize();
    }

    @Override
    public long getMaxAge() {
        return inner.getMaxAge();
    }

    @Override
    public int getMaxRetainSize() {
        return inner.getMaxRetainSize();
    }

}
