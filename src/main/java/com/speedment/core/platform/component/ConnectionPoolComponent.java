/**
 *
 * Copyright (c) 2006-2015, Speedment, Inc. All Rights Reserved.
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
package com.speedment.core.platform.component;

import com.speedment.core.annotations.Api;
import com.speedment.core.pool.PoolableConnection;
import java.sql.SQLException;

/**
 * This Component interface is used for holding the connection pool that is
 * being used by Speedment.
 *
 * @author pemi
 * @since 2.0
 */
@Api(version = "2.0")
public interface ConnectionPoolComponent extends Component {

    @Override
    default Class<ConnectionPoolComponent> getComponentClass() {
        return ConnectionPoolComponent.class;
    }

    /**
     * Returns a {link PoolableConnection} from this connection pool. If a
     * connection is not present in the connection pool, a new one will be
     * created.
     *
     * @param uri the connection URI for the connector
     * @param user the user for the connector
     * @param password the password for the connector
     * @return a {link Connection} from this connection pool
     * @throws java.sql.SQLException if a connection can neither be obtained
     * from the pool nor created.
     */
    PoolableConnection getConnection(String uri, String user, String password) throws SQLException;

    /**
     * Returns a {@link PoolableConnection} to the pool. If the
     * PoolableConnection has expired or has a closed underlying connection, it
     * will be discarded from the pool.
     *
     * @param connection to return to the pool
     */
    void returnConnection(PoolableConnection connection);

    int getPoolSize();

    void setPoolSize(int size);

    long getMaxAge();

    void setMaxAge(long maxAge);

}
