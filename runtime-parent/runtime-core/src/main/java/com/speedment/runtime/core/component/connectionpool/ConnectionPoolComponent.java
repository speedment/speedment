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
package com.speedment.runtime.core.component.connectionpool;

import com.speedment.common.injector.annotation.InjectKey;
import com.speedment.runtime.config.Dbms;

import java.sql.Connection;

/**
 * This Component interface is used for holding the connection pool that is
 * being used by Speedment.
 *
 * @author pemi
 * @since 2.1.0
 */
@InjectKey(ConnectionPoolComponent.class)
public interface ConnectionPoolComponent {

    /**
     * Returns a {link PoolableConnection} from this connection pool. If a
     * connection is not present in the connection pool, a new one will be
     * created.
     *
     * @param uri the connection URI for the connector
     * @param username the user for the connector
     * @param password the password for the connector
     * @return a {@link PoolableConnection} from this connection pool
     */
    PoolableConnection getConnection(String uri, String username, char[] password);

    /**
     * Returns a {link PoolableConnection} from this connection pool. If a
     * connection is not present in the connection pool, a new one will be
     * created.
     *
     * @param dbms the dbms to connect to
     * @return a {@link PoolableConnection} from this connection pool
     */
    PoolableConnection getConnection(Dbms dbms);

    /**
     * Creates and returns a new {@link Connection} for the given parameters.
     * This method is called whenever the pool needs to allocate a new
     * Connection.
     *
     * @param uri the connection URI for the connector
     * @param username the user for the connector
     * @param password the password for the connector
     * @return a new {@link Connection} for the given parameters
     */
    Connection newConnection(String uri, String username, char[] password);

    /**
     * Creates and returns a new {@link Connection} for the given parameters.
     * This method is called whenever the pool needs to allocate a new
     * Connection.
     *
     * @param dbms the dbms to connect to
     * @return a new {@link Connection} for the given parameters
     */
    Connection newConnection(Dbms dbms);

    /**
     * Returns a {@link PoolableConnection} to the pool. If the
     * PoolableConnection has expired or has a closed underlying connection, it
     * will be discarded from the pool.
     *
     * @param connection to return to the pool
     */
    void returnConnection(PoolableConnection connection);

    /**
     * Returns the current number of idle connections in the pool.
     *
     * @return the current number of idle connections in the pool
     */
    int poolSize();

    /**
     * Returns the current number of leased connections from the pool.
     *
     * @return the current number of leased connections from the pool
     */
    int leaseSize();

    /**
     * Returns the maximum number of connection this pool will retain when
     * connections are returned. If the number of connections in the pool
     * exceeds this number of connections, a connection that is returned will be
     * discarded and will not be recycled.
     *
     * @return the maximum number of connection this pool will retain when
     * connections are returned
     */
    int getMaxRetainSize();


    /**
     * Returns the maximum age for recyclable connections. Connections older
     * that this age will be discarded.
     *
     * @return the maximum age for recyclable connections
     */
    long getMaxAge();


}
